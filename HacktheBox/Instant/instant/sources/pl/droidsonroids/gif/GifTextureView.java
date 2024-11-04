package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.lang.ref.WeakReference;
import pl.droidsonroids.gif.GifViewUtils;
import pl.droidsonroids.gif.InputSource;

/* loaded from: classes.dex */
public class GifTextureView extends TextureView {
    private static final ImageView.ScaleType[] sScaleTypeArray = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    private InputSource mInputSource;
    private RenderThread mRenderThread;
    private ImageView.ScaleType mScaleType;
    private float mSpeedFactor;
    private final Matrix mTransform;
    private GifViewUtils.GifViewAttributes viewAttributes;

    /* loaded from: classes.dex */
    public interface PlaceholderDrawListener {
        void onDrawPlaceholder(Canvas canvas);
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override // android.view.TextureView
    public TextureView.SurfaceTextureListener getSurfaceTextureListener() {
        return null;
    }

    public GifTextureView(Context context) {
        super(context);
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mTransform = new Matrix();
        this.mSpeedFactor = 1.0f;
        init(null, 0, 0);
    }

    public GifTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mTransform = new Matrix();
        this.mSpeedFactor = 1.0f;
        init(attributeSet, 0, 0);
    }

    public GifTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mTransform = new Matrix();
        this.mSpeedFactor = 1.0f;
        init(attributeSet, i, 0);
    }

    public GifTextureView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mTransform = new Matrix();
        this.mSpeedFactor = 1.0f;
        init(attributeSet, i, i2);
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        if (attributeSet != null) {
            int attributeIntValue = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "scaleType", -1);
            if (attributeIntValue >= 0) {
                ImageView.ScaleType[] scaleTypeArr = sScaleTypeArray;
                if (attributeIntValue < scaleTypeArr.length) {
                    this.mScaleType = scaleTypeArr[attributeIntValue];
                }
            }
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.GifTextureView, i, i2);
            this.mInputSource = findSource(obtainStyledAttributes);
            super.setOpaque(obtainStyledAttributes.getBoolean(R.styleable.GifTextureView_isOpaque, false));
            obtainStyledAttributes.recycle();
            this.viewAttributes = new GifViewUtils.GifViewAttributes(this, attributeSet, i, i2);
        } else {
            super.setOpaque(false);
            this.viewAttributes = new GifViewUtils.GifViewAttributes();
        }
        if (isInEditMode()) {
            return;
        }
        RenderThread renderThread = new RenderThread(this);
        this.mRenderThread = renderThread;
        if (this.mInputSource != null) {
            renderThread.start();
        }
    }

    @Override // android.view.TextureView
    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        throw new UnsupportedOperationException("Changing SurfaceTextureListener is not supported");
    }

    @Override // android.view.TextureView
    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        throw new UnsupportedOperationException("Changing SurfaceTexture is not supported");
    }

    private static InputSource findSource(TypedArray typedArray) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(R.styleable.GifTextureView_gifSource, typedValue)) {
            return null;
        }
        if (typedValue.resourceId != 0) {
            String resourceTypeName = typedArray.getResources().getResourceTypeName(typedValue.resourceId);
            if (GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
                return new InputSource.ResourcesSource(typedArray.getResources(), typedValue.resourceId);
            }
            if (!TypedValues.Custom.S_STRING.equals(resourceTypeName)) {
                throw new IllegalArgumentException("Expected string, drawable, mipmap or raw resource type. '" + resourceTypeName + "' is not supported");
            }
        }
        return new InputSource.AssetSource(typedArray.getResources().getAssets(), typedValue.string.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class RenderThread extends Thread implements TextureView.SurfaceTextureListener {
        final ConditionVariable isSurfaceValid;
        private GifInfoHandle mGifInfoHandle;
        private final WeakReference<GifTextureView> mGifTextureViewReference;
        private IOException mIOException;
        long[] mSavedState;

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        RenderThread(GifTextureView gifTextureView) {
            super("GifRenderThread");
            this.isSurfaceValid = new ConditionVariable();
            this.mGifInfoHandle = new GifInfoHandle();
            this.mGifTextureViewReference = new WeakReference<>(gifTextureView);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                GifTextureView gifTextureView = this.mGifTextureViewReference.get();
                if (gifTextureView == null) {
                    return;
                }
                GifInfoHandle open = gifTextureView.mInputSource.open();
                this.mGifInfoHandle = open;
                open.setOptions((char) 1, gifTextureView.isOpaque());
                if (gifTextureView.viewAttributes.mLoopCount >= 0) {
                    this.mGifInfoHandle.setLoopCount(gifTextureView.viewAttributes.mLoopCount);
                }
                final GifTextureView gifTextureView2 = this.mGifTextureViewReference.get();
                if (gifTextureView2 != null) {
                    gifTextureView2.setSuperSurfaceTextureListener(this);
                    boolean isAvailable = gifTextureView2.isAvailable();
                    this.isSurfaceValid.set(isAvailable);
                    if (isAvailable) {
                        gifTextureView2.post(new Runnable() { // from class: pl.droidsonroids.gif.GifTextureView.RenderThread.1
                            @Override // java.lang.Runnable
                            public void run() {
                                gifTextureView2.updateTextureViewSize(RenderThread.this.mGifInfoHandle);
                            }
                        });
                    }
                    this.mGifInfoHandle.setSpeedFactor(gifTextureView2.mSpeedFactor);
                    while (!isInterrupted()) {
                        try {
                            this.isSurfaceValid.block();
                            GifTextureView gifTextureView3 = this.mGifTextureViewReference.get();
                            if (gifTextureView3 == null) {
                                break;
                            }
                            SurfaceTexture surfaceTexture = gifTextureView3.getSurfaceTexture();
                            if (surfaceTexture != null) {
                                Surface surface = new Surface(surfaceTexture);
                                try {
                                    this.mGifInfoHandle.bindSurface(surface, this.mSavedState);
                                } finally {
                                    surface.release();
                                }
                            }
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    this.mGifInfoHandle.recycle();
                    this.mGifInfoHandle = new GifInfoHandle();
                    return;
                }
                this.mGifInfoHandle.recycle();
            } catch (IOException e) {
                this.mIOException = e;
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            GifTextureView gifTextureView = this.mGifTextureViewReference.get();
            if (gifTextureView != null) {
                gifTextureView.updateTextureViewSize(this.mGifInfoHandle);
            }
            this.isSurfaceValid.open();
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            this.isSurfaceValid.close();
            this.mGifInfoHandle.postUnbindSurface();
            interrupt();
            return true;
        }

        void dispose(GifTextureView gifTextureView, PlaceholderDrawListener placeholderDrawListener) {
            this.isSurfaceValid.close();
            gifTextureView.setSuperSurfaceTextureListener(placeholderDrawListener != null ? new PlaceholderDrawingSurfaceTextureListener(placeholderDrawListener) : null);
            this.mGifInfoHandle.postUnbindSurface();
            interrupt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSuperSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        super.setSurfaceTextureListener(surfaceTextureListener);
    }

    @Override // android.view.TextureView
    public void setOpaque(boolean z) {
        if (z != isOpaque()) {
            super.setOpaque(z);
            setInputSource(this.mInputSource);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        this.mRenderThread.dispose(this, null);
        super.onDetachedFromWindow();
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
    }

    public synchronized void setInputSource(InputSource inputSource) {
        setInputSource(inputSource, null);
    }

    public synchronized void setInputSource(InputSource inputSource, PlaceholderDrawListener placeholderDrawListener) {
        this.mRenderThread.dispose(this, placeholderDrawListener);
        try {
            this.mRenderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mInputSource = inputSource;
        RenderThread renderThread = new RenderThread(this);
        this.mRenderThread = renderThread;
        if (inputSource != null) {
            renderThread.start();
        } else {
            clearSurface();
        }
    }

    private void clearSurface() {
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (surfaceTexture != null) {
            Surface surface = new Surface(surfaceTexture);
            try {
                surface.unlockCanvasAndPost(surface.lockCanvas(null));
            } finally {
                surface.release();
            }
        }
    }

    public void setSpeed(float f) {
        this.mSpeedFactor = f;
        this.mRenderThread.mGifInfoHandle.setSpeedFactor(f);
    }

    public IOException getIOException() {
        return this.mRenderThread.mIOException != null ? this.mRenderThread.mIOException : GifIOException.fromCode(this.mRenderThread.mGifInfoHandle.getNativeErrorCode());
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
        updateTextureViewSize(this.mRenderThread.mGifInfoHandle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextureViewSize(GifInfoHandle gifInfoHandle) {
        Matrix matrix = new Matrix();
        float width = getWidth();
        float height = getHeight();
        float width2 = gifInfoHandle.getWidth() / width;
        float height2 = gifInfoHandle.getHeight() / height;
        RectF rectF = new RectF(0.0f, 0.0f, gifInfoHandle.getWidth(), gifInfoHandle.getHeight());
        RectF rectF2 = new RectF(0.0f, 0.0f, width, height);
        switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
            case 1:
                matrix.setScale(width2, height2, width / 2.0f, height / 2.0f);
                break;
            case 2:
                float min = 1.0f / Math.min(width2, height2);
                matrix.setScale(width2 * min, min * height2, width / 2.0f, height / 2.0f);
                break;
            case 3:
                float min2 = (((float) gifInfoHandle.getWidth()) > width || ((float) gifInfoHandle.getHeight()) > height) ? Math.min(1.0f / width2, 1.0f / height2) : 1.0f;
                matrix.setScale(width2 * min2, min2 * height2, width / 2.0f, height / 2.0f);
                break;
            case 4:
                matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
                matrix.preScale(width2, height2);
                break;
            case 5:
                matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.END);
                matrix.preScale(width2, height2);
                break;
            case 6:
                matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.START);
                matrix.preScale(width2, height2);
                break;
            case 7:
                return;
            case 8:
                matrix.set(this.mTransform);
                matrix.preScale(width2, height2);
                break;
        }
        super.setTransform(matrix);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: pl.droidsonroids.gif.GifTextureView$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.MATRIX.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public void setImageMatrix(Matrix matrix) {
        setTransform(matrix);
    }

    @Override // android.view.TextureView
    public void setTransform(Matrix matrix) {
        this.mTransform.set(matrix);
        updateTextureViewSize(this.mRenderThread.mGifInfoHandle);
    }

    @Override // android.view.TextureView
    public Matrix getTransform(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.set(this.mTransform);
        return matrix;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        RenderThread renderThread = this.mRenderThread;
        renderThread.mSavedState = renderThread.mGifInfoHandle.getSavedState();
        return new GifViewSavedState(super.onSaveInstanceState(), this.viewAttributes.freezesAnimation ? this.mRenderThread.mSavedState : null);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        this.mRenderThread.mSavedState = gifViewSavedState.mStates[0];
    }

    public void setFreezesAnimation(boolean z) {
        this.viewAttributes.freezesAnimation = z;
    }
}
