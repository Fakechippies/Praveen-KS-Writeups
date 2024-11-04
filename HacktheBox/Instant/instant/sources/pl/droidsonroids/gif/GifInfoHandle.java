package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.view.Surface;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.text.Typography;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class GifInfoHandle {
    private volatile long gifInfoPtr;

    private static native void bindSurface(long j, Surface surface, long[] jArr);

    static native int createTempNativeFileDescriptor() throws GifIOException;

    static native int extractNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException;

    private static native void free(long j);

    private static native long getAllocationByteCount(long j);

    private static native String getComment(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getFrameDuration(long j, int i);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native long getMetadataByteCount(long j);

    private static native int getNativeErrorCode(long j);

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native long getSourceLength(long j);

    private static native int getWidth(long j);

    private static native void glTexImage2D(long j, int i, int i2);

    private static native void glTexSubImage2D(long j, int i, int i2);

    private static native void initTexImageDescriptor(long j);

    private static native boolean isAnimationCompleted(long j);

    private static native boolean isOpaque(long j);

    static native long openByteArray(byte[] bArr) throws GifIOException;

    static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    static native long openFile(String str) throws GifIOException;

    static native long openNativeFileDescriptor(int i, long j) throws GifIOException;

    static native long openStream(InputStream inputStream) throws GifIOException;

    private static native void postUnbindSurface(long j);

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToFrame(long j, int i, Bitmap bitmap);

    private static native void seekToFrameGL(long j, int i);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    private static native void setLoopCount(long j, char c);

    private static native void setOptions(long j, char c, boolean z);

    private static native void setSpeedFactor(long j, float f);

    private static native void startDecoderThread(long j);

    private static native void stopDecoderThread(long j);

    static {
        LibraryLoader.loadLibrary();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.gifInfoPtr = openFileDescriptor(fileDescriptor, 0L, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(byte[] bArr) throws GifIOException {
        this.gifInfoPtr = openByteArray(bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(ByteBuffer byteBuffer) throws GifIOException {
        this.gifInfoPtr = openDirectByteBuffer(byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(String str) throws GifIOException {
        this.gifInfoPtr = openFile(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(InputStream inputStream) throws GifIOException {
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("InputStream does not support marking");
        }
        this.gifInfoPtr = openStream(inputStream);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        try {
            this.gifInfoPtr = openFileDescriptor(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), false);
        } finally {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
    }

    private static long openFileDescriptor(FileDescriptor fileDescriptor, long j, boolean z) throws GifIOException {
        int nativeFileDescriptor;
        if (Build.VERSION.SDK_INT > 27) {
            try {
                nativeFileDescriptor = getNativeFileDescriptor(fileDescriptor, z);
            } catch (Exception e) {
                throw new GifIOException(GifError.OPEN_FAILED.errorCode, e.getMessage());
            }
        } else {
            nativeFileDescriptor = extractNativeFileDescriptor(fileDescriptor, z);
        }
        return openNativeFileDescriptor(nativeFileDescriptor, j);
    }

    private static int getNativeFileDescriptor(FileDescriptor fileDescriptor, boolean z) throws GifIOException, ErrnoException {
        try {
            int createTempNativeFileDescriptor = createTempNativeFileDescriptor();
            Os.dup2(fileDescriptor, createTempNativeFileDescriptor);
            return createTempNativeFileDescriptor;
        } finally {
            if (z) {
                Os.close(fileDescriptor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static GifInfoHandle openUri(ContentResolver contentResolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        AssetFileDescriptor openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
        if (openAssetFileDescriptor == null) {
            throw new IOException("Could not open AssetFileDescriptor for " + uri);
        }
        return new GifInfoHandle(openAssetFileDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long renderFrame(Bitmap bitmap) {
        return renderFrame(this.gifInfoPtr, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void bindSurface(Surface surface, long[] jArr) {
        bindSurface(this.gifInfoPtr, surface, jArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void recycle() {
        free(this.gifInfoPtr);
        this.gifInfoPtr = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long restoreRemainder() {
        return restoreRemainder(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean reset() {
        return reset(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void saveRemainder() {
        saveRemainder(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized String getComment() {
        return getComment(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getLoopCount() {
        return getLoopCount(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setLoopCount(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.gifInfoPtr, (char) i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long getSourceLength() {
        return getSourceLength(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getNativeErrorCode() {
        return getNativeErrorCode(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSpeedFactor(float f) {
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (f < 4.656613E-10f) {
            f = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.gifInfoPtr, f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getDuration() {
        return getDuration(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getCurrentPosition() {
        return getCurrentPosition(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getCurrentFrameIndex() {
        return getCurrentFrameIndex(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getCurrentLoop() {
        return getCurrentLoop(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void seekToTime(int i, Bitmap bitmap) {
        seekToTime(this.gifInfoPtr, i, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void seekToFrame(int i, Bitmap bitmap) {
        seekToFrame(this.gifInfoPtr, i, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long getAllocationByteCount() {
        return getAllocationByteCount(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long getMetadataByteCount() {
        return getMetadataByteCount(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean isRecycled() {
        return this.gifInfoPtr == 0;
    }

    protected void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void postUnbindSurface() {
        postUnbindSurface(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean isAnimationCompleted() {
        return isAnimationCompleted(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized long[] getSavedState() {
        return getSavedState(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int restoreSavedState(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.gifInfoPtr, jArr, bitmap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getFrameDuration(int i) {
        throwIfFrameIndexOutOfBounds(i);
        return getFrameDuration(this.gifInfoPtr, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOptions(char c, boolean z) {
        setOptions(this.gifInfoPtr, c, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getWidth() {
        return getWidth(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getHeight() {
        return getHeight(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getNumberOfFrames() {
        return getNumberOfFrames(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean isOpaque() {
        return isOpaque(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void glTexImage2D(int i, int i2) {
        glTexImage2D(this.gifInfoPtr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void glTexSubImage2D(int i, int i2) {
        glTexSubImage2D(this.gifInfoPtr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startDecoderThread() {
        startDecoderThread(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stopDecoderThread() {
        stopDecoderThread(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initTexImageDescriptor() {
        initTexImageDescriptor(this.gifInfoPtr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void seekToFrameGL(int i) {
        throwIfFrameIndexOutOfBounds(i);
        seekToFrameGL(this.gifInfoPtr, i);
    }

    private void throwIfFrameIndexOutOfBounds(int i) {
        int numberOfFrames = getNumberOfFrames(this.gifInfoPtr);
        if (i < 0 || i >= numberOfFrames) {
            throw new IndexOutOfBoundsException("Frame index is not in range <0;" + numberOfFrames + Typography.greater);
        }
    }
}
