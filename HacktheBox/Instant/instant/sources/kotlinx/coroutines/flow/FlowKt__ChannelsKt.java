package kotlinx.coroutines.flow;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Channels.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a/\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a9\u0010\u000b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0082@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a&\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/channels/BroadcastChannel;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "receiveAsFlow", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes.dex */
public final /* synthetic */ class FlowKt__ChannelsKt {
    public static final <T> Object emitAll(FlowCollector<? super T> flowCollector, ReceiveChannel<? extends T> receiveChannel, Continuation<? super Unit> continuation) {
        Object emitAllImpl$FlowKt__ChannelsKt = emitAllImpl$FlowKt__ChannelsKt(flowCollector, receiveChannel, true, continuation);
        return emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0076 A[Catch: all -> 0x0056, TRY_LEAVE, TryCatch #0 {all -> 0x0056, blocks: (B:12:0x0033, B:20:0x0070, B:22:0x0076, B:28:0x0085, B:30:0x0086, B:46:0x004c), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0086 A[Catch: all -> 0x0056, TRY_LEAVE, TryCatch #0 {all -> 0x0056, blocks: (B:12:0x0033, B:20:0x0070, B:22:0x0076, B:28:0x0085, B:30:0x0086, B:46:0x004c), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /* JADX WARN: Type inference failed for: r6v0, types: [kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v3, types: [kotlinx.coroutines.flow.FlowCollector, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0096 -> B:13:0x0036). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector<? super T> r6, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r7, boolean r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L58
            if (r2 == r4) goto L42
            if (r2 != r3) goto L3a
            boolean r6 = r0.Z$0
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L56
        L36:
            r5 = r8
            r8 = r6
            r6 = r5
            goto L5e
        L3a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L42:
            boolean r6 = r0.Z$0
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L56
            kotlinx.coroutines.channels.ChannelResult r9 = (kotlinx.coroutines.channels.ChannelResult) r9     // Catch: java.lang.Throwable -> L56
            java.lang.Object r9 = r9.getHolder()     // Catch: java.lang.Throwable -> L56
            goto L70
        L56:
            r8 = move-exception
            goto L9d
        L58:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.flow.FlowKt.ensureActive(r6)
        L5e:
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L99
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L99
            r0.Z$0 = r8     // Catch: java.lang.Throwable -> L99
            r0.label = r4     // Catch: java.lang.Throwable -> L99
            java.lang.Object r9 = r7.mo1653receiveCatchingJP2dKIU(r0)     // Catch: java.lang.Throwable -> L99
            if (r9 != r1) goto L6d
            return r1
        L6d:
            r5 = r8
            r8 = r6
            r6 = r5
        L70:
            boolean r2 = kotlinx.coroutines.channels.ChannelResult.m1668isClosedimpl(r9)     // Catch: java.lang.Throwable -> L56
            if (r2 == 0) goto L86
            java.lang.Throwable r8 = kotlinx.coroutines.channels.ChannelResult.m1664exceptionOrNullimpl(r9)     // Catch: java.lang.Throwable -> L56
            if (r8 != 0) goto L85
            if (r6 == 0) goto L82
            r6 = 0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
        L82:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L85:
            throw r8     // Catch: java.lang.Throwable -> L56
        L86:
            java.lang.Object r9 = kotlinx.coroutines.channels.ChannelResult.m1666getOrThrowimpl(r9)     // Catch: java.lang.Throwable -> L56
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L56
            r0.L$1 = r7     // Catch: java.lang.Throwable -> L56
            r0.Z$0 = r6     // Catch: java.lang.Throwable -> L56
            r0.label = r3     // Catch: java.lang.Throwable -> L56
            java.lang.Object r9 = r8.emit(r9, r0)     // Catch: java.lang.Throwable -> L56
            if (r9 != r1) goto L36
            return r1
        L99:
            r6 = move-exception
            r5 = r8
            r8 = r6
            r6 = r5
        L9d:
            throw r8     // Catch: java.lang.Throwable -> L9e
        L9e:
            r9 = move-exception
            if (r6 == 0) goto La4
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8)
        La4:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> receiveAsFlow(ReceiveChannel<? extends T> receiveChannel) {
        return new ChannelAsFlow(receiveChannel, false, null, 0, null, 28, null);
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> receiveChannel) {
        return new ChannelAsFlow(receiveChannel, true, null, 0, null, 28, null);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> flow, CoroutineScope coroutineScope) {
        return ChannelFlowKt.asChannelFlow(flow).produceImpl(coroutineScope);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "'BroadcastChannel' is obsolete and all corresponding operators are deprecated in the favour of StateFlow and SharedFlow")
    public static final <T> Flow<T> asFlow(final BroadcastChannel<T> broadcastChannel) {
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
                Object emitAll = FlowKt.emitAll(flowCollector, BroadcastChannel.this.openSubscription(), continuation);
                return emitAll == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAll : Unit.INSTANCE;
            }
        };
    }
}
