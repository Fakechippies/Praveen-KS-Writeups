package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import pl.droidsonroids.gif.GifDrawableInit;
import pl.droidsonroids.gif.InputSource;

/* loaded from: classes.dex */
public abstract class GifDrawableInit<T extends GifDrawableInit<T>> {
    private ScheduledThreadPoolExecutor mExecutor;
    private InputSource mInputSource;
    private GifDrawable mOldDrawable;
    private boolean mIsRenderingTriggeredOnDraw = true;
    private final GifOptions mOptions = new GifOptions();

    public ScheduledThreadPoolExecutor getExecutor() {
        return this.mExecutor;
    }

    public InputSource getInputSource() {
        return this.mInputSource;
    }

    public GifDrawable getOldDrawable() {
        return this.mOldDrawable;
    }

    public GifOptions getOptions() {
        return this.mOptions;
    }

    public boolean isRenderingTriggeredOnDraw() {
        return this.mIsRenderingTriggeredOnDraw;
    }

    protected abstract T self();

    public T sampleSize(int i) {
        this.mOptions.setInSampleSize(i);
        return self();
    }

    public GifDrawable build() throws IOException {
        InputSource inputSource = this.mInputSource;
        if (inputSource == null) {
            throw new NullPointerException("Source is not set");
        }
        return inputSource.createGifDrawable(this.mOldDrawable, this.mExecutor, this.mIsRenderingTriggeredOnDraw, this.mOptions);
    }

    public T with(GifDrawable gifDrawable) {
        this.mOldDrawable = gifDrawable;
        return self();
    }

    public T threadPoolSize(int i) {
        this.mExecutor = new ScheduledThreadPoolExecutor(i);
        return self();
    }

    public T taskExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.mExecutor = scheduledThreadPoolExecutor;
        return self();
    }

    public T renderingTriggeredOnDraw(boolean z) {
        this.mIsRenderingTriggeredOnDraw = z;
        return self();
    }

    public T setRenderingTriggeredOnDraw(boolean z) {
        return renderingTriggeredOnDraw(z);
    }

    public T options(GifOptions gifOptions) {
        this.mOptions.setFrom(gifOptions);
        return self();
    }

    public T from(InputStream inputStream) {
        this.mInputSource = new InputSource.InputStreamSource(inputStream);
        return self();
    }

    public T from(AssetFileDescriptor assetFileDescriptor) {
        this.mInputSource = new InputSource.AssetFileDescriptorSource(assetFileDescriptor);
        return self();
    }

    public T from(FileDescriptor fileDescriptor) {
        this.mInputSource = new InputSource.FileDescriptorSource(fileDescriptor);
        return self();
    }

    public T from(AssetManager assetManager, String str) {
        this.mInputSource = new InputSource.AssetSource(assetManager, str);
        return self();
    }

    public T from(ContentResolver contentResolver, Uri uri) {
        this.mInputSource = new InputSource.UriSource(contentResolver, uri);
        return self();
    }

    public T from(File file) {
        this.mInputSource = new InputSource.FileSource(file);
        return self();
    }

    public T from(String str) {
        this.mInputSource = new InputSource.FileSource(str);
        return self();
    }

    public T from(byte[] bArr) {
        this.mInputSource = new InputSource.ByteArraySource(bArr);
        return self();
    }

    public T from(ByteBuffer byteBuffer) {
        this.mInputSource = new InputSource.DirectByteBufferSource(byteBuffer);
        return self();
    }

    public T from(Resources resources, int i) {
        this.mInputSource = new InputSource.ResourcesSource(resources, i);
        return self();
    }
}
