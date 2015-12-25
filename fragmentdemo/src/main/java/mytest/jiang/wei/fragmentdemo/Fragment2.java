package mytest.jiang.wei.fragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wei.jiang on 2015/12/25.
 */
public class Fragment2 extends Fragment{

    private static final String TAG = "Fragment2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ondestory");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "ondestoryView");
        super.onDestroyView();
    }
}
