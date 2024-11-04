package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            i2 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
            i3 = 2;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            ChainHead chainHead = chainHeadArr[i4];
            chainHead.define();
            if (arrayList == null || (arrayList != null && arrayList.contains(chainHead.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
    
        if (r2.mHorizontalChainStyle == 2) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x004c, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x004a, code lost:
    
        if (r2.mVerticalChainStyle == 2) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0526  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x053c  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0545  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x03de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01b7  */
    /* JADX WARN: Type inference failed for: r2v63, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r8v39 */
    /* JADX WARN: Type inference failed for: r8v40 */
    /* JADX WARN: Type inference failed for: r8v46 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [androidx.constraintlayout.core.widgets.ConstraintWidget] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r38, androidx.constraintlayout.core.LinearSystem r39, int r40, int r41, androidx.constraintlayout.core.widgets.ChainHead r42) {
        /*
            Method dump skipped, instructions count: 1411
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }
}
