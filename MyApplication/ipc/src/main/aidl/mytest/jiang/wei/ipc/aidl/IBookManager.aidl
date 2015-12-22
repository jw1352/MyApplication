package mytest.jiang.wei.ipc.aidl;

import java.util.List;
import mytest.jiang.wei.ipc.aidl.Book;
import mytest.jiang.wei.ipc.aidl.IOnNewBookArrivedListener;

/**
 * Created by wei.jiang on 2015/11/24.
 */
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
