package mytest.jiang.wei.ipcremote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

import mytest.jiang.wei.ipc.bindpool.IBindPool;

/**
 * Created by wei.jiang on 2015/11/30.
 */
public class BinderPool {
    private final Context mContext;
    private static volatile BinderPool instantce;
    private static IBindPool mBinderPool;

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
        Intent service = new Intent("com.wei.jiang.binderpool");
        //5.0后的service必须以显式意图去调用，这里通过设置包名转为显式意图
        service.setPackage("mytest.jiang.wei.ipc");
        mContext.bindService(service, mBinderPoolConnction, Context.BIND_AUTO_CREATE);
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    public void unBind() {
        mContext.unbindService(mBinderPoolConnction);
    }
}
