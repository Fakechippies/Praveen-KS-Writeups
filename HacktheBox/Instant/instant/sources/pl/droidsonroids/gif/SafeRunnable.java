package pl.droidsonroids.gif;

import java.lang.Thread;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class SafeRunnable implements Runnable {
    final GifDrawable mGifDrawable;

    abstract void doWork();

    /* JADX INFO: Access modifiers changed from: package-private */
    public SafeRunnable(GifDrawable gifDrawable) {
        this.mGifDrawable = gifDrawable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.mGifDrawable.isRecycled()) {
                return;
            }
            doWork();
        } catch (Throwable th) {
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
            }
            throw th;
        }
    }
}
