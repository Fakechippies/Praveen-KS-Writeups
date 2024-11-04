package pl.droidsonroids.gif;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import pl.droidsonroids.gif.GifViewUtils;

/* loaded from: classes.dex */
public class GifImageButton extends ImageButton {
    private boolean mFreezesAnimation;

    public void setFreezesAnimation(boolean z) {
        this.mFreezesAnimation = z;
    }

    public GifImageButton(Context context) {
        super(context);
    }

    public GifImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        postInit(GifViewUtils.initImageView(this, attributeSet, 0, 0));
    }

    public GifImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        postInit(GifViewUtils.initImageView(this, attributeSet, i, 0));
    }

    public GifImageButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        postInit(GifViewUtils.initImageView(this, attributeSet, i, i2));
    }

    private void postInit(GifViewUtils.GifImageViewAttributes gifImageViewAttributes) {
        this.mFreezesAnimation = gifImageViewAttributes.freezesAnimation;
        if (gifImageViewAttributes.mSourceResId > 0) {
            super.setImageResource(gifImageViewAttributes.mSourceResId);
        }
        if (gifImageViewAttributes.mBackgroundResId > 0) {
            super.setBackgroundResource(gifImageViewAttributes.mBackgroundResId);
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        if (GifViewUtils.setGifImageUri(this, uri)) {
            return;
        }
        super.setImageURI(uri);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        if (GifViewUtils.setResource(this, true, i)) {
            return;
        }
        super.setImageResource(i);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        if (GifViewUtils.setResource(this, false, i)) {
            return;
        }
        super.setBackgroundResource(i);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new GifViewSavedState(super.onSaveInstanceState(), this.mFreezesAnimation ? getDrawable() : null, this.mFreezesAnimation ? getBackground() : null);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        gifViewSavedState.restoreState(getDrawable(), 0);
        gifViewSavedState.restoreState(getBackground(), 1);
    }
}
