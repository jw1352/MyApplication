package mytest.jiang.wei.ipcremote;


import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import mytest.jiang.wei.ipc.bindpool.ICompute;
import mytest.jiang.wei.ipc.bindpool.ISecurityCenter;

public class MainActivity extends Activity{

    private BinderPool binderPool;

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
        binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(1);
        ISecurityCenter securityCenter = ISecurityCenter.Stub.asInterface(securityBinder);

        IBinder computeBinder = binderPool.queryBinder(2);
        ICompute computeImpl = ICompute.Stub.asInterface(computeBinder);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binderPool.unBind();
    }
}
