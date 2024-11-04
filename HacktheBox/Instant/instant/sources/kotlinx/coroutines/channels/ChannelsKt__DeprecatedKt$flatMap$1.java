package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Add missing generic type declarations: [R] */
/* compiled from: Deprecated.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1", f = "Deprecated.kt", i = {0, 1, 2}, l = {321, 322, 322}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$0"})
/* loaded from: classes.dex */
final class ChannelsKt__DeprecatedKt$flatMap$1<R> extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_flatMap;
    final /* synthetic */ Function2<E, Continuation<? super ReceiveChannel<? extends R>>, Object> $transform;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ChannelsKt__DeprecatedKt$flatMap$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$flatMap$1> continuation) {
        super(2, continuation);
        this.$this_flatMap = receiveChannel;
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$flatMap$1 channelsKt__DeprecatedKt$flatMap$1 = new ChannelsKt__DeprecatedKt$flatMap$1(this.$this_flatMap, this.$transform, continuation);
        channelsKt__DeprecatedKt$flatMap$1.L$0 = obj;
        return channelsKt__DeprecatedKt$flatMap$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope<? super R> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$flatMap$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0099 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009f  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x009a -> B:8:0x0054). Please report as a decompilation issue!!! */
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
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L45
            if (r1 == r4) goto L35
            if (r1 == r3) goto L25
            if (r1 != r2) goto L1d
            java.lang.Object r1 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r5 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L53
        L1d:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L25:
            java.lang.Object r1 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r5 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r5
            r5 = r1
            r1 = r0
            r0 = r10
            goto L85
        L35:
            java.lang.Object r1 = r10.L$1
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r5 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r5
            r5 = r1
            r1 = r0
            r0 = r10
            goto L6a
        L45:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r11 = (kotlinx.coroutines.channels.ProducerScope) r11
            kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r10.$this_flatMap
            kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
            r5 = r11
        L53:
            r11 = r10
        L54:
            r6 = r11
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r11.L$0 = r5
            r11.L$1 = r1
            r11.label = r4
            java.lang.Object r6 = r1.hasNext(r6)
            if (r6 != r0) goto L64
            return r0
        L64:
            r9 = r0
            r0 = r11
            r11 = r6
            r6 = r5
            r5 = r1
            r1 = r9
        L6a:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L9f
            java.lang.Object r11 = r5.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ReceiveChannel<? extends R>>, java.lang.Object> r7 = r0.$transform
            r0.L$0 = r6
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r11 = r7.invoke(r11, r0)
            if (r11 != r1) goto L85
            return r1
        L85:
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            r7 = r6
            kotlinx.coroutines.channels.SendChannel r7 = (kotlinx.coroutines.channels.SendChannel) r7
            r8 = r0
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r0.L$0 = r6
            r0.L$1 = r5
            r0.label = r2
            java.lang.Object r11 = kotlinx.coroutines.channels.ChannelsKt.toChannel(r11, r7, r8)
            if (r11 != r1) goto L9a
            return r1
        L9a:
            r11 = r0
            r0 = r1
            r1 = r5
            r5 = r6
            goto L54
        L9f:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
