package mytest.jiang.wei.ipc.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import mytest.jiang.wei.ipc.R;

/**
 * Created by wei.jiang on 2015/11/27.
 */
public class MessengerActivity extends Activity{

    private Messenger replyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                System.out.println(msg.getData().getString("msg"));
            }
            super.handleMessage(msg);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Message message = Message.obtain();
            message.what = 1;
           // message.obj = "试试";
            //message.obj传递对象装失效，但可以能过setDate传递Bundle
            Bundle data = new Bundle();
            data.putString("msg", "helloworld");
            message.setData(data);

            //把messenger传递过去
            message.replyTo = replyMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);


    }

    public void bind(View v) {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
