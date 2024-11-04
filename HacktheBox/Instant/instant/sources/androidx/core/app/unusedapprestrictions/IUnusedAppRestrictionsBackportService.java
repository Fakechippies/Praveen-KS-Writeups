package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;

/* loaded from: classes.dex */
public interface IUnusedAppRestrictionsBackportService extends IInterface {
    public static final String DESCRIPTOR = "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService";

    /* loaded from: classes.dex */
    public static class Default implements IUnusedAppRestrictionsBackportService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
        public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) throws RemoteException {
        }
    }

    void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IUnusedAppRestrictionsBackportService {
        static final int TRANSACTION_isPermissionRevocationEnabledForApp = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IUnusedAppRestrictionsBackportService.DESCRIPTOR);
        }

        public static IUnusedAppRestrictionsBackportService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUnusedAppRestrictionsBackportService)) {
                return (IUnusedAppRestrictionsBackportService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IUnusedAppRestrictionsBackportService {
            private IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnusedAppRestrictionsBackportService.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
            public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
                    obtain.writeStrongInterface(iUnusedAppRestrictionsBackportCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
