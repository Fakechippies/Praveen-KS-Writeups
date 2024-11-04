package android.support.v4.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IResultReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.support.v4.os.IResultReceiver";

    /* loaded from: classes.dex */
    public static class Default implements IResultReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.support.v4.os.IResultReceiver
        public void send(int i, Bundle bundle) throws RemoteException {
        }
    }

    void send(int i, Bundle bundle) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IResultReceiver {
        static final int TRANSACTION_send = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IResultReceiver.DESCRIPTOR);
        }

        public static IResultReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResultReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResultReceiver)) {
                return (IResultReceiver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResultReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResultReceiver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                send(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IResultReceiver {
            private IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResultReceiver.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.support.v4.os.IResultReceiver
            public void send(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResultReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
