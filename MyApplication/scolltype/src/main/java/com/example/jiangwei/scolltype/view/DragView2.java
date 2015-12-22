package com.example.jiangwei.scolltype.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by jiangwei on 2015/11/15.
 */
public class DragView2 extends FrameLayout {


    private ViewDragHelper mViewDragHelper;
    private View mMenuView;
    private View mContentView;

    public DragView2(Context context) {
        super(context);
    }

    public DragView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return mContentView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > 600) left = 600;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            super.onViewReleased(releasedChild, xvel, yvel);
            if (mContentView.getLeft() < 500) {
                mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragView2.this);
            } else {
                mViewDragHelper.smoothSlideViewTo(mContentView, 300, 0);
                ViewCompat.postInvalidateOnAnimation(DragView2.this);
            }

        }
    };

}
