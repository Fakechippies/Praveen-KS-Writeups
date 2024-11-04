package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: Add missing generic type declarations: [E] */
/* compiled from: Deprecated.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1", f = "Deprecated.kt", i = {0, 1, 1, 2}, l = {198, 199, 199}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0"})
/* loaded from: classes.dex */
public final class ChannelsKt__DeprecatedKt$filter$1<E> extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_filter;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ChannelsKt__DeprecatedKt$filter$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$filter$1> continuation) {
        super(2, continuation);
        this.$this_filter = receiveChannel;
        this.$predicate = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$filter$1 channelsKt__DeprecatedKt$filter$1 = new ChannelsKt__DeprecatedKt$filter$1(this.$this_filter, this.$predicate, continuation);
        channelsKt__DeprecatedKt$filter$1.L$0 = obj;
        return channelsKt__DeprecatedKt$filter$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$filter$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0058, code lost:
    
        r11 = r0;
        r0 = r1;
        r1 = r6;
        r6 = r8;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0069 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b0  */
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
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L49
            if (r1 == r5) goto L39
            if (r1 == r4) goto L26
            if (r1 != r3) goto L1e
            java.lang.Object r1 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r6 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            kotlin.ResultKt.throwOnFailure(r11)
            goto L57
        L1e:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L26:
            java.lang.Object r1 = r10.L$2
            java.lang.Object r6 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r7
            r7 = r1
            r1 = r0
            r0 = r10
            goto L91
        L39:
            java.lang.Object r1 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r6 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            kotlin.ResultKt.throwOnFailure(r11)
            r7 = r6
            r6 = r1
            r1 = r0
            r0 = r10
            goto L70
        L49:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r11 = (kotlinx.coroutines.channels.ProducerScope) r11
            kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r10.$this_filter
            kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
            r6 = r11
        L57:
            r11 = r10
        L58:
            r7 = r11
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r11.L$0 = r6
            r11.L$1 = r1
            r11.L$2 = r2
            r11.label = r5
            java.lang.Object r7 = r1.hasNext(r7)
            if (r7 != r0) goto L6a
            return r0
        L6a:
            r9 = r0
            r0 = r11
            r11 = r7
            r7 = r6
            r6 = r1
            r1 = r9
        L70:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto Lb0
            java.lang.Object r11 = r6.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r8 = r0.$predicate
            r0.L$0 = r7
            r0.L$1 = r6
            r0.L$2 = r11
            r0.label = r4
            java.lang.Object r8 = r8.invoke(r11, r0)
            if (r8 != r1) goto L8d
            return r1
        L8d:
            r9 = r7
            r7 = r11
            r11 = r8
            r8 = r9
        L91:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto Lab
            r11 = r0
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r0.L$0 = r8
            r0.L$1 = r6
            r0.L$2 = r2
            r0.label = r3
            java.lang.Object r11 = r8.send(r7, r11)
            if (r11 != r1) goto Lab
            return r1
        Lab:
            r11 = r0
            r0 = r1
            r1 = r6
            r6 = r8
            goto L58
        Lb0:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
