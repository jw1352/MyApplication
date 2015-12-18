package wei.jiang.selfview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyListViewActivity extends Activity {

    private ListView myListView;
    private int mTouchSlop;
    private Animator mAnimator;
    private Toolbar mToolbar;
    private String[] mStr = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylistview);

        initView();
    }

    private void initView() {
        myListView = (ListView) findViewById(R.id.myListView);
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material)));
        myListView.addHeaderView(header);
        myListView.setOnTouchListener(myTouchListener);
        for (int i = 0; i < mStr.length; i++) {
            mStr[i] = "Item " + i;
        }

        myListView.setAdapter(new ArrayAdapter<String>(
                MyListViewActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                mStr));

        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        private float mFirstY;
        private float mCurrentY;
        private int direction;
        private boolean isShow = true;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if (mCurrentY - mFirstY > mTouchSlop) { //向下
                        direction = 0;
                    } else if (mFirstY - mCurrentY > mTouchSlop) { //向上
                        direction = 1;
                    }

                    if (direction == 1) { //向上隐藏toolbar
                        if (!isShow) {
                            toolbarAnim(0);
                            isShow = false;
                        }
                    } else if (direction == 0) {
                        if (isShow) {
                            toolbarAnim(1);
                            isShow = true;
                        }
                    }


                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }

            return false;
        }
    };


    protected void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }

        if (flag == 0) {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), 0);
        } else {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), -mToolbar.getHeight());
        }

        mAnimator.start();
    }
}
