package androidx.core.os;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ParcelCompat {
    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }

    public static <T> void readList(Parcel parcel, List<? super T> list, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            TiramisuImpl.readList(parcel, list, classLoader, cls);
        } else {
            parcel.readList(list, classLoader);
        }
    }

    public static <T> ArrayList<T> readArrayList(Parcel parcel, ClassLoader classLoader, Class<? extends T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return TiramisuImpl.readArrayList(parcel, classLoader, cls);
        }
        return parcel.readArrayList(classLoader);
    }

    public static <T> T[] readArray(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return (T[]) TiramisuImpl.readArray(parcel, classLoader, cls);
        }
        return (T[]) parcel.readArray(classLoader);
    }

    public static <T> SparseArray<T> readSparseArray(Parcel parcel, ClassLoader classLoader, Class<? extends T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return TiramisuImpl.readSparseArray(parcel, classLoader, cls);
        }
        return parcel.readSparseArray(classLoader);
    }

    public static <K, V> void readMap(Parcel parcel, Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
        if (BuildCompat.isAtLeastT()) {
            TiramisuImpl.readMap(parcel, map, classLoader, cls, cls2);
        } else {
            parcel.readMap(map, classLoader);
        }
    }

    public static <K, V> HashMap<K, V> readHashMap(Parcel parcel, ClassLoader classLoader, Class<? extends K> cls, Class<? extends V> cls2) {
        if (BuildCompat.isAtLeastT()) {
            return TiramisuImpl.readHashMap(parcel, classLoader, cls, cls2);
        }
        return parcel.readHashMap(classLoader);
    }

    public static <T extends Parcelable> T readParcelable(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return (T) TiramisuImpl.readParcelable(parcel, classLoader, cls);
        }
        return (T) parcel.readParcelable(classLoader);
    }

    public static <T> Parcelable.Creator<T> readParcelableCreator(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return TiramisuImpl.readParcelableCreator(parcel, classLoader, cls);
        }
        return (Parcelable.Creator<T>) Api30Impl.readParcelableCreator(parcel, classLoader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T[] readParcelableArray(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return (T[]) TiramisuImpl.readParcelableArray(parcel, classLoader, cls);
        }
        return (T[]) parcel.readParcelableArray(classLoader);
    }

    public static <T> List<T> readParcelableList(Parcel parcel, List<T> list, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return TiramisuImpl.readParcelableList(parcel, list, classLoader, cls);
        }
        return Api29Impl.readParcelableList(parcel, list, classLoader);
    }

    public static <T extends Serializable> T readSerializable(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
        if (BuildCompat.isAtLeastT()) {
            return (T) TiramisuImpl.readSerializable(parcel, classLoader, cls);
        }
        return (T) parcel.readSerializable();
    }

    private ParcelCompat() {
    }

    /* loaded from: classes.dex */
    static class Api29Impl {
        private Api29Impl() {
        }

        static final <T extends Parcelable> List<T> readParcelableList(Parcel parcel, List<T> list, ClassLoader classLoader) {
            return parcel.readParcelableList(list, classLoader);
        }
    }

    /* loaded from: classes.dex */
    static class Api30Impl {
        private Api30Impl() {
        }

        static final Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader) {
            return parcel.readParcelableCreator(classLoader);
        }
    }

    /* loaded from: classes.dex */
    static class TiramisuImpl {
        private TiramisuImpl() {
        }

        static <T extends Serializable> T readSerializable(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
            return (T) parcel.readSerializable(classLoader, cls);
        }

        static <T extends Parcelable> T readParcelable(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
            return (T) parcel.readParcelable(classLoader, cls);
        }

        public static <T> Parcelable.Creator<T> readParcelableCreator(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
            return parcel.readParcelableCreator(classLoader, cls);
        }

        static <T> T[] readParcelableArray(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
            return (T[]) parcel.readParcelableArray(classLoader, cls);
        }

        static <T> List<T> readParcelableList(Parcel parcel, List<T> list, ClassLoader classLoader, Class<T> cls) {
            return parcel.readParcelableList(list, classLoader, cls);
        }

        public static <T> void readList(Parcel parcel, List<? super T> list, ClassLoader classLoader, Class<T> cls) {
            parcel.readList(list, classLoader, cls);
        }

        public static <T> ArrayList<T> readArrayList(Parcel parcel, ClassLoader classLoader, Class<? extends T> cls) {
            return parcel.readArrayList(classLoader, cls);
        }

        public static <T> T[] readArray(Parcel parcel, ClassLoader classLoader, Class<T> cls) {
            return (T[]) parcel.readArray(classLoader, cls);
        }

        public static <T> SparseArray<T> readSparseArray(Parcel parcel, ClassLoader classLoader, Class<? extends T> cls) {
            return parcel.readSparseArray(classLoader, cls);
        }

        public static <K, V> void readMap(Parcel parcel, Map<? super K, ? super V> map, ClassLoader classLoader, Class<K> cls, Class<V> cls2) {
            parcel.readMap(map, classLoader, cls, cls2);
        }

        public static <V, K> HashMap<K, V> readHashMap(Parcel parcel, ClassLoader classLoader, Class<? extends K> cls, Class<? extends V> cls2) {
            return parcel.readHashMap(classLoader, cls, cls2);
        }
    }
}
