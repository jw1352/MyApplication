package mytest.jiang.wei.ipc.bindpool;

import android.os.IBinder;

/**
 * Created by wei.jiang on 2015/11/30.
 */
interface IBindPool {
    IBinder queryBind(int binderCode);
}
