package mytest.jiang.wei.ipc.aidl;

import mytest.jiang.wei.ipc.aidl.Book;

 /**
  * Created by wei.jiang on 2015/11/27.
  */
 interface IOnNewBookArrivedListener {

     void onNewBookArrived(in Book book);
 }