package pl.droidsonroids.relinker;

import android.os.Build;
import pl.droidsonroids.relinker.ReLinker;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class SystemLibraryLoader implements ReLinker.LibraryLoader {
    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public void loadLibrary(String str) {
        System.loadLibrary(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public void loadPath(String str) {
        System.load(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String mapLibraryName(String str) {
        return (str.startsWith("lib") && str.endsWith(".so")) ? str : System.mapLibraryName(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String unmapLibraryName(String str) {
        return str.substring(3, str.length() - 3);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String[] supportedAbis() {
        if (Build.SUPPORTED_ABIS.length > 0) {
            return Build.SUPPORTED_ABIS;
        }
        if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
            return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        return new String[]{Build.CPU_ABI};
    }
}
