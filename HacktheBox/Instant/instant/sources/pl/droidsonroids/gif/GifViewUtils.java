package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class GifViewUtils {
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final List<String> SUPPORTED_RESOURCE_TYPE_NAMES = Arrays.asList("raw", "drawable", "mipmap");

    private GifViewUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static GifImageViewAttributes initImageView(ImageView imageView, AttributeSet attributeSet, int i, int i2) {
        if (attributeSet != null && !imageView.isInEditMode()) {
            GifImageViewAttributes gifImageViewAttributes = new GifImageViewAttributes(imageView, attributeSet, i, i2);
            int i3 = gifImageViewAttributes.mLoopCount;
            if (i3 >= 0) {
                applyLoopCount(i3, imageView.getDrawable());
                applyLoopCount(i3, imageView.getBackground());
            }
            return gifImageViewAttributes;
        }
        return new GifImageViewAttributes();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void applyLoopCount(int i, Drawable drawable) {
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).setLoopCount(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean setResource(ImageView imageView, boolean z, int i) {
        Resources resources = imageView.getResources();
        if (resources != null) {
            try {
                if (!SUPPORTED_RESOURCE_TYPE_NAMES.contains(resources.getResourceTypeName(i))) {
                    return false;
                }
                GifDrawable gifDrawable = new GifDrawable(resources, i);
                if (z) {
                    imageView.setImageDrawable(gifDrawable);
                    return true;
                }
                imageView.setBackground(gifDrawable);
                return true;
            } catch (Resources.NotFoundException | IOException unused) {
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean setGifImageUri(ImageView imageView, Uri uri) {
        if (uri == null) {
            return false;
        }
        try {
            imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static float getDensityScale(Resources resources, int i) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.density;
        if (i2 == 0) {
            i2 = 160;
        } else if (i2 == 65535) {
            i2 = 0;
        }
        int i3 = resources.getDisplayMetrics().densityDpi;
        if (i2 <= 0 || i3 <= 0) {
            return 1.0f;
        }
        return i3 / i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class GifViewAttributes {
        boolean freezesAnimation;
        final int mLoopCount;

        /* JADX INFO: Access modifiers changed from: package-private */
        public GifViewAttributes(View view, AttributeSet attributeSet, int i, int i2) {
            TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.GifView, i, i2);
            this.freezesAnimation = obtainStyledAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
            this.mLoopCount = obtainStyledAttributes.getInt(R.styleable.GifView_loopCount, -1);
            obtainStyledAttributes.recycle();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public GifViewAttributes() {
            this.freezesAnimation = false;
            this.mLoopCount = -1;
        }
    }

    /* loaded from: classes.dex */
    static class GifImageViewAttributes extends GifViewAttributes {
        final int mBackgroundResId;
        final int mSourceResId;

        GifImageViewAttributes(ImageView imageView, AttributeSet attributeSet, int i, int i2) {
            super(imageView, attributeSet, i, i2);
            this.mSourceResId = getResourceId(imageView, attributeSet, true);
            this.mBackgroundResId = getResourceId(imageView, attributeSet, false);
        }

        GifImageViewAttributes() {
            this.mSourceResId = 0;
            this.mBackgroundResId = 0;
        }

        private static int getResourceId(ImageView imageView, AttributeSet attributeSet, boolean z) {
            int attributeResourceValue = attributeSet.getAttributeResourceValue(GifViewUtils.ANDROID_NS, z ? "src" : "background", 0);
            if (attributeResourceValue > 0) {
                if (GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(imageView.getResources().getResourceTypeName(attributeResourceValue)) && !GifViewUtils.setResource(imageView, z, attributeResourceValue)) {
                    return attributeResourceValue;
                }
            }
            return 0;
        }
    }
}
