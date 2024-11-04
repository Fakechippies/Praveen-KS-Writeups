package kotlinx.coroutines.flow;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: SafeCollector.common.kt */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1<T> implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_onEmpty$inlined;

    /* compiled from: SafeCollector.common.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1", f = "Emitters.kt", i = {0, 0, 0, 1}, l = {114, 122}, m = "collect", n = {"this", "$this$onEmpty_u24lambda_u2d3", "isEmpty", "collector"}, s = {"L$0", "L$1", "L$2", "L$0"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /* JADX WARN: Type inference failed for: r7v0, types: [kotlinx.coroutines.flow.FlowCollector, java.lang.Object, kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARN: Type inference failed for: r7v1, types: [kotlinx.coroutines.flow.internal.SafeCollector] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v7, types: [kotlinx.coroutines.flow.internal.SafeCollector] */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L4b
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r7 = (kotlinx.coroutines.flow.internal.SafeCollector) r7
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L31
            goto L9a
        L31:
            r8 = move-exception
            goto L9e
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            java.lang.Object r7 = r0.L$2
            kotlin.jvm.internal.Ref$BooleanRef r7 = (kotlin.jvm.internal.Ref.BooleanRef) r7
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 r4 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1) r4
            kotlin.ResultKt.throwOnFailure(r8)
            goto L73
        L4b:
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r0
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            kotlin.jvm.internal.Ref$BooleanRef r8 = new kotlin.jvm.internal.Ref$BooleanRef
            r8.<init>()
            r8.element = r4
            kotlinx.coroutines.flow.Flow r2 = r6.$this_onEmpty$inlined
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1 r5 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1
            r5.<init>(r8, r7)
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r8
            r0.label = r4
            java.lang.Object r2 = r2.collect(r5, r0)
            if (r2 != r1) goto L70
            return r1
        L70:
            r4 = r6
            r2 = r7
            r7 = r8
        L73:
            boolean r7 = r7.element
            if (r7 == 0) goto La2
            kotlinx.coroutines.flow.internal.SafeCollector r7 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlin.coroutines.CoroutineContext r8 = r0.getContext()
            r7.<init>(r2, r8)
            kotlin.jvm.functions.Function2 r8 = r4.$action$inlined     // Catch: java.lang.Throwable -> L31
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L31
            r2 = 0
            r0.L$1 = r2     // Catch: java.lang.Throwable -> L31
            r0.L$2 = r2     // Catch: java.lang.Throwable -> L31
            r0.label = r3     // Catch: java.lang.Throwable -> L31
            r2 = 6
            kotlin.jvm.internal.InlineMarker.mark(r2)     // Catch: java.lang.Throwable -> L31
            java.lang.Object r8 = r8.invoke(r7, r0)     // Catch: java.lang.Throwable -> L31
            r0 = 7
            kotlin.jvm.internal.InlineMarker.mark(r0)     // Catch: java.lang.Throwable -> L31
            if (r8 != r1) goto L9a
            return r1
        L9a:
            r7.releaseIntercepted()
            goto La2
        L9e:
            r7.releaseIntercepted()
            throw r8
        La2:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_onEmpty$inlined = flow;
        this.$action$inlined = function2;
    }
}
