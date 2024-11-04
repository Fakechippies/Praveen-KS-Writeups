package kotlinx.coroutines.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;

/* compiled from: FastServiceLoader.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"ANDROID_DETECTED", "", "getANDROID_DETECTED", "()Z", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class FastServiceLoaderKt {
    private static final boolean ANDROID_DETECTED;

    public static final boolean getANDROID_DETECTED() {
        return ANDROID_DETECTED;
    }

    static {
        Object m148constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            m148constructorimpl = Result.m148constructorimpl(Class.forName("android.os.Build"));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            m148constructorimpl = Result.m148constructorimpl(ResultKt.createFailure(th));
        }
        ANDROID_DETECTED = Result.m155isSuccessimpl(m148constructorimpl);
    }
}
