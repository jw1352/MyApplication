package com.example.jiangwei.transanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

/**
 * Created by jiangwei on 2015/11/22.
 */
public class OtherActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        int flag = getIntent().getIntExtra("flag", -1);
        //设置进入的动画
        if (flag == 0) {
            getWindow().setEnterTransition(new Explode());
        } else if (flag == 1) {
            getWindow().setEnterTransition(new Slide());
        } else if (flag == 2) {
            getWindow().setEnterTransition(new Fade());
        } else if (flag ==3) {
        }


        setContentView(R.layout.content_main);
    }
}
