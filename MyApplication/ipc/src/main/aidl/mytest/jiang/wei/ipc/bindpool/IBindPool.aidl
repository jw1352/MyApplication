package mytest.jiang.wei.ipc.bindpool;

import android.os.IBinder;

interface IBindPool {
    IBinder queryBind(int binderCode);
}
