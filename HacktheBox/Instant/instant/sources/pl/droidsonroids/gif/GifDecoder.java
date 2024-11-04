package pl.droidsonroids.gif;

import android.graphics.Bitmap;
import java.io.IOException;

/* loaded from: classes.dex */
public class GifDecoder {
    private final GifInfoHandle mGifInfoHandle;

    public GifDecoder(InputSource inputSource) throws IOException {
        this(inputSource, null);
    }

    public GifDecoder(InputSource inputSource, GifOptions gifOptions) throws IOException {
        GifInfoHandle open = inputSource.open();
        this.mGifInfoHandle = open;
        if (gifOptions != null) {
            open.setOptions(gifOptions.inSampleSize, gifOptions.inIsOpaque);
        }
    }

    public String getComment() {
        return this.mGifInfoHandle.getComment();
    }

    public int getLoopCount() {
        return this.mGifInfoHandle.getLoopCount();
    }

    public long getSourceLength() {
        return this.mGifInfoHandle.getSourceLength();
    }

    public void seekToTime(int i, Bitmap bitmap) {
        checkBuffer(bitmap);
        this.mGifInfoHandle.seekToTime(i, bitmap);
    }

    public void seekToFrame(int i, Bitmap bitmap) {
        checkBuffer(bitmap);
        this.mGifInfoHandle.seekToFrame(i, bitmap);
    }

    public long getAllocationByteCount() {
        return this.mGifInfoHandle.getAllocationByteCount();
    }

    public int getFrameDuration(int i) {
        return this.mGifInfoHandle.getFrameDuration(i);
    }

    public int getDuration() {
        return this.mGifInfoHandle.getDuration();
    }

    public int getWidth() {
        return this.mGifInfoHandle.getWidth();
    }

    public int getHeight() {
        return this.mGifInfoHandle.getHeight();
    }

    public int getNumberOfFrames() {
        return this.mGifInfoHandle.getNumberOfFrames();
    }

    public boolean isAnimated() {
        return this.mGifInfoHandle.getNumberOfFrames() > 1 && getDuration() > 0;
    }

    public void recycle() {
        this.mGifInfoHandle.recycle();
    }

    private void checkBuffer(Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("Bitmap is recycled");
        }
        if (bitmap.getWidth() < this.mGifInfoHandle.getWidth() || bitmap.getHeight() < this.mGifInfoHandle.getHeight()) {
            throw new IllegalArgumentException("Bitmap ia too small, size must be greater than or equal to GIF size");
        }
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("Only Config.ARGB_8888 is supported. Current bitmap config: " + bitmap.getConfig());
        }
    }
}
