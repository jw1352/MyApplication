package mytest.jiang.wei.ipc.bindpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by wei.jiang on 2015/11/30.
 */
public class BinderPoolImpl extends IBindPool.Stub{

    @Override
    public IBinder queryBind(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case 1 :
                binder = new SecurityCenterImpl();
                break;
            case 2:
                binder = new ComputeImpl();
                break;
            default:
                break;
        }
        return binder;
    }
}
