package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: Add missing generic type declarations: [E] */
/* compiled from: Deprecated.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 1, 2, 2}, l = {211, 212, 212}, m = "invokeSuspend", n = {"$this$produce", "index", "$this$produce", "e", "index", "$this$produce", "index"}, s = {"L$0", "I$0", "L$0", "L$2", "I$0", "L$0", "I$0"})
/* loaded from: classes.dex */
final class ChannelsKt__DeprecatedKt$filterIndexed$1<E> extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3<Integer, E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_filterIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ChannelsKt__DeprecatedKt$filterIndexed$1(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> function3, Continuation<? super ChannelsKt__DeprecatedKt$filterIndexed$1> continuation) {
        super(2, continuation);
        this.$this_filterIndexed = receiveChannel;
        this.$predicate = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$filterIndexed$1 channelsKt__DeprecatedKt$filterIndexed$1 = new ChannelsKt__DeprecatedKt$filterIndexed$1(this.$this_filterIndexed, this.$predicate, continuation);
        channelsKt__DeprecatedKt$filterIndexed$1.L$0 = obj;
        return channelsKt__DeprecatedKt$filterIndexed$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$filterIndexed$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0066, code lost:
    
        r13 = r0;
        r0 = r1;
        r7 = r8;
        r1 = r10;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00cb  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 206
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
