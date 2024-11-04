package pl.droidsonroids.relinker;

import java.util.Arrays;

/* loaded from: classes.dex */
public class MissingLibraryException extends RuntimeException {
    public MissingLibraryException(String str, String[] strArr, String[] strArr2) {
        super("Could not find '" + str + "'. Looked for: " + Arrays.toString(strArr) + ", but only found: " + Arrays.toString(strArr2) + ".");
    }
}
