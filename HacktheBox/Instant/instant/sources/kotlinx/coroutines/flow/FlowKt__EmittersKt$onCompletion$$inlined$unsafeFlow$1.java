package kotlinx.coroutines.flow;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function3;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: SafeCollector.common.kt */
@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1<T> implements Flow<T> {
    final /* synthetic */ Function3 $action$inlined;
    final /* synthetic */ Flow $this_onCompletion$inlined;

    /* compiled from: SafeCollector.common.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1", f = "Emitters.kt", i = {0, 0, 1, 2}, l = {114, 121, 128}, m = "collect", n = {"this", "$this$onCompletion_u24lambda_u2d2", "e", "sc"}, s = {"L$0", "L$1", "L$0", "L$0"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            r0.<init>(r10)
        L19:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L56
            if (r2 == r5) goto L48
            if (r2 == r4) goto L3f
            if (r2 != r3) goto L37
            java.lang.Object r9 = r0.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r9 = (kotlinx.coroutines.flow.internal.SafeCollector) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L35
            goto L8d
        L35:
            r10 = move-exception
            goto L97
        L37:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3f:
            java.lang.Object r9 = r0.L$0
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lb3
        L48:
            java.lang.Object r9 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r2 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r2
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L54
            goto L6c
        L54:
            r9 = move-exception
            goto L9d
        L56:
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r0
            kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
            kotlinx.coroutines.flow.Flow r10 = r8.$this_onCompletion$inlined     // Catch: java.lang.Throwable -> L9b
            r0.L$0 = r8     // Catch: java.lang.Throwable -> L9b
            r0.L$1 = r9     // Catch: java.lang.Throwable -> L9b
            r0.label = r5     // Catch: java.lang.Throwable -> L9b
            java.lang.Object r10 = r10.collect(r9, r0)     // Catch: java.lang.Throwable -> L9b
            if (r10 != r1) goto L6b
            return r1
        L6b:
            r2 = r8
        L6c:
            kotlinx.coroutines.flow.internal.SafeCollector r10 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()
            r10.<init>(r9, r4)
            kotlin.jvm.functions.Function3 r9 = r2.$action$inlined     // Catch: java.lang.Throwable -> L93
            r0.L$0 = r10     // Catch: java.lang.Throwable -> L93
            r0.L$1 = r6     // Catch: java.lang.Throwable -> L93
            r0.label = r3     // Catch: java.lang.Throwable -> L93
            r2 = 6
            kotlin.jvm.internal.InlineMarker.mark(r2)     // Catch: java.lang.Throwable -> L93
            java.lang.Object r9 = r9.invoke(r10, r6, r0)     // Catch: java.lang.Throwable -> L93
            r0 = 7
            kotlin.jvm.internal.InlineMarker.mark(r0)     // Catch: java.lang.Throwable -> L93
            if (r9 != r1) goto L8c
            return r1
        L8c:
            r9 = r10
        L8d:
            r9.releaseIntercepted()
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L93:
            r9 = move-exception
            r7 = r10
            r10 = r9
            r9 = r7
        L97:
            r9.releaseIntercepted()
            throw r10
        L9b:
            r9 = move-exception
            r2 = r8
        L9d:
            kotlinx.coroutines.flow.ThrowingCollector r10 = new kotlinx.coroutines.flow.ThrowingCollector
            r10.<init>(r9)
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            kotlin.jvm.functions.Function3 r2 = r2.$action$inlined
            r0.L$0 = r9
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt__EmittersKt.access$invokeSafely$FlowKt__EmittersKt(r10, r2, r9, r0)
            if (r10 != r1) goto Lb3
            return r1
        Lb3:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(Flow flow, Function3 function3) {
        this.$this_onCompletion$inlined = flow;
        this.$action$inlined = function3;
    }
}
