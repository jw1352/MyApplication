package mytest.jiang.wei.ipc.bindpool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import mytest.jiang.wei.ipc.R;

/**
 * Created by wei.jiang on 2015/11/30.
 */
public class BinderPoolActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);

        IBinder computeBinder = binderPool.queryBinder(BinderPool.BIND_COMPUTE);
        ICompute computeImpl = ComputeImpl.asInterface(computeBinder);

        String msg = "hello world";
        System.out.println(msg);

        try {
            String password= securityCenter.encrypt(msg);
            System.out.println(password);
            System.out.println(securityCenter.decrypt(password));

            System.out.println(computeImpl.add(1, 2));

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
