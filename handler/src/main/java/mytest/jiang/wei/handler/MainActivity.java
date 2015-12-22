package mytest.jiang.wei.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mytest.jiang.wei.handler.entity.User;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            User user = (User) msg.obj;
            System.out.println(user);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        User user = new User(1, "zhangsan");
        Message msg = Message.obtain(handler, new Runnable() {
            @Override
            public void run() {
                System.out.println("11111111111");
            }
        });
        msg.obj = user;
        handler.sendMessage(msg);

       new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.d("xx", "xx11");
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d("msg", msg.obj.toString());
                    }
                };
                Looper.loop();
                Log.d("xx", "xx");
                Message message = Message.obtain();
                message.obj = "helloworld";
                handler.sendMessage(message);

            }
        }).start();
    }
}
