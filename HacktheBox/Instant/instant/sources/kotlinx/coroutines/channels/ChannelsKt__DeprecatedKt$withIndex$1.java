package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Add missing generic type declarations: [E] */
/* compiled from: Deprecated.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/collections/IndexedValue;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {370, 371}, m = "invokeSuspend", n = {"$this$produce", "index", "$this$produce", "index"}, s = {"L$0", "I$0", "L$0", "I$0"})
/* loaded from: classes.dex */
final class ChannelsKt__DeprecatedKt$withIndex$1<E> extends SuspendLambda implements Function2<ProducerScope<? super IndexedValue<? extends E>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_withIndex;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ChannelsKt__DeprecatedKt$withIndex$1(ReceiveChannel<? extends E> receiveChannel, Continuation<? super ChannelsKt__DeprecatedKt$withIndex$1> continuation) {
        super(2, continuation);
        this.$this_withIndex = receiveChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$withIndex$1 channelsKt__DeprecatedKt$withIndex$1 = new ChannelsKt__DeprecatedKt$withIndex$1(this.$this_withIndex, continuation);
        channelsKt__DeprecatedKt$withIndex$1.L$0 = obj;
        return channelsKt__DeprecatedKt$withIndex$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope<? super IndexedValue<? extends E>> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$withIndex$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0085 -> B:6:0x0047). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L38
            if (r1 == r3) goto L29
            if (r1 != r2) goto L21
            int r1 = r11.I$0
            java.lang.Object r4 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r12)
            r12 = r5
            r5 = r11
            r9 = r4
            r4 = r1
            r1 = r9
            goto L47
        L21:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L29:
            int r1 = r11.I$0
            java.lang.Object r4 = r11.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r11.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r12)
            r6 = r11
            goto L60
        L38:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r11.$this_withIndex
            kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
            r4 = 0
            r5 = r11
        L47:
            r6 = r5
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r5.L$0 = r12
            r5.L$1 = r1
            r5.I$0 = r4
            r5.label = r3
            java.lang.Object r6 = r1.hasNext(r6)
            if (r6 != r0) goto L59
            return r0
        L59:
            r9 = r5
            r5 = r12
            r12 = r6
            r6 = r9
            r10 = r4
            r4 = r1
            r1 = r10
        L60:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L8a
            java.lang.Object r12 = r4.next()
            kotlin.collections.IndexedValue r7 = new kotlin.collections.IndexedValue
            int r8 = r1 + 1
            r7.<init>(r1, r12)
            r12 = r6
            kotlin.coroutines.Continuation r12 = (kotlin.coroutines.Continuation) r12
            r6.L$0 = r5
            r6.L$1 = r4
            r6.I$0 = r8
            r6.label = r2
            java.lang.Object r12 = r5.send(r7, r12)
            if (r12 != r0) goto L85
            return r0
        L85:
            r1 = r4
            r12 = r5
            r5 = r6
            r4 = r8
            goto L47
        L8a:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
