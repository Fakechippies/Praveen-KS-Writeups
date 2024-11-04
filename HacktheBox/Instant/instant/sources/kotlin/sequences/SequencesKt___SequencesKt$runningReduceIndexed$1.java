package kotlin.sequences;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: Add missing generic type declarations: [S] */
/* compiled from: _Sequences.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "S", "T", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1", f = "_Sequences.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {2377, 2381}, m = "invokeSuspend", n = {"$this$sequence", "iterator", "accumulator", "$this$sequence", "iterator", "accumulator", "index"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "I$0"})
/* loaded from: classes.dex */
final class SequencesKt___SequencesKt$runningReduceIndexed$1<S> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3<Integer, S, T, S> $operation;
    final /* synthetic */ Sequence<T> $this_runningReduceIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$runningReduceIndexed$1(Sequence<? extends T> sequence, Function3<? super Integer, ? super S, ? super T, ? extends S> function3, Continuation<? super SequencesKt___SequencesKt$runningReduceIndexed$1> continuation) {
        super(2, continuation);
        this.$this_runningReduceIndexed = sequence;
        this.$operation = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SequencesKt___SequencesKt$runningReduceIndexed$1 sequencesKt___SequencesKt$runningReduceIndexed$1 = new SequencesKt___SequencesKt$runningReduceIndexed$1(this.$this_runningReduceIndexed, this.$operation, continuation);
        sequencesKt___SequencesKt$runningReduceIndexed$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningReduceIndexed$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super S> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SequencesKt___SequencesKt$runningReduceIndexed$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0069  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L38
            if (r1 == r3) goto L2a
            if (r1 != r2) goto L22
            int r1 = r10.I$0
            java.lang.Object r3 = r10.L$2
            java.lang.Object r4 = r10.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r10.L$0
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r11 = r10
            r9 = r3
            r3 = r1
            r1 = r9
            goto L63
        L22:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L2a:
            java.lang.Object r1 = r10.L$2
            java.lang.Object r4 = r10.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r10.L$0
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L62
        L38:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            r5 = r11
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.sequences.Sequence<T> r11 = r10.$this_runningReduceIndexed
            java.util.Iterator r4 = r11.iterator()
            boolean r11 = r4.hasNext()
            if (r11 == 0) goto L95
            java.lang.Object r1 = r4.next()
            r11 = r10
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r10.L$0 = r5
            r10.L$1 = r4
            r10.L$2 = r1
            r10.label = r3
            java.lang.Object r11 = r5.yield(r1, r11)
            if (r11 != r0) goto L62
            return r0
        L62:
            r11 = r10
        L63:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L95
            kotlin.jvm.functions.Function3<java.lang.Integer, S, T, S> r6 = r11.$operation
            int r7 = r3 + 1
            if (r3 >= 0) goto L72
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L72:
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            java.lang.Object r8 = r4.next()
            java.lang.Object r3 = r6.invoke(r3, r1, r8)
            r1 = r11
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            r11.L$0 = r5
            r11.L$1 = r4
            r11.L$2 = r3
            r11.I$0 = r7
            r11.label = r2
            java.lang.Object r1 = r5.yield(r3, r1)
            if (r1 != r0) goto L92
            return r0
        L92:
            r1 = r3
            r3 = r7
            goto L63
        L95:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
