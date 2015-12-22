package mytest.jiang.wei.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by wei.jiang on 2015/11/27.
 */
public class MessengerService extends Service {

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
               // System.out.println(msg.obj.toString());
                Bundle data = msg.getData();
                System.out.println(data.getString("msg"));

                Messenger client = msg.replyTo;
                Message replayMessage = Message.obtain();
                replayMessage.what = 1;
                Bundle replyData = new Bundle();
                replyData.putString("msg", "哈哈，，我已收到");
                replayMessage.setData(replyData);
                try {
                    client.send(replayMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
