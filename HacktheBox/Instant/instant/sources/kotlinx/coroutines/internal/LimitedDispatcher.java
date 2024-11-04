package kotlinx.coroutines.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: LimitedDispatcher.kt */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0002J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0097Aø\u0001\u0000¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0016J#\u0010\u001a\u001a\u00020\u00132\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u001bH\u0082\bJ\u001c\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0017J%\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00152\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u0096\u0001J\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0017J\b\u0010!\u001a\u00020\u0013H\u0016J\u001f\u0010\"\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00152\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00130$H\u0096\u0001J\b\u0010%\u001a\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\f\u0012\b\u0012\u00060\u0002j\u0002`\u00030\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rj\u0002`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, d2 = {"Lkotlinx/coroutines/internal/LimitedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "Lkotlinx/coroutines/Delay;", "dispatcher", "parallelism", "", "(Lkotlinx/coroutines/CoroutineDispatcher;I)V", "queue", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "runningWorkers", "workerAllocationLock", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "addAndTryDispatching", "", "block", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "dispatchInternal", "Lkotlin/Function0;", "dispatchYield", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "limitedParallelism", "run", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "tryAllocateWorker", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class LimitedDispatcher extends CoroutineDispatcher implements Runnable, Delay {
    private final /* synthetic */ Delay $$delegate_0;
    private final CoroutineDispatcher dispatcher;
    private final int parallelism;
    private final LockFreeTaskQueue<Runnable> queue;
    private volatile int runningWorkers;
    private final Object workerAllocationLock;

    @Override // kotlinx.coroutines.Delay
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long j, Continuation<? super Unit> continuation) {
        return this.$$delegate_0.delay(j, continuation);
    }

    @Override // kotlinx.coroutines.Delay
    public DisposableHandle invokeOnTimeout(long timeMillis, Runnable block, CoroutineContext context) {
        return this.$$delegate_0.invokeOnTimeout(timeMillis, block, context);
    }

    @Override // kotlinx.coroutines.Delay
    /* renamed from: scheduleResumeAfterDelay */
    public void mo1701scheduleResumeAfterDelay(long timeMillis, CancellableContinuation<? super Unit> continuation) {
        this.$$delegate_0.mo1701scheduleResumeAfterDelay(timeMillis, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LimitedDispatcher(CoroutineDispatcher coroutineDispatcher, int i) {
        this.dispatcher = coroutineDispatcher;
        this.parallelism = i;
        Delay delay = coroutineDispatcher instanceof Delay ? (Delay) coroutineDispatcher : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
        this.queue = new LockFreeTaskQueue<>(false);
        this.workerAllocationLock = new Object();
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public CoroutineDispatcher limitedParallelism(int parallelism) {
        LimitedDispatcherKt.checkParallelism(parallelism);
        if (parallelism >= this.parallelism) {
            return this;
        }
        return super.limitedParallelism(parallelism);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
    
        r1 = r4.workerAllocationLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0034, code lost:
    
        monitor-enter(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0035, code lost:
    
        r4.runningWorkers--;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0041, code lost:
    
        if (r4.queue.getSize() != 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0045, code lost:
    
        r4.runningWorkers++;
        r2 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0043, code lost:
    
        monitor-exit(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0044, code lost:
    
        return;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            r4 = this;
            r0 = 0
        L1:
            r1 = r0
        L2:
            kotlinx.coroutines.internal.LockFreeTaskQueue<java.lang.Runnable> r2 = r4.queue
            java.lang.Object r2 = r2.removeFirstOrNull()
            java.lang.Runnable r2 = (java.lang.Runnable) r2
            if (r2 == 0) goto L32
            r2.run()     // Catch: java.lang.Throwable -> L10
            goto L18
        L10:
            r2 = move-exception
            kotlin.coroutines.EmptyCoroutineContext r3 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
            kotlinx.coroutines.CoroutineExceptionHandlerKt.handleCoroutineException(r3, r2)
        L18:
            int r1 = r1 + 1
            r2 = 16
            if (r1 < r2) goto L2
            kotlinx.coroutines.CoroutineDispatcher r2 = r4.dispatcher
            r3 = r4
            kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
            boolean r2 = r2.isDispatchNeeded(r3)
            if (r2 == 0) goto L2
            kotlinx.coroutines.CoroutineDispatcher r0 = r4.dispatcher
            r1 = r4
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            r0.mo1700dispatch(r3, r1)
            return
        L32:
            java.lang.Object r1 = r4.workerAllocationLock
            monitor-enter(r1)
            int r2 = r4.runningWorkers     // Catch: java.lang.Throwable -> L4f
            int r2 = r2 + (-1)
            r4.runningWorkers = r2     // Catch: java.lang.Throwable -> L4f
            kotlinx.coroutines.internal.LockFreeTaskQueue<java.lang.Runnable> r2 = r4.queue     // Catch: java.lang.Throwable -> L4f
            int r2 = r2.getSize()     // Catch: java.lang.Throwable -> L4f
            if (r2 != 0) goto L45
            monitor-exit(r1)
            return
        L45:
            int r2 = r4.runningWorkers     // Catch: java.lang.Throwable -> L4f
            int r2 = r2 + 1
            r4.runningWorkers = r2     // Catch: java.lang.Throwable -> L4f
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L4f
            monitor-exit(r1)
            goto L1
        L4f:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LimitedDispatcher.run():void");
    }

    private final void dispatchInternal(Runnable block, Function0<Unit> dispatch) {
        if (!addAndTryDispatching(block) && tryAllocateWorker()) {
            dispatch.invoke();
        }
    }

    private final boolean addAndTryDispatching(Runnable block) {
        this.queue.addLast(block);
        return this.runningWorkers >= this.parallelism;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* renamed from: dispatch */
    public void mo1700dispatch(CoroutineContext context, Runnable block) {
        if (addAndTryDispatching(block) || !tryAllocateWorker()) {
            return;
        }
        this.dispatcher.mo1700dispatch(this, this);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatchYield(CoroutineContext context, Runnable block) {
        if (addAndTryDispatching(block) || !tryAllocateWorker()) {
            return;
        }
        this.dispatcher.dispatchYield(this, this);
    }

    private final boolean tryAllocateWorker() {
        synchronized (this.workerAllocationLock) {
            if (this.runningWorkers >= this.parallelism) {
                return false;
            }
            this.runningWorkers++;
            return true;
        }
    }
}
