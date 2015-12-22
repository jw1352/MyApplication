package com.example.jiangwei.transanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //分解
    public void explode(View v) {
        intent = new Intent(this, OtherActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //滑动
    public void slide(View v) {
        intent = new Intent(this, OtherActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //淡出
    public void fade(View v) {
        intent = new Intent(this, OtherActivity.class);
        intent.putExtra("flag", 2);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }


    //共享元素
    public void share(View v) {
        View fab = findViewById(R.id.fab);

        intent = new Intent(this, OtherActivity.class);
        intent.putExtra("flag", 3);
       // startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, v, "share").toBundle());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(v, "share"),
                Pair.create(fab, "fab")).toBundle());
    }
}
