package mytest.jiang.wei.ipc.bindpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wei.jiang on 2015/11/30.
 */
public class BinderPool {
    public static final int BINDER_NONE = -1;
    public static final int BIND_COMPUTE = 1;
    public static final int BINDER_SECURITY_CENTER = 2;
    private final Context mContext;
    private static volatile BinderPool instantce;
    private static IBindPool mBinderPool;
    private CountDownLatch countDownLatch;

    private BinderPool(Context context) {
        mContext = context;
        connectBinderPoolService();
    }

    public static  BinderPool getInstance(Context context) {
        if (instantce == null) {
            synchronized (BinderPool.class) {
                if (instantce == null) {
                    instantce = new BinderPool(context);
                }
            }
        }
        return instantce;
    }

    private void connectBinderPoolService() {
        countDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnction, Context.BIND_AUTO_CREATE);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;

        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBind(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder ;

    }


    private ServiceConnection mBinderPoolConnction = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBindPool.Stub.asInterface(service);


            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //监听死亡状态
    private IBinder.DeathRecipient mBinderPoolDeathRecipient  = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBindPool.Stub {

        @Override
        public IBinder queryBind(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER :
                    binder = new SecurityCenterImpl();
                    break;
                case BIND_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
