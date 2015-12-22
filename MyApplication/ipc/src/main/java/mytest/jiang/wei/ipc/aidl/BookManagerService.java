package mytest.jiang.wei.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wei.jiang on 2015/11/27.
 */
public class BookManagerService extends Service {


    //内部实现了读写同步
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<Book>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();
    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
            notifyListener(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public void notifyListener(Book book) throws RemoteException {
        System.out.println(book.getBookName());
        int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null)
                listener.onNewBookArrived(book);
        }
        mListenerList.finishBroadcast();
    }


    @Override
    public void onCreate() {
        bookList.add(new Book(1, "第一本书"));
        bookList.add(new Book(2, "第二本书"));
        super.onCreate();


        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
                Book newbook = new Book(bookList.size() + 1, "新书" + bookList.size() + 1);
                bookList.add(newbook);
                try {
                    notifyListener(newbook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
