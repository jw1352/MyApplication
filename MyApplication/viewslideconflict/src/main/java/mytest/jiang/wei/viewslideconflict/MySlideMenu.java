package mytest.jiang.wei.viewslideconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MySlideMenu extends HorizontalScrollView {
    private LinearLayout llContainer;
    private ViewGroup leftMenu;
    private ViewGroup rightMenu;
    private ViewGroup content;
    private int mLeftMenuWidth;
    private int mRightMenuWidth;
    private int mScreenWidth;
    private boolean isLeft = false;
    private boolean isRight = false;

    public MySlideMenu(Context context) {
        super(context);
    }

    public MySlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setHorizontalScrollBarEnabled(false); // 隐藏滚动条
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        llContainer = (LinearLayout) this.getChildAt(0);
        leftMenu = (ViewGroup) llContainer.getChildAt(0);
        content = (ViewGroup) llContainer.getChildAt(1);
        rightMenu = (ViewGroup) llContainer.getChildAt(2);
        // 测量子控件的宽搞
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mLeftMenuWidth = leftMenu.getWidth();
        mRightMenuWidth = rightMenu.getWidth();
        content.getLayoutParams().width = mScreenWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(leftMenu.getWidth(), 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mLeftMenuWidth + mRightMenuWidth / 2) {
                    this.smoothScrollTo(mLeftMenuWidth + mRightMenuWidth, 0);
                } else if (scrollX > mLeftMenuWidth / 2) {
                    this.smoothScrollTo(mLeftMenuWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void openLeft() {
        if (isLeft) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isLeft = true;
    }

    public void openRight() {
        if (isRight) {
            return;
        }
        this.smoothScrollTo(mLeftMenuWidth + mRightMenuWidth, 0);
        isRight = true;
    }

    public void resume() {
        this.smoothScrollTo(mLeftMenuWidth, 0);
        isLeft = false;
        isRight = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int scrollX = getScrollX();
        int rawX = (int) ev.getRawX();
        if (scrollX == 0 && rawX < mLeftMenuWidth) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
