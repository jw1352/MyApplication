package mytest.jiang.wei.ipc.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import java.util.List;

import mytest.jiang.wei.ipc.R;

/**
 * Created by wei.jiang on 2015/11/27.
 */
public class BooKManagerActivity extends Activity {

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = bookManager.getBookList();
                for (Book book : bookList) {
                    System.out.println(book.getBookName());
                }

                bookManager.registerListener(new IOnNewBookArrivedListener.Stub() {

                    @Override
                    public void onNewBookArrived(Book book) throws RemoteException {
                        System.out.println("监听到新书来了：" + book.getBookName());
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IBookManager bookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    public void bind(View v) {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void addBook(View v) {
        try {
            bookManager.addBook(new Book(12, "新书"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBook(View v) {
        try {
            List<Book> bookList = bookManager.getBookList();
            for (Book book : bookList) {
                System.out.println(book.getBookName());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
