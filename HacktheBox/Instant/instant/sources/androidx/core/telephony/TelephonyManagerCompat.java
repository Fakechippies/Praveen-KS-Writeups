package androidx.core.telephony;

import android.os.Build;
import android.telephony.TelephonyManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class TelephonyManagerCompat {
    private static Method sGetDeviceIdMethod;
    private static Method sGetSubIdMethod;

    public static String getImei(TelephonyManager telephonyManager) {
        return Api26Impl.getImei(telephonyManager);
    }

    public static int getSubscriptionId(TelephonyManager telephonyManager) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.getSubscriptionId(telephonyManager);
        }
        try {
            if (sGetSubIdMethod == null) {
                Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getSubId", new Class[0]);
                sGetSubIdMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            Integer num = (Integer) sGetSubIdMethod.invoke(telephonyManager, new Object[0]);
            if (num == null || num.intValue() == -1) {
                return Integer.MAX_VALUE;
            }
            return num.intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return Integer.MAX_VALUE;
        }
    }

    private TelephonyManagerCompat() {
    }

    /* loaded from: classes.dex */
    private static class Api30Impl {
        private Api30Impl() {
        }

        static int getSubscriptionId(TelephonyManager telephonyManager) {
            return telephonyManager.getSubscriptionId();
        }
    }

    /* loaded from: classes.dex */
    private static class Api26Impl {
        private Api26Impl() {
        }

        static String getImei(TelephonyManager telephonyManager) {
            return telephonyManager.getImei();
        }
    }

    /* loaded from: classes.dex */
    private static class Api23Impl {
        private Api23Impl() {
        }

        static String getDeviceId(TelephonyManager telephonyManager, int i) {
            return telephonyManager.getDeviceId(i);
        }
    }
}
