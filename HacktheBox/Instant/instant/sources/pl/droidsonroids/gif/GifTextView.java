package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;
import java.io.IOException;
import pl.droidsonroids.gif.GifViewUtils;

/* loaded from: classes.dex */
public class GifTextView extends TextView {
    private GifViewUtils.GifViewAttributes viewAttributes;

    public GifTextView(Context context) {
        super(context);
    }

    public GifTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0, 0);
    }

    public GifTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i, 0);
    }

    public GifTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet, i, i2);
    }

    private static void setDrawablesVisible(Drawable[] drawableArr, boolean z) {
        for (Drawable drawable : drawableArr) {
            if (drawable != null) {
                drawable.setVisible(z, false);
            }
        }
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        if (attributeSet != null) {
            Drawable gifOrDefaultDrawable = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable gifOrDefaultDrawable2 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable gifOrDefaultDrawable3 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable gifOrDefaultDrawable4 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable gifOrDefaultDrawable5 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable gifOrDefaultDrawable6 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            if (getLayoutDirection() == 0) {
                if (gifOrDefaultDrawable5 != null) {
                    gifOrDefaultDrawable = gifOrDefaultDrawable5;
                }
                if (gifOrDefaultDrawable6 == null) {
                    gifOrDefaultDrawable6 = gifOrDefaultDrawable3;
                }
            } else {
                if (gifOrDefaultDrawable5 != null) {
                    gifOrDefaultDrawable3 = gifOrDefaultDrawable5;
                }
                if (gifOrDefaultDrawable6 == null) {
                    gifOrDefaultDrawable6 = gifOrDefaultDrawable;
                }
                gifOrDefaultDrawable = gifOrDefaultDrawable3;
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(gifOrDefaultDrawable, gifOrDefaultDrawable2, gifOrDefaultDrawable6, gifOrDefaultDrawable4);
            setBackground(getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", 0)));
            this.viewAttributes = new GifViewUtils.GifViewAttributes(this, attributeSet, i, i2);
            applyGifViewAttributes();
        }
        this.viewAttributes = new GifViewUtils.GifViewAttributes();
    }

    private void applyGifViewAttributes() {
        if (this.viewAttributes.mLoopCount < 0) {
            return;
        }
        for (Drawable drawable : getCompoundDrawables()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable);
        }
        for (Drawable drawable2 : getCompoundDrawablesRelative()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable2);
        }
        GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, getBackground());
    }

    private Drawable getGifOrDefaultDrawable(int i) {
        if (i == 0) {
            return null;
        }
        Resources resources = getResources();
        String resourceTypeName = resources.getResourceTypeName(i);
        if (!isInEditMode() && GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
            try {
                return new GifDrawable(resources, i);
            } catch (Resources.NotFoundException | IOException unused) {
            }
        }
        return resources.getDrawable(i, getContext().getTheme());
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        setCompoundDrawablesWithIntrinsicBounds(getGifOrDefaultDrawable(i), getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4));
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(getGifOrDefaultDrawable(i), getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4));
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        Drawable[] drawableArr = new Drawable[7];
        if (this.viewAttributes.freezesAnimation) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            System.arraycopy(compoundDrawables, 0, drawableArr, 0, compoundDrawables.length);
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            drawableArr[4] = compoundDrawablesRelative[0];
            drawableArr[5] = compoundDrawablesRelative[2];
            drawableArr[6] = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), drawableArr);
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        Drawable[] compoundDrawables = getCompoundDrawables();
        gifViewSavedState.restoreState(compoundDrawables[0], 0);
        gifViewSavedState.restoreState(compoundDrawables[1], 1);
        gifViewSavedState.restoreState(compoundDrawables[2], 2);
        gifViewSavedState.restoreState(compoundDrawables[3], 3);
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        gifViewSavedState.restoreState(compoundDrawablesRelative[0], 4);
        gifViewSavedState.restoreState(compoundDrawablesRelative[2], 5);
        gifViewSavedState.restoreState(getBackground(), 6);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setCompoundDrawablesVisible(true);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setCompoundDrawablesVisible(false);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        setBackground(getGifOrDefaultDrawable(i));
    }

    private void setCompoundDrawablesVisible(boolean z) {
        setDrawablesVisible(getCompoundDrawables(), z);
        setDrawablesVisible(getCompoundDrawablesRelative(), z);
    }

    public void setFreezesAnimation(boolean z) {
        this.viewAttributes.freezesAnimation = z;
    }
}
