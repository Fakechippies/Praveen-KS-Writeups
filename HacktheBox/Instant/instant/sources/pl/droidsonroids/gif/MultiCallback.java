package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class MultiCallback implements Drawable.Callback {
    private final CopyOnWriteArrayList<CallbackWeakReference> mCallbacks;
    private final boolean mUseViewInvalidate;

    public MultiCallback() {
        this(false);
    }

    public MultiCallback(boolean z) {
        this.mCallbacks = new CopyOnWriteArrayList<>();
        this.mUseViewInvalidate = z;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) callbackWeakReference.get();
            if (callback != null) {
                if (this.mUseViewInvalidate && (callback instanceof View)) {
                    ((View) callback).invalidate();
                } else {
                    callback.invalidateDrawable(drawable);
                }
            } else {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) callbackWeakReference.get();
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j);
            } else {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i);
            Drawable.Callback callback = (Drawable.Callback) callbackWeakReference.get();
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            } else {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    public void addView(Drawable.Callback callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i);
            if (((Drawable.Callback) callbackWeakReference.get()) == null) {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
        this.mCallbacks.addIfAbsent(new CallbackWeakReference(callback));
    }

    public void removeView(Drawable.Callback callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            CallbackWeakReference callbackWeakReference = this.mCallbacks.get(i);
            Drawable.Callback callback2 = (Drawable.Callback) callbackWeakReference.get();
            if (callback2 == null || callback2 == callback) {
                this.mCallbacks.remove(callbackWeakReference);
            }
        }
    }

    /* loaded from: classes.dex */
    static final class CallbackWeakReference extends WeakReference<Drawable.Callback> {
        CallbackWeakReference(Drawable.Callback callback) {
            super(callback);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && get() == ((CallbackWeakReference) obj).get();
        }

        public int hashCode() {
            Drawable.Callback callback = (Drawable.Callback) get();
            if (callback != null) {
                return callback.hashCode();
            }
            return 0;
        }
    }
}
