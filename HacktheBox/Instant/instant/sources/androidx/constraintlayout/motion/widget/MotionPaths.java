package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class MotionPaths implements Comparable<MotionPaths> {
    static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes;
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo;
    int mDrawPath;
    Easing mKeyFrameEasing;
    int mMode;
    int mPathMotionArc;
    float mPathRotate;
    float mProgress;
    float mRelativeAngle;
    MotionController mRelativeToController;
    double[] mTempDelta;
    double[] mTempValue;
    float position;
    float time;
    float width;
    float x;
    float y;

    private static final float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((x - cx) * sin) + ((y - cy) * cos) + cy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBounds(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public MotionPaths() {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
    }

    void initCartesian(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width;
        float f5 = startTimePoint.width;
        float f6 = endTimePoint.height;
        float f7 = startTimePoint.height;
        this.position = this.time;
        float f8 = startTimePoint.x;
        float f9 = startTimePoint.y;
        float f10 = (endTimePoint.x + (f4 / 2.0f)) - ((f5 / 2.0f) + f8);
        float f11 = (endTimePoint.y + (f6 / 2.0f)) - (f9 + (f7 / 2.0f));
        float f12 = ((f4 - f5) * f2) / 2.0f;
        this.x = (int) ((f8 + (f10 * f)) - f12);
        float f13 = ((f6 - f7) * f3) / 2.0f;
        this.y = (int) ((f9 + (f11 * f)) - f13);
        this.width = (int) (f5 + r9);
        this.height = (int) (f7 + r12);
        float f14 = Float.isNaN(c.mPercentX) ? f : c.mPercentX;
        float f15 = Float.isNaN(c.mAltPercentY) ? 0.0f : c.mAltPercentY;
        if (!Float.isNaN(c.mPercentY)) {
            f = c.mPercentY;
        }
        float f16 = Float.isNaN(c.mAltPercentX) ? 0.0f : c.mAltPercentX;
        this.mMode = 0;
        this.x = (int) (((startTimePoint.x + (f14 * f10)) + (f16 * f11)) - f12);
        this.y = (int) (((startTimePoint.y + (f10 * f15)) + (f11 * f)) - f13);
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public MotionPaths(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        this.mDrawPath = 0;
        this.mPathRotate = Float.NaN;
        this.mProgress = Float.NaN;
        this.mPathMotionArc = Key.UNSET;
        this.mAnimateRelativeTo = Key.UNSET;
        this.mRelativeAngle = Float.NaN;
        this.mRelativeToController = null;
        this.attributes = new LinkedHashMap<>();
        this.mMode = 0;
        this.mTempValue = new double[18];
        this.mTempDelta = new double[18];
        if (startTimePoint.mAnimateRelativeTo != Key.UNSET) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
            return;
        }
        int i = c.mPositionType;
        if (i == 1) {
            initPath(c, startTimePoint, endTimePoint);
        } else if (i == 2) {
            initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
        } else {
            initCartesian(c, startTimePoint, endTimePoint);
        }
    }

    void initPolar(int parentWidth, int parentHeight, KeyPosition c, MotionPaths s, MotionPaths e) {
        float min;
        float f;
        float f2 = c.mFramePosition / 100.0f;
        this.time = f2;
        this.mDrawPath = c.mDrawPath;
        this.mMode = c.mPositionType;
        float f3 = Float.isNaN(c.mPercentWidth) ? f2 : c.mPercentWidth;
        float f4 = Float.isNaN(c.mPercentHeight) ? f2 : c.mPercentHeight;
        float f5 = e.width;
        float f6 = s.width;
        float f7 = e.height;
        float f8 = s.height;
        this.position = this.time;
        this.width = (int) (f6 + ((f5 - f6) * f3));
        this.height = (int) (f8 + ((f7 - f8) * f4));
        int i = c.mPositionType;
        if (i == 1) {
            float f9 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
            float f10 = e.x;
            float f11 = s.x;
            this.x = (f9 * (f10 - f11)) + f11;
            if (!Float.isNaN(c.mPercentY)) {
                f2 = c.mPercentY;
            }
            float f12 = e.y;
            float f13 = s.y;
            this.y = (f2 * (f12 - f13)) + f13;
        } else if (i == 2) {
            if (Float.isNaN(c.mPercentX)) {
                float f14 = e.x;
                float f15 = s.x;
                min = ((f14 - f15) * f2) + f15;
            } else {
                min = Math.min(f4, f3) * c.mPercentX;
            }
            this.x = min;
            if (Float.isNaN(c.mPercentY)) {
                float f16 = e.y;
                float f17 = s.y;
                f = (f2 * (f16 - f17)) + f17;
            } else {
                f = c.mPercentY;
            }
            this.y = f;
        } else {
            float f18 = Float.isNaN(c.mPercentX) ? f2 : c.mPercentX;
            float f19 = e.x;
            float f20 = s.x;
            this.x = (f18 * (f19 - f20)) + f20;
            if (!Float.isNaN(c.mPercentY)) {
                f2 = c.mPercentY;
            }
            float f21 = e.y;
            float f22 = s.y;
            this.y = (f2 * (f21 - f22)) + f22;
        }
        this.mAnimateRelativeTo = s.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public void setupRelative(MotionController mc, MotionPaths relative) {
        double d = ((this.x + (this.width / 2.0f)) - relative.x) - (relative.width / 2.0f);
        double d2 = ((this.y + (this.height / 2.0f)) - relative.y) - (relative.height / 2.0f);
        this.mRelativeToController = mc;
        this.x = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.y = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.y = (float) Math.toRadians(this.mRelativeAngle);
        }
    }

    void initScreen(int parentWidth, int parentHeight, KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width;
        float f5 = startTimePoint.width;
        float f6 = endTimePoint.height;
        float f7 = startTimePoint.height;
        this.position = this.time;
        float f8 = startTimePoint.x;
        float f9 = startTimePoint.y;
        float f10 = endTimePoint.x + (f4 / 2.0f);
        float f11 = endTimePoint.y + (f6 / 2.0f);
        float f12 = (f4 - f5) * f2;
        this.x = (int) ((f8 + ((f10 - ((f5 / 2.0f) + f8)) * f)) - (f12 / 2.0f));
        float f13 = (f6 - f7) * f3;
        this.y = (int) ((f9 + ((f11 - (f9 + (f7 / 2.0f))) * f)) - (f13 / 2.0f));
        this.width = (int) (f5 + f12);
        this.height = (int) (f7 + f13);
        this.mMode = 2;
        if (!Float.isNaN(c.mPercentX)) {
            this.x = (int) (c.mPercentX * ((int) (parentWidth - this.width)));
        }
        if (!Float.isNaN(c.mPercentY)) {
            this.y = (int) (c.mPercentY * ((int) (parentHeight - this.height)));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    void initPath(KeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        float f = c.mFramePosition / 100.0f;
        this.time = f;
        this.mDrawPath = c.mDrawPath;
        float f2 = Float.isNaN(c.mPercentWidth) ? f : c.mPercentWidth;
        float f3 = Float.isNaN(c.mPercentHeight) ? f : c.mPercentHeight;
        float f4 = endTimePoint.width - startTimePoint.width;
        float f5 = endTimePoint.height - startTimePoint.height;
        this.position = this.time;
        if (!Float.isNaN(c.mPercentX)) {
            f = c.mPercentX;
        }
        float f6 = startTimePoint.x;
        float f7 = startTimePoint.width;
        float f8 = startTimePoint.y;
        float f9 = startTimePoint.height;
        float f10 = (endTimePoint.x + (endTimePoint.width / 2.0f)) - ((f7 / 2.0f) + f6);
        float f11 = (endTimePoint.y + (endTimePoint.height / 2.0f)) - ((f9 / 2.0f) + f8);
        float f12 = f10 * f;
        float f13 = (f4 * f2) / 2.0f;
        this.x = (int) ((f6 + f12) - f13);
        float f14 = f * f11;
        float f15 = (f5 * f3) / 2.0f;
        this.y = (int) ((f8 + f14) - f15);
        this.width = (int) (f7 + r7);
        this.height = (int) (f9 + r8);
        float f16 = Float.isNaN(c.mPercentY) ? 0.0f : c.mPercentY;
        this.mMode = 1;
        float f17 = (int) ((startTimePoint.x + f12) - f13);
        this.x = f17;
        float f18 = (int) ((startTimePoint.y + f14) - f15);
        this.x = f17 + ((-f11) * f16);
        this.y = f18 + (f10 * f16);
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    private boolean diff(float a, float b) {
        return (Float.isNaN(a) || Float.isNaN(b)) ? Float.isNaN(a) != Float.isNaN(b) : Math.abs(a - b) > 1.0E-6f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean diff = diff(this.x, points.x);
        boolean diff2 = diff(this.y, points.y);
        mask[0] = mask[0] | diff(this.position, points.position);
        boolean z = diff | diff2 | arcMode;
        mask[1] = mask[1] | z;
        mask[2] = z | mask[2];
        mask[3] = mask[3] | diff(this.width, points.width);
        mask[4] = diff(this.height, points.height) | mask[4];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f5;
            } else if (i2 == 2) {
                f2 = f5;
            } else if (i2 == 3) {
                f3 = f5;
            } else if (i2 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p, fArr, new float[2]);
            float f6 = fArr[0];
            float f7 = fArr[1];
            double d = f6;
            double d2 = f;
            double d3 = f2;
            f = (float) ((d + (Math.sin(d3) * d2)) - (f3 / 2.0f));
            f2 = (float) ((f7 - (d2 * Math.cos(d3))) - (f4 / 2.0f));
        }
        point[offset] = f + (f3 / 2.0f) + 0.0f;
        point[offset + 1] = f2 + (f4 / 2.0f) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float f;
        float f2 = this.x;
        float f3 = this.y;
        float f4 = this.width;
        float f5 = this.height;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float f10 = (float) data[i];
            float f11 = (float) vdata[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f2 = f10;
                f6 = f11;
            } else if (i2 == 2) {
                f3 = f10;
                f8 = f11;
            } else if (i2 == 3) {
                f4 = f10;
                f7 = f11;
            } else if (i2 == 4) {
                f5 = f10;
                f9 = f11;
            }
        }
        float f12 = 2.0f;
        float f13 = (f7 / 2.0f) + f6;
        float f14 = (f9 / 2.0f) + f8;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(p, fArr, fArr2);
            float f15 = fArr[0];
            float f16 = fArr[1];
            float f17 = fArr2[0];
            float f18 = fArr2[1];
            double d = f2;
            double d2 = f3;
            f = f4;
            float sin = (float) ((f15 + (Math.sin(d2) * d)) - (f4 / 2.0f));
            float cos = (float) ((f16 - (d * Math.cos(d2))) - (f5 / 2.0f));
            double d3 = f6;
            double d4 = f8;
            float sin2 = (float) (f17 + (Math.sin(d2) * d3) + (Math.cos(d2) * d4));
            f14 = (float) ((f18 - (d3 * Math.cos(d2))) + (Math.sin(d2) * d4));
            f13 = sin2;
            f2 = sin;
            f3 = cos;
            f12 = 2.0f;
        } else {
            f = f4;
        }
        point[0] = f2 + (f / f12) + 0.0f;
        point[1] = f3 + (f5 / f12) + 0.0f;
        velocity[0] = f13;
        velocity[1] = f14;
    }

    void getCenterVelocity(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f5;
            } else if (i2 == 2) {
                f2 = f5;
            } else if (i2 == 3) {
                f3 = f5;
            } else if (i2 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            motionController.getCenter(p, fArr, new float[2]);
            float f6 = fArr[0];
            float f7 = fArr[1];
            double d = f6;
            double d2 = f;
            double d3 = f2;
            f = (float) ((d + (Math.sin(d3) * d2)) - (f3 / 2.0f));
            f2 = (float) ((f7 - (d2 * Math.cos(d3))) - (f4 / 2.0f));
        }
        point[offset] = f + (f3 / 2.0f) + 0.0f;
        point[offset + 1] = f2 + (f4 / 2.0f) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f = this.width;
        float f2 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f3 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 3) {
                f = f3;
            } else if (i2 == 4) {
                f2 = f3;
            }
        }
        point[offset] = f;
        point[offset + 1] = f2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public void setView(float position, View view, int[] toUse, double[] data, double[] slope, double[] cycle, boolean mForceMeasure) {
        float f;
        float f2;
        float f3 = this.x;
        float f4 = this.y;
        float f5 = this.width;
        float f6 = this.height;
        if (toUse.length != 0 && this.mTempValue.length <= toUse[toUse.length - 1]) {
            int i = toUse[toUse.length - 1] + 1;
            this.mTempValue = new double[i];
            this.mTempDelta = new double[i];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i2 = 0; i2 < toUse.length; i2++) {
            double[] dArr = this.mTempValue;
            int i3 = toUse[i2];
            dArr[i3] = data[i2];
            this.mTempDelta[i3] = slope[i2];
        }
        float f7 = Float.NaN;
        int i4 = 0;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        while (true) {
            double[] dArr2 = this.mTempValue;
            if (i4 >= dArr2.length) {
                break;
            }
            if (Double.isNaN(dArr2[i4]) && (cycle == null || cycle[i4] == 0.0d)) {
                f2 = f7;
            } else {
                double d = cycle != null ? cycle[i4] : 0.0d;
                if (!Double.isNaN(this.mTempValue[i4])) {
                    d = this.mTempValue[i4] + d;
                }
                f2 = f7;
                float f12 = (float) d;
                float f13 = (float) this.mTempDelta[i4];
                if (i4 == 1) {
                    f7 = f2;
                    f8 = f13;
                    f3 = f12;
                } else if (i4 == 2) {
                    f7 = f2;
                    f9 = f13;
                    f4 = f12;
                } else if (i4 == 3) {
                    f7 = f2;
                    f10 = f13;
                    f5 = f12;
                } else if (i4 == 4) {
                    f7 = f2;
                    f11 = f13;
                    f6 = f12;
                } else if (i4 == 5) {
                    f7 = f12;
                }
                i4++;
            }
            f7 = f2;
            i4++;
        }
        float f14 = f7;
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(position, fArr, fArr2);
            float f15 = fArr[0];
            float f16 = fArr[1];
            float f17 = fArr2[0];
            float f18 = fArr2[1];
            double d2 = f3;
            double d3 = f4;
            float sin = (float) ((f15 + (Math.sin(d3) * d2)) - (f5 / 2.0f));
            f = f6;
            float cos = (float) ((f16 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
            double d4 = f8;
            double d5 = f9;
            float sin2 = (float) (f17 + (Math.sin(d3) * d4) + (Math.cos(d3) * d2 * d5));
            float cos2 = (float) ((f18 - (d4 * Math.cos(d3))) + (d2 * Math.sin(d3) * d5));
            if (slope.length >= 2) {
                slope[0] = sin2;
                slope[1] = cos2;
            }
            if (!Float.isNaN(f14)) {
                view.setRotation((float) (f14 + Math.toDegrees(Math.atan2(cos2, sin2))));
            }
            f3 = sin;
            f4 = cos;
        } else {
            f = f6;
            if (!Float.isNaN(f14)) {
                view.setRotation((float) (0.0f + f14 + Math.toDegrees(Math.atan2(f9 + (f11 / 2.0f), f8 + (f10 / 2.0f)))));
            }
        }
        if (view instanceof FloatLayout) {
            ((FloatLayout) view).layout(f3, f4, f5 + f3, f4 + f);
            return;
        }
        float f19 = f3 + 0.5f;
        int i5 = (int) f19;
        float f20 = f4 + 0.5f;
        int i6 = (int) f20;
        int i7 = (int) (f19 + f5);
        int i8 = (int) (f20 + f);
        int i9 = i7 - i5;
        int i10 = i8 - i6;
        if (i9 != view.getMeasuredWidth() || i10 != view.getMeasuredHeight() || mForceMeasure) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i9, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i10, BasicMeasure.EXACTLY));
        }
        view.layout(i5, i6, i7, i8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f5;
            } else if (i2 == 2) {
                f2 = f5;
            } else if (i2 == 3) {
                f3 = f5;
            } else if (i2 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.mRelativeToController;
        if (motionController != null) {
            float centerX = motionController.getCenterX();
            float centerY = this.mRelativeToController.getCenterY();
            double d = f;
            double d2 = f2;
            float sin = (float) ((centerX + (Math.sin(d2) * d)) - (f3 / 2.0f));
            f2 = (float) ((centerY - (d * Math.cos(d2))) - (f4 / 2.0f));
            f = sin;
        }
        float f6 = f3 + f;
        float f7 = f4 + f2;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        path[offset] = f + 0.0f;
        path[offset + 1] = f2 + 0.0f;
        path[offset + 2] = f6 + 0.0f;
        path[offset + 3] = f2 + 0.0f;
        path[offset + 4] = f6 + 0.0f;
        path[offset + 5] = f7 + 0.0f;
        path[offset + 6] = f + 0.0f;
        path[offset + 7] = f7 + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (int i = 0; i < toUse.length; i++) {
            float f5 = (float) deltaData[i];
            double d = data[i];
            int i2 = toUse[i];
            if (i2 == 1) {
                f = f5;
            } else if (i2 == 2) {
                f3 = f5;
            } else if (i2 == 3) {
                f2 = f5;
            } else if (i2 == 4) {
                f4 = f5;
            }
        }
        float f6 = f - ((0.0f * f2) / 2.0f);
        float f7 = f3 - ((0.0f * f4) / 2.0f);
        mAnchorDpDt[0] = (f6 * (1.0f - locationX)) + (((f2 * 1.0f) + f6) * locationX) + 0.0f;
        mAnchorDpDt[1] = (f7 * (1.0f - locationY)) + (((f4 * 1.0f) + f7) * locationY) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        float[] fArr = {this.position, this.x, this.y, this.width, this.height, this.mPathRotate};
        int i = 0;
        for (int i2 : toUse) {
            if (i2 < 6) {
                data[i] = fArr[r4];
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasCustomData(String name) {
        return this.attributes.containsKey(name);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        if (constraintAttribute == null) {
            return 0;
        }
        return constraintAttribute.numberOfInterpolatedValues();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute constraintAttribute = this.attributes.get(name);
        int i = 0;
        if (constraintAttribute == null) {
            return 0;
        }
        if (constraintAttribute.numberOfInterpolatedValues() == 1) {
            value[offset] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int numberOfInterpolatedValues = constraintAttribute.numberOfInterpolatedValues();
        constraintAttribute.getValuesToInterpolate(new float[numberOfInterpolatedValues]);
        while (i < numberOfInterpolatedValues) {
            value[offset] = r2[i];
            i++;
            offset++;
        }
        return numberOfInterpolatedValues;
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths o) {
        return Float.compare(this.position, o.position);
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathMotionArc = c.motion.mPathMotionArc;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateCircleAngleTo = c.motion.mAnimateCircleAngleTo;
        this.mProgress = c.propertySet.mProgress;
        this.mRelativeAngle = c.layout.circleAngle;
        for (String str : c.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = c.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.attributes.put(str, constraintAttribute);
            }
        }
    }

    public void configureRelativeTo(MotionController toOrbit) {
        toOrbit.getPos(this.mProgress);
    }
}
