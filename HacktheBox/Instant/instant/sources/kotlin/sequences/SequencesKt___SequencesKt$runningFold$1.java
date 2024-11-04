package kotlin.sequences;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: Add missing generic type declarations: [R] */
/* compiled from: _Sequences.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlin.sequences.SequencesKt___SequencesKt$runningFold$1", f = "_Sequences.kt", i = {0, 1, 1}, l = {2290, 2294}, m = "invokeSuspend", n = {"$this$sequence", "$this$sequence", "accumulator"}, s = {"L$0", "L$0", "L$1"})
/* loaded from: classes.dex */
public final class SequencesKt___SequencesKt$runningFold$1<R> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ R $initial;
    final /* synthetic */ Function2<R, T, R> $operation;
    final /* synthetic */ Sequence<T> $this_runningFold;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$runningFold$1(R r, Sequence<? extends T> sequence, Function2<? super R, ? super T, ? extends R> function2, Continuation<? super SequencesKt___SequencesKt$runningFold$1> continuation) {
        super(2, continuation);
        this.$initial = r;
        this.$this_runningFold = sequence;
        this.$operation = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SequencesKt___SequencesKt$runningFold$1 sequencesKt___SequencesKt$runningFold$1 = new SequencesKt___SequencesKt$runningFold$1(this.$initial, this.$this_runningFold, this.$operation, continuation);
        sequencesKt___SequencesKt$runningFold$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningFold$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super R> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SequencesKt___SequencesKt$runningFold$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0056  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L2d
            if (r1 == r3) goto L25
            if (r1 != r2) goto L1d
            java.lang.Object r1 = r7.L$2
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r3 = r7.L$1
            java.lang.Object r4 = r7.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r3
            goto L4f
        L1d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L25:
            java.lang.Object r1 = r7.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L45
        L2d:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            R r8 = r7.$initial
            r4 = r7
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r7.L$0 = r1
            r7.label = r3
            java.lang.Object r8 = r1.yield(r8, r4)
            if (r8 != r0) goto L45
            return r0
        L45:
            R r8 = r7.$initial
            kotlin.sequences.Sequence<T> r3 = r7.$this_runningFold
            java.util.Iterator r3 = r3.iterator()
            r4 = r1
            r1 = r3
        L4f:
            r3 = r7
        L50:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L72
            java.lang.Object r5 = r1.next()
            kotlin.jvm.functions.Function2<R, T, R> r6 = r3.$operation
            java.lang.Object r8 = r6.invoke(r8, r5)
            r5 = r3
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r3.L$0 = r4
            r3.L$1 = r8
            r3.L$2 = r1
            r3.label = r2
            java.lang.Object r5 = r4.yield(r8, r5)
            if (r5 != r0) goto L50
            return r0
        L72:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningFold$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
