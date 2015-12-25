package com.example.jiangwei.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Spinner mSpinner;

    private List<String> mDate = new ArrayList<String>();
    private RecylerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else if (position == 1) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int i = 0; i < 10; i++) {
            mDate.add("item" + i);
        }
        ;

        mAdapter = new RecylerAdapter(mDate);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLisener(new RecylerAdapter.OnItemClickLisener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(final View v, int position) {
                //属性动画
                v.animate().translationZ(100f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                v.animate()
                                        .translationZ(1f)
                                        .setDuration(300)
                                        .start();
                            }
                        })
                        .start();
            }
        });
    }

    public void addRecycler(View v) {
        mDate.add("item" + mDate.size() + 1);
        mAdapter.notifyDataSetChanged();
    }

    public void delRecycler(View v) {
        if (mDate.size() > 0) {
            mDate.remove(mDate.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }
}

