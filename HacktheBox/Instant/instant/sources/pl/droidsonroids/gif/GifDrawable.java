package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.MediaController;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import pl.droidsonroids.gif.transforms.CornerRadiusTransform;
import pl.droidsonroids.gif.transforms.Transform;

/* loaded from: classes.dex */
public class GifDrawable extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    final Bitmap mBuffer;
    private final Rect mDstRect;
    final ScheduledThreadPoolExecutor mExecutor;
    final InvalidationHandler mInvalidationHandler;
    final boolean mIsRenderingTriggeredOnDraw;
    volatile boolean mIsRunning;
    final ConcurrentLinkedQueue<AnimationListener> mListeners;
    final GifInfoHandle mNativeInfoHandle;
    long mNextFrameRenderTime;
    protected final Paint mPaint;
    private final RenderTask mRenderTask;
    ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode;
    private Transform mTransform;

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        return 100;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    public Transform getTransform() {
        return this.mTransform;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return this.mIsRunning;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mIsRunning;
    }

    public GifDrawable(Resources resources, int i) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
        float densityScale = GifViewUtils.getDensityScale(resources, i);
        this.mScaledHeight = (int) (this.mNativeInfoHandle.getHeight() * densityScale);
        this.mScaledWidth = (int) (this.mNativeInfoHandle.getWidth() * densityScale);
    }

    public GifDrawable(AssetManager assetManager, String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifDrawable(String str) throws IOException {
        this(new GifInfoHandle(str), null, null, true);
    }

    public GifDrawable(File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream), null, null, true);
    }

    public GifDrawable(AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor), null, null, true);
    }

    public GifDrawable(FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor), null, null, true);
    }

    public GifDrawable(byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr), null, null, true);
    }

    public GifDrawable(ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer), null, null, true);
    }

    public GifDrawable(ContentResolver contentResolver, Uri uri) throws IOException {
        this(GifInfoHandle.openUri(contentResolver, uri), null, null, true);
    }

    protected GifDrawable(InputSource inputSource, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z, GifOptions gifOptions) throws IOException {
        this(inputSource.createHandleWith(gifOptions), gifDrawable, scheduledThreadPoolExecutor, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        RenderTask renderTask = new RenderTask(this);
        this.mRenderTask = renderTask;
        this.mIsRenderingTriggeredOnDraw = z;
        this.mExecutor = scheduledThreadPoolExecutor == null ? GifRenderingExecutor.getInstance() : scheduledThreadPoolExecutor;
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap bitmap = null;
        if (gifDrawable != null) {
            synchronized (gifDrawable.mNativeInfoHandle) {
                if (!gifDrawable.mNativeInfoHandle.isRecycled() && gifDrawable.mNativeInfoHandle.getHeight() >= gifInfoHandle.getHeight() && gifDrawable.mNativeInfoHandle.getWidth() >= gifInfoHandle.getWidth()) {
                    gifDrawable.shutdown();
                    Bitmap bitmap2 = gifDrawable.mBuffer;
                    bitmap2.eraseColor(0);
                    bitmap = bitmap2;
                }
            }
        }
        if (bitmap == null) {
            this.mBuffer = Bitmap.createBitmap(gifInfoHandle.getWidth(), gifInfoHandle.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            this.mBuffer = bitmap;
        }
        this.mBuffer.setHasAlpha(!gifInfoHandle.isOpaque());
        this.mSrcRect = new Rect(0, 0, gifInfoHandle.getWidth(), gifInfoHandle.getHeight());
        this.mInvalidationHandler = new InvalidationHandler(this);
        renderTask.doWork();
        this.mScaledWidth = gifInfoHandle.getWidth();
        this.mScaledHeight = gifInfoHandle.getHeight();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.recycle();
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.isRecycled();
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        scheduleNextRender();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (!this.mNativeInfoHandle.isOpaque() || this.mPaint.getAlpha() < 255) ? -2 : -1;
    }

    @Override // android.graphics.drawable.Animatable, android.widget.MediaController.MediaPlayerControl
    public void start() {
        synchronized (this) {
            if (this.mIsRunning) {
                return;
            }
            this.mIsRunning = true;
            startAnimation(this.mNativeInfoHandle.restoreRemainder());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startAnimation(long j) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0L;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        } else {
            cancelPendingRenderTask();
            this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(j, 0L), TimeUnit.MILLISECONDS);
        }
    }

    public void reset() {
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.1
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                if (GifDrawable.this.mNativeInfoHandle.reset()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.saveRemainder();
            }
        }
    }

    private void cancelPendingRenderTask() {
        ScheduledFuture<?> scheduledFuture = this.mRenderTaskSchedule;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    public String getComment() {
        return this.mNativeInfoHandle.getComment();
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.getLoopCount();
    }

    public void setLoopCount(int i) {
        this.mNativeInfoHandle.setLoopCount(i);
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", Integer.valueOf(this.mNativeInfoHandle.getWidth()), Integer.valueOf(this.mNativeInfoHandle.getHeight()), Integer.valueOf(this.mNativeInfoHandle.getNumberOfFrames()), Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode()));
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.getNumberOfFrames();
    }

    public GifError getError() {
        return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
    }

    public static GifDrawable createFromResource(Resources resources, int i) {
        try {
            return new GifDrawable(resources, i);
        } catch (IOException unused) {
            return null;
        }
    }

    public void setSpeed(float f) {
        this.mNativeInfoHandle.setSpeedFactor(f);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        stop();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        return this.mNativeInfoHandle.getDuration();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        return this.mNativeInfoHandle.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.2
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable.this.mNativeInfoHandle.seekToTime(i, GifDrawable.this.mBuffer);
                this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public void seekToBlocking(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i, this.mBuffer);
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
    }

    public void seekToFrame(final int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        this.mExecutor.execute(new SafeRunnable(this) { // from class: pl.droidsonroids.gif.GifDrawable.3
            @Override // pl.droidsonroids.gif.SafeRunnable
            public void doWork() {
                GifDrawable.this.mNativeInfoHandle.seekToFrame(i, GifDrawable.this.mBuffer);
                GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
            }
        });
    }

    public Bitmap seekToFrameAndGet(int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToFrame(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    public Bitmap seekToPositionAndGet(int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.seekToTime(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0L);
        return currentFrame;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    public int getFrameByteCount() {
        return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
    }

    public long getAllocationByteCount() {
        return this.mNativeInfoHandle.getAllocationByteCount() + this.mBuffer.getAllocationByteCount();
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.getMetadataByteCount();
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.getSourceLength();
    }

    public void getPixels(int[] iArr) {
        this.mBuffer.getPixels(iArr, 0, this.mNativeInfoHandle.getWidth(), 0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
    }

    public int getPixel(int i, int i2) {
        if (i >= this.mNativeInfoHandle.getWidth()) {
            throw new IllegalArgumentException("x must be < width");
        }
        if (i2 >= this.mNativeInfoHandle.getHeight()) {
            throw new IllegalArgumentException("y must be < height");
        }
        return this.mBuffer.getPixel(i, i2);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mDstRect.set(rect);
        Transform transform = this.mTransform;
        if (transform != null) {
            transform.onBoundsChange(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            z = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            z = true;
        }
        Transform transform = this.mTransform;
        if (transform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            transform.onDraw(canvas, this.mPaint, this.mBuffer);
        }
        if (z) {
            this.mPaint.setColorFilter(null);
        }
    }

    private void scheduleNextRender() {
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning) {
            long j = this.mNextFrameRenderTime;
            if (j != Long.MIN_VALUE) {
                long max = Math.max(0L, j - SystemClock.uptimeMillis());
                this.mNextFrameRenderTime = Long.MIN_VALUE;
                this.mExecutor.remove(this.mRenderTask);
                this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, max, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    public void addAnimationListener(AnimationListener animationListener) {
        this.mListeners.add(animationListener);
    }

    public boolean removeAnimationListener(AnimationListener animationListener) {
        return this.mListeners.remove(animationListener);
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public Bitmap getCurrentFrame() {
        Bitmap bitmap = this.mBuffer;
        Bitmap copy = bitmap.copy(bitmap.getConfig(), this.mBuffer.isMutable());
        copy.setHasAlpha(this.mBuffer.hasAlpha());
        return copy;
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mTint;
        if (colorStateList == null || (mode = this.mTintMode) == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(colorStateList, mode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList;
        return super.isStateful() || ((colorStateList = this.mTint) != null && colorStateList.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (z) {
                if (z2) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.getCurrentFrameIndex();
    }

    public int getCurrentLoop() {
        int currentLoop = this.mNativeInfoHandle.getCurrentLoop();
        return (currentLoop == 0 || currentLoop < this.mNativeInfoHandle.getLoopCount()) ? currentLoop : currentLoop - 1;
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.isAnimationCompleted();
    }

    public int getFrameDuration(int i) {
        return this.mNativeInfoHandle.getFrameDuration(i);
    }

    public void setCornerRadius(float f) {
        CornerRadiusTransform cornerRadiusTransform = new CornerRadiusTransform(f);
        this.mTransform = cornerRadiusTransform;
        cornerRadiusTransform.onBoundsChange(this.mDstRect);
    }

    public float getCornerRadius() {
        Transform transform = this.mTransform;
        if (transform instanceof CornerRadiusTransform) {
            return ((CornerRadiusTransform) transform).getCornerRadius();
        }
        return 0.0f;
    }

    public void setTransform(Transform transform) {
        this.mTransform = transform;
        if (transform != null) {
            transform.onBoundsChange(this.mDstRect);
        }
    }
}
