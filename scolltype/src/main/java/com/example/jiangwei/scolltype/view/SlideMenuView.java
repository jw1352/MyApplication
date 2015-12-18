package com.example.jiangwei.scolltype.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by jiangwei on 2015/11/17.
 */
public class SlideMenuView extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private int mScreenWidth;
    private ViewGroup leftMenu;
    private ViewGroup content;
    private ViewGroup rightMenu;
    private int mLeftMenuWidth;
    private int mRightMenuWidth;

    public SlideMenuView(Context context) {
        super(context);
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();

        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        leftMenu = (ViewGroup) getChildAt(0);
        content = (ViewGroup) getChildAt(2);
        rightMenu = (ViewGroup) getChildAt(1);

        mLeftMenuWidth = leftMenu.getMeasuredWidth();
        mRightMenuWidth = rightMenu.getMeasuredWidth();
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //让右侧菜单靠右显示
        rightMenu.layout(mScreenWidth-mRightMenuWidth, top, mScreenWidth, bottom);

    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //这里只用让中间主界面可滑动
            return content == child;
        }

        //处理横向的滚动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > mLeftMenuWidth) left = mLeftMenuWidth;
            if (-left > mRightMenuWidth) left = -mRightMenuWidth;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            super.onViewReleased(releasedChild, xvel, yvel);
            if (content.getLeft() > mLeftMenuWidth / 2) { // 向右滑动超过左侧菜单一半时
                mViewDragHelper.smoothSlideViewTo(content, mLeftMenuWidth, 0);
            } else if (-content.getLeft() > mRightMenuWidth / 2) { //向左滑动距离超过右侧菜单一半时，
                mViewDragHelper.smoothSlideViewTo(content, -mRightMenuWidth, 0);
            } else { //回到原点
                mViewDragHelper.smoothSlideViewTo(content, 0, 0);

            }
            ViewCompat.postInvalidateOnAnimation(SlideMenuView.this);
        }
    };
}
