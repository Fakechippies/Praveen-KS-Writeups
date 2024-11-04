package kotlinx.coroutines.channels;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

/* compiled from: ConflatedChannel.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0018\u001a\u00020\r2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u001aH\u0014J\u0015\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u00002\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\rH\u0014J\n\u0010$\u001a\u0004\u0018\u00010\u0017H\u0014J\u0016\u0010%\u001a\u0004\u0018\u00010\u00172\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014J\u0014\u0010&\u001a\u0004\u0018\u00010'2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0017H\u0002R\u0014\u0010\b\u001a\u00020\t8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\r8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\r8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\r8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000eR\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lkotlinx/coroutines/channels/ConflatedChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isEmpty", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "value", "", "enqueueReceiveInternal", "receive", "Lkotlinx/coroutines/channels/Receive;", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotent", "wasClosed", "pollInternal", "pollSelectInternal", "updateValueLocked", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public class ConflatedChannel<E> extends AbstractChannel<E> {
    private final ReentrantLock lock;
    private Object value;

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected final boolean isBufferAlwaysEmpty() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected final boolean isBufferAlwaysFull() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
    }

    public ConflatedChannel(Function1<? super E, Unit> function1) {
        super(function1);
        this.lock = new ReentrantLock();
        this.value = AbstractChannelKt.EMPTY;
    }

    private final UndeliveredElementException updateValueLocked(Object element) {
        Function1<E, Unit> function1;
        Object obj = this.value;
        UndeliveredElementException undeliveredElementException = null;
        if (obj != AbstractChannelKt.EMPTY && (function1 = this.onUndeliveredElement) != null) {
            undeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, obj, null, 2, null);
        }
        this.value = element;
        return undeliveredElementException;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.value == AbstractChannelKt.EMPTY;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel, kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return isEmptyImpl();
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
    
        r1 = takeFirstReceiveOrPeekClosed();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
    
        if (r1 != null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0020, code lost:
    
        if ((r1 instanceof kotlinx.coroutines.channels.Closed) == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r1);
        r2 = r1.tryResumeReceive(r5, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
    
        if (r2 == null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        if (kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED() == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        if (r2 != kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0040, code lost:
    
        throw new java.lang.AssertionError();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0041, code lost:
    
        r2 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0043, code lost:
    
        r0.unlock();
        r1.completeResumeReceive(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
    
        return r1.getOfferResult();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0025, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x004e, code lost:
    
        r5 = updateValueLocked(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0052, code lost:
    
        if (r5 != null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0059, code lost:
    
        return kotlinx.coroutines.channels.AbstractChannelKt.OFFER_SUCCESS;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005a, code lost:
    
        throw r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0015, code lost:
    
        if (r4.value == kotlinx.coroutines.channels.AbstractChannelKt.EMPTY) goto L9;
     */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object offerInternal(E r5) {
        /*
            r4 = this;
            java.util.concurrent.locks.ReentrantLock r0 = r4.lock
            java.util.concurrent.locks.Lock r0 = (java.util.concurrent.locks.Lock) r0
            r0.lock()
            kotlinx.coroutines.channels.Closed r1 = r4.getClosedForSend()     // Catch: java.lang.Throwable -> L5b
            if (r1 == 0) goto L11
            r0.unlock()
            return r1
        L11:
            java.lang.Object r1 = r4.value     // Catch: java.lang.Throwable -> L5b
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.AbstractChannelKt.EMPTY     // Catch: java.lang.Throwable -> L5b
            if (r1 != r2) goto L4e
        L17:
            kotlinx.coroutines.channels.ReceiveOrClosed r1 = r4.takeFirstReceiveOrPeekClosed()     // Catch: java.lang.Throwable -> L5b
            if (r1 != 0) goto L1e
            goto L4e
        L1e:
            boolean r2 = r1 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L26
            r0.unlock()
            return r1
        L26:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch: java.lang.Throwable -> L5b
            r2 = 0
            kotlinx.coroutines.internal.Symbol r2 = r1.tryResumeReceive(r5, r2)     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L17
            boolean r3 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L41
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN     // Catch: java.lang.Throwable -> L5b
            if (r2 != r3) goto L3b
            goto L41
        L3b:
            java.lang.AssertionError r5 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> L5b
            r5.<init>()     // Catch: java.lang.Throwable -> L5b
            throw r5     // Catch: java.lang.Throwable -> L5b
        L41:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L5b
            r0.unlock()
            r1.completeResumeReceive(r5)
            java.lang.Object r5 = r1.getOfferResult()
            return r5
        L4e:
            kotlinx.coroutines.internal.UndeliveredElementException r5 = r4.updateValueLocked(r5)     // Catch: java.lang.Throwable -> L5b
            if (r5 != 0) goto L5a
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.channels.AbstractChannelKt.OFFER_SUCCESS     // Catch: java.lang.Throwable -> L5b
            r0.unlock()
            return r5
        L5a:
            throw r5     // Catch: java.lang.Throwable -> L5b
        L5b:
            r5 = move-exception
            r0.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ConflatedChannel.offerInternal(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
    
        r1 = describeTryOffer(r4);
        r2 = r5.performAtomicTrySelect(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
    
        if (r2 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003c, code lost:
    
        if (r2 == kotlinx.coroutines.channels.AbstractChannelKt.OFFER_FAILED) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
    
        if (r2 == kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0046, code lost:
    
        if (r2 == kotlinx.coroutines.selects.SelectKt.getALREADY_SELECTED()) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004a, code lost:
    
        if ((r2 instanceof kotlinx.coroutines.channels.Closed) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0069, code lost:
    
        throw new java.lang.IllegalStateException(("performAtomicTrySelect(describeTryOffer) returned " + r2).toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0024, code lost:
    
        r5 = r1.getResult();
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x002a, code lost:
    
        r0.unlock();
        kotlin.jvm.internal.Intrinsics.checkNotNull(r5);
        r5 = r5;
        r5.completeResumeReceive(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0039, code lost:
    
        return r5.getOfferResult();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0072, code lost:
    
        if (r5.trySelect() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
    
        return kotlinx.coroutines.selects.SelectKt.getALREADY_SELECTED();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007c, code lost:
    
        r4 = updateValueLocked(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0080, code lost:
    
        if (r4 != null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0087, code lost:
    
        return kotlinx.coroutines.channels.AbstractChannelKt.OFFER_SUCCESS;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0088, code lost:
    
        throw r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0015, code lost:
    
        if (r3.value == kotlinx.coroutines.channels.AbstractChannelKt.EMPTY) goto L9;
     */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object offerSelectInternal(E r4, kotlinx.coroutines.selects.SelectInstance<?> r5) {
        /*
            r3 = this;
            java.util.concurrent.locks.ReentrantLock r0 = r3.lock
            java.util.concurrent.locks.Lock r0 = (java.util.concurrent.locks.Lock) r0
            r0.lock()
            kotlinx.coroutines.channels.Closed r1 = r3.getClosedForSend()     // Catch: java.lang.Throwable -> L89
            if (r1 == 0) goto L11
            r0.unlock()
            return r1
        L11:
            java.lang.Object r1 = r3.value     // Catch: java.lang.Throwable -> L89
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.AbstractChannelKt.EMPTY     // Catch: java.lang.Throwable -> L89
            if (r1 != r2) goto L6e
        L17:
            kotlinx.coroutines.channels.AbstractSendChannel$TryOfferDesc r1 = r3.describeTryOffer(r4)     // Catch: java.lang.Throwable -> L89
            r2 = r1
            kotlinx.coroutines.internal.AtomicDesc r2 = (kotlinx.coroutines.internal.AtomicDesc) r2     // Catch: java.lang.Throwable -> L89
            java.lang.Object r2 = r5.performAtomicTrySelect(r2)     // Catch: java.lang.Throwable -> L89
            if (r2 != 0) goto L3a
            java.lang.Object r5 = r1.getResult()     // Catch: java.lang.Throwable -> L89
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L89
            r0.unlock()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            kotlinx.coroutines.channels.ReceiveOrClosed r5 = (kotlinx.coroutines.channels.ReceiveOrClosed) r5
            r5.completeResumeReceive(r4)
            java.lang.Object r4 = r5.getOfferResult()
            return r4
        L3a:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.channels.AbstractChannelKt.OFFER_FAILED     // Catch: java.lang.Throwable -> L89
            if (r2 == r1) goto L6e
            java.lang.Object r1 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC     // Catch: java.lang.Throwable -> L89
            if (r2 == r1) goto L17
            java.lang.Object r4 = kotlinx.coroutines.selects.SelectKt.getALREADY_SELECTED()     // Catch: java.lang.Throwable -> L89
            if (r2 == r4) goto L6a
            boolean r4 = r2 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L89
            if (r4 == 0) goto L4d
            goto L6a
        L4d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L89
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L89
            r5.<init>()     // Catch: java.lang.Throwable -> L89
            java.lang.String r1 = "performAtomicTrySelect(describeTryOffer) returned "
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch: java.lang.Throwable -> L89
            java.lang.StringBuilder r5 = r5.append(r2)     // Catch: java.lang.Throwable -> L89
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L89
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L89
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L89
            throw r4     // Catch: java.lang.Throwable -> L89
        L6a:
            r0.unlock()
            return r2
        L6e:
            boolean r5 = r5.trySelect()     // Catch: java.lang.Throwable -> L89
            if (r5 != 0) goto L7c
            java.lang.Object r4 = kotlinx.coroutines.selects.SelectKt.getALREADY_SELECTED()     // Catch: java.lang.Throwable -> L89
            r0.unlock()
            return r4
        L7c:
            kotlinx.coroutines.internal.UndeliveredElementException r4 = r3.updateValueLocked(r4)     // Catch: java.lang.Throwable -> L89
            if (r4 != 0) goto L88
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.channels.AbstractChannelKt.OFFER_SUCCESS     // Catch: java.lang.Throwable -> L89
            r0.unlock()
            return r4
        L88:
            throw r4     // Catch: java.lang.Throwable -> L89
        L89:
            r4 = move-exception
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ConflatedChannel.offerSelectInternal(java.lang.Object, kotlinx.coroutines.selects.SelectInstance):java.lang.Object");
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected Object pollInternal() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.value == AbstractChannelKt.EMPTY) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object obj = this.value;
            this.value = AbstractChannelKt.EMPTY;
            Unit unit = Unit.INSTANCE;
            return obj;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected Object pollSelectInternal(SelectInstance<?> select) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.value == AbstractChannelKt.EMPTY) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            if (!select.trySelect()) {
                return SelectKt.getALREADY_SELECTED();
            }
            Object obj = this.value;
            this.value = AbstractChannelKt.EMPTY;
            Unit unit = Unit.INSTANCE;
            return obj;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    public void onCancelIdempotent(boolean wasClosed) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            UndeliveredElementException updateValueLocked = updateValueLocked(AbstractChannelKt.EMPTY);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            super.onCancelIdempotent(wasClosed);
            if (updateValueLocked != null) {
                throw updateValueLocked;
            }
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    public boolean enqueueReceiveInternal(Receive<? super E> receive) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return super.enqueueReceiveInternal(receive);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected String getBufferDebugString() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return "(value=" + this.value + ')';
        } finally {
            reentrantLock.unlock();
        }
    }
}
