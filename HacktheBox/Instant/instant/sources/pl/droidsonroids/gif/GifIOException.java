package pl.droidsonroids.gif;

import java.io.IOException;

/* loaded from: classes.dex */
public class GifIOException extends IOException {
    private static final long serialVersionUID = 13038402904505L;
    private final String mErrnoMessage;
    public final GifError reason;

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.mErrnoMessage == null) {
            return this.reason.getFormattedDescription();
        }
        return this.reason.getFormattedDescription() + ": " + this.mErrnoMessage;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GifIOException(int i, String str) {
        this.reason = GifError.fromCode(i);
        this.mErrnoMessage = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static GifIOException fromCode(int i) {
        if (i == GifError.NO_ERROR.errorCode) {
            return null;
        }
        return new GifIOException(i, null);
    }
}
