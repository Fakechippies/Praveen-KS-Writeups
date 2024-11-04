package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GifViewSavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<GifViewSavedState> CREATOR = new Parcelable.Creator<GifViewSavedState>() { // from class: pl.droidsonroids.gif.GifViewSavedState.1
        @Override // android.os.Parcelable.Creator
        public GifViewSavedState createFromParcel(Parcel parcel) {
            return new GifViewSavedState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GifViewSavedState[] newArray(int i) {
            return new GifViewSavedState[i];
        }
    };
    final long[][] mStates;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifViewSavedState(Parcelable parcelable, Drawable... drawableArr) {
        super(parcelable);
        this.mStates = new long[drawableArr.length];
        for (int i = 0; i < drawableArr.length; i++) {
            Drawable drawable = drawableArr[i];
            if (drawable instanceof GifDrawable) {
                this.mStates[i] = ((GifDrawable) drawable).mNativeInfoHandle.getSavedState();
            } else {
                this.mStates[i] = null;
            }
        }
    }

    private GifViewSavedState(Parcel parcel) {
        super(parcel);
        this.mStates = new long[parcel.readInt()];
        int i = 0;
        while (true) {
            long[][] jArr = this.mStates;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = parcel.createLongArray();
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifViewSavedState(Parcelable parcelable, long[] jArr) {
        super(parcelable);
        this.mStates = r2;
        long[][] jArr2 = {jArr};
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mStates.length);
        for (long[] jArr : this.mStates) {
            parcel.writeLongArray(jArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void restoreState(Drawable drawable, int i) {
        if (this.mStates[i] == null || !(drawable instanceof GifDrawable)) {
            return;
        }
        ((GifDrawable) drawable).startAnimation(r3.mNativeInfoHandle.restoreSavedState(this.mStates[i], r3.mBuffer));
    }
}
