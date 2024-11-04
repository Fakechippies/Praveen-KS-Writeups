package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class EmojiProcessor {
    private static final int ACTION_ADVANCE_BOTH = 1;
    private static final int ACTION_ADVANCE_END = 2;
    private static final int ACTION_FLUSH = 3;
    private final int[] mEmojiAsDefaultStyleExceptions;
    private EmojiCompat.GlyphChecker mGlyphChecker;
    private final MetadataRepo mMetadataRepo;
    private final EmojiCompat.SpanFactory mSpanFactory;
    private final boolean mUseEmojiAsDefaultStyle;

    private static boolean hasInvalidSelection(int i, int i2) {
        return i == -1 || i2 == -1 || i != i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, EmojiCompat.GlyphChecker glyphChecker, boolean z, int[] iArr) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = z;
        this.mEmojiAsDefaultStyleExceptions = iArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getEmojiMatch(CharSequence charSequence) {
        return getEmojiMatch(charSequence, this.mMetadataRepo.getMetadataVersion());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getEmojiMatch(CharSequence charSequence, int i) {
        ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        int length = charSequence.length();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < length) {
            int codePointAt = Character.codePointAt(charSequence, i2);
            int check = processorSm.check(codePointAt);
            EmojiMetadata currentMetadata = processorSm.getCurrentMetadata();
            if (check == 1) {
                i2 += Character.charCount(codePointAt);
                i4 = 0;
            } else if (check == 2) {
                i2 += Character.charCount(codePointAt);
            } else if (check == 3) {
                currentMetadata = processorSm.getFlushMetadata();
                if (currentMetadata.getCompatAdded() <= i) {
                    i3++;
                }
            }
            if (currentMetadata != null && currentMetadata.getCompatAdded() <= i) {
                i4++;
            }
        }
        if (i3 != 0) {
            return 2;
        }
        if (!processorSm.isInFlushableState() || processorSm.getCurrentMetadata().getCompatAdded() > i) {
            return i4 == 0 ? 0 : 2;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0126, code lost:
    
        ((androidx.emoji2.text.SpannableBuilder) r10).endBatchEdit();
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0048 A[Catch: all -> 0x012d, TryCatch #0 {all -> 0x012d, blocks: (B:98:0x000c, B:101:0x0011, B:103:0x0015, B:105:0x0024, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0064, B:28:0x0073, B:29:0x0081, B:33:0x009c, B:59:0x00ac, B:63:0x00b8, B:64:0x00c2, B:46:0x00cc, B:49:0x00d3, B:36:0x00d8, B:38:0x00e3, B:70:0x00ea, B:74:0x00f4, B:77:0x0100, B:78:0x0106, B:80:0x010f, B:5:0x002c), top: B:97:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0100 A[Catch: all -> 0x012d, TryCatch #0 {all -> 0x012d, blocks: (B:98:0x000c, B:101:0x0011, B:103:0x0015, B:105:0x0024, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0064, B:28:0x0073, B:29:0x0081, B:33:0x009c, B:59:0x00ac, B:63:0x00b8, B:64:0x00c2, B:46:0x00cc, B:49:0x00d3, B:36:0x00d8, B:38:0x00e3, B:70:0x00ea, B:74:0x00f4, B:77:0x0100, B:78:0x0106, B:80:0x010f, B:5:0x002c), top: B:97:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x010f A[Catch: all -> 0x012d, TRY_LEAVE, TryCatch #0 {all -> 0x012d, blocks: (B:98:0x000c, B:101:0x0011, B:103:0x0015, B:105:0x0024, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0064, B:28:0x0073, B:29:0x0081, B:33:0x009c, B:59:0x00ac, B:63:0x00b8, B:64:0x00c2, B:46:0x00cc, B:49:0x00d3, B:36:0x00d8, B:38:0x00e3, B:70:0x00ea, B:74:0x00f4, B:77:0x0100, B:78:0x0106, B:80:0x010f, B:5:0x002c), top: B:97:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.CharSequence process(java.lang.CharSequence r10, int r11, int r12, int r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiProcessor.process(java.lang.CharSequence, int, int, int, boolean):java.lang.CharSequence");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean handleOnKeyDown(Editable editable, int i, KeyEvent keyEvent) {
        boolean delete;
        if (i != 67) {
            if (i == 112) {
                delete = delete(editable, keyEvent, true);
            }
            return false;
        }
        delete = delete(editable, keyEvent, false);
        if (delete) {
            MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
            return true;
        }
        return false;
    }

    private static boolean delete(Editable editable, KeyEvent keyEvent, boolean z) {
        EmojiSpan[] emojiSpanArr;
        if (hasModifiers(keyEvent)) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (!hasInvalidSelection(selectionStart, selectionEnd) && (emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                    editable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int i, int i2, boolean z) {
        int max;
        int min;
        if (editable != null && inputConnection != null && i >= 0 && i2 >= 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (hasInvalidSelection(selectionStart, selectionEnd)) {
                return false;
            }
            if (z) {
                max = CodepointIndexFinder.findIndexBackward(editable, selectionStart, Math.max(i, 0));
                min = CodepointIndexFinder.findIndexForward(editable, selectionEnd, Math.max(i2, 0));
                if (max == -1 || min == -1) {
                    return false;
                }
            } else {
                max = Math.max(selectionStart - i, 0);
                min = Math.min(selectionEnd + i2, editable.length());
            }
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(max, min, EmojiSpan.class);
            if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                for (EmojiSpan emojiSpan : emojiSpanArr) {
                    int spanStart = editable.getSpanStart(emojiSpan);
                    int spanEnd = editable.getSpanEnd(emojiSpan);
                    max = Math.min(spanStart, max);
                    min = Math.max(spanEnd, min);
                }
                int max2 = Math.max(max, 0);
                int min2 = Math.min(min, editable.length());
                inputConnection.beginBatchEdit();
                editable.delete(max2, min2);
                inputConnection.endBatchEdit();
                return true;
            }
        }
        return false;
    }

    private static boolean hasModifiers(KeyEvent keyEvent) {
        return !KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState());
    }

    private void addEmoji(Spannable spannable, EmojiMetadata emojiMetadata, int i, int i2) {
        spannable.setSpan(this.mSpanFactory.createSpan(emojiMetadata), i, i2, 33);
    }

    private boolean hasGlyph(CharSequence charSequence, int i, int i2, EmojiMetadata emojiMetadata) {
        if (emojiMetadata.getHasGlyph() == 0) {
            emojiMetadata.setHasGlyph(this.mGlyphChecker.hasGlyph(charSequence, i, i2, emojiMetadata.getSdkAdded()));
        }
        return emojiMetadata.getHasGlyph() == 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class ProcessorSm {
        private static final int STATE_DEFAULT = 1;
        private static final int STATE_WALKING = 2;
        private int mCurrentDepth;
        private MetadataRepo.Node mCurrentNode;
        private final int[] mEmojiAsDefaultStyleExceptions;
        private MetadataRepo.Node mFlushNode;
        private int mLastCodepoint;
        private final MetadataRepo.Node mRootNode;
        private int mState = 1;
        private final boolean mUseEmojiAsDefaultStyle;

        private static boolean isEmojiStyle(int i) {
            return i == 65039;
        }

        private static boolean isTextStyle(int i) {
            return i == 65038;
        }

        private int reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
            return 1;
        }

        ProcessorSm(MetadataRepo.Node node, boolean z, int[] iArr) {
            this.mRootNode = node;
            this.mCurrentNode = node;
            this.mUseEmojiAsDefaultStyle = z;
            this.mEmojiAsDefaultStyleExceptions = iArr;
        }

        int check(int i) {
            MetadataRepo.Node node = this.mCurrentNode.get(i);
            int i2 = 2;
            if (this.mState != 2) {
                if (node == null) {
                    i2 = reset();
                } else {
                    this.mState = 2;
                    this.mCurrentNode = node;
                    this.mCurrentDepth = 1;
                }
            } else if (node != null) {
                this.mCurrentNode = node;
                this.mCurrentDepth++;
            } else if (isTextStyle(i)) {
                i2 = reset();
            } else if (!isEmojiStyle(i)) {
                if (this.mCurrentNode.getData() != null) {
                    i2 = 3;
                    if (this.mCurrentDepth == 1) {
                        if (shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                            this.mFlushNode = this.mCurrentNode;
                            reset();
                        } else {
                            i2 = reset();
                        }
                    } else {
                        this.mFlushNode = this.mCurrentNode;
                        reset();
                    }
                } else {
                    i2 = reset();
                }
            }
            this.mLastCodepoint = i;
            return i2;
        }

        EmojiMetadata getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        EmojiMetadata getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        boolean isInFlushableState() {
            return this.mState == 2 && this.mCurrentNode.getData() != null && (this.mCurrentDepth > 1 || shouldUseEmojiPresentationStyleForSingleCodepoint());
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            if (this.mCurrentNode.getData().isDefaultEmoji() || isEmojiStyle(this.mLastCodepoint)) {
                return true;
            }
            if (this.mUseEmojiAsDefaultStyle) {
                if (this.mEmojiAsDefaultStyleExceptions == null) {
                    return true;
                }
                if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, this.mCurrentNode.getData().getCodepointAt(0)) < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class CodepointIndexFinder {
        private static final int INVALID_INDEX = -1;

        private CodepointIndexFinder() {
        }

        static int findIndexBackward(CharSequence charSequence, int i, int i2) {
            int length = charSequence.length();
            if (i < 0 || length < i || i2 < 0) {
                return -1;
            }
            while (true) {
                boolean z = false;
                while (i2 != 0) {
                    i--;
                    if (i < 0) {
                        return z ? -1 : 0;
                    }
                    char charAt = charSequence.charAt(i);
                    if (z) {
                        if (!Character.isHighSurrogate(charAt)) {
                            return -1;
                        }
                        i2--;
                    } else if (!Character.isSurrogate(charAt)) {
                        i2--;
                    } else {
                        if (Character.isHighSurrogate(charAt)) {
                            return -1;
                        }
                        z = true;
                    }
                }
                return i;
            }
        }

        static int findIndexForward(CharSequence charSequence, int i, int i2) {
            int length = charSequence.length();
            if (i < 0 || length < i || i2 < 0) {
                return -1;
            }
            while (true) {
                boolean z = false;
                while (i2 != 0) {
                    if (i >= length) {
                        if (z) {
                            return -1;
                        }
                        return length;
                    }
                    char charAt = charSequence.charAt(i);
                    if (z) {
                        if (!Character.isLowSurrogate(charAt)) {
                            return -1;
                        }
                        i2--;
                        i++;
                    } else if (!Character.isSurrogate(charAt)) {
                        i2--;
                        i++;
                    } else {
                        if (Character.isLowSurrogate(charAt)) {
                            return -1;
                        }
                        i++;
                        z = true;
                    }
                }
                return i;
            }
        }
    }
}
