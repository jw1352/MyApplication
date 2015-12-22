package mytest.jiang.wei.ipc.bindpool;

import android.os.RemoteException;

/**
 * Created by wei.jiang on 2015/11/30.
 */
public class ComputeImpl extends ICompute.Stub{
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
