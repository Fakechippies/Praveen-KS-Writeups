package pl.droidsonroids.gif;

import java.io.IOException;

/* loaded from: classes.dex */
public class GifTexImage2D {
    private final GifInfoHandle mGifInfoHandle;

    public GifTexImage2D(InputSource inputSource, GifOptions gifOptions) throws IOException {
        gifOptions = gifOptions == null ? new GifOptions() : gifOptions;
        GifInfoHandle open = inputSource.open();
        this.mGifInfoHandle = open;
        open.setOptions(gifOptions.inSampleSize, gifOptions.inIsOpaque);
        open.initTexImageDescriptor();
    }

    public int getFrameDuration(int i) {
        return this.mGifInfoHandle.getFrameDuration(i);
    }

    public void seekToFrame(int i) {
        this.mGifInfoHandle.seekToFrameGL(i);
    }

    public int getNumberOfFrames() {
        return this.mGifInfoHandle.getNumberOfFrames();
    }

    public int getCurrentFrameIndex() {
        return this.mGifInfoHandle.getCurrentFrameIndex();
    }

    public void setSpeed(float f) {
        this.mGifInfoHandle.setSpeedFactor(f);
    }

    public void glTexImage2D(int i, int i2) {
        this.mGifInfoHandle.glTexImage2D(i, i2);
    }

    public void glTexSubImage2D(int i, int i2) {
        this.mGifInfoHandle.glTexSubImage2D(i, i2);
    }

    public void startDecoderThread() {
        this.mGifInfoHandle.startDecoderThread();
    }

    public void stopDecoderThread() {
        this.mGifInfoHandle.stopDecoderThread();
    }

    public void recycle() {
        GifInfoHandle gifInfoHandle = this.mGifInfoHandle;
        if (gifInfoHandle != null) {
            gifInfoHandle.recycle();
        }
    }

    public int getWidth() {
        return this.mGifInfoHandle.getWidth();
    }

    public int getHeight() {
        return this.mGifInfoHandle.getHeight();
    }

    public int getDuration() {
        return this.mGifInfoHandle.getDuration();
    }

    protected final void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }
}
