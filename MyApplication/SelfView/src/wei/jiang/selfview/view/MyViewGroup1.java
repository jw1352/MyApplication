package wei.jiang.selfview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class MyViewGroup1 extends ViewGroup {

	private int mScreenHeight;
	private int count;
	private int mLastY;
	private Scroller mScroller;
	private int mStart;
	private int myEnd;
	private int mEnd;

	public MyViewGroup1(Context context) {
		super(context);
	}

	public MyViewGroup1(Context context, AttributeSet attrs) {
		super(context, attrs);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenHeight = dm.heightPixels;
		
		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 测量 子控制
		// measureChildren(widthMeasureSpec, heightMeasureSpec);

		count = getChildCount();
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, heightMeasureSpec);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 设置viewGroup的高度
		MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
		mlp.height = mScreenHeight * count;
		setLayoutParams(mlp);

		// 设置子控件的位置
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				childView.layout(l, i * mScreenHeight, r, (i + 1)
						* mScreenHeight);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			mStart = getScrollY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			
			int dy = mLastY - y;
			if (getScrollY() < 0) {
				dy = 0;
			}
			if (getScrollY() > (count-1)* mScreenHeight) {
				dy = 0;
			}
			
			scrollBy(0, dy);
			mLastY = y;
			break;
		 case MotionEvent.ACTION_UP:
			 mEnd = getScrollY();
			 int dScrollY = mEnd - mStart;
			 if (dScrollY > 0) {
                 if (dScrollY < mScreenHeight / 3) {
                     mScroller.startScroll(
                             0, getScrollY(),
                             0, -dScrollY);
                 } else {
                     mScroller.startScroll(
                             0, getScrollY(),
                             0, mScreenHeight - dScrollY);
                 }
             } else {
                 if (-dScrollY < mScreenHeight / 3) {
                     mScroller.startScroll(
                             0, getScrollY(),
                             0, -dScrollY);
                 } else {
                     mScroller.startScroll(
                             0, getScrollY(),
                             0, -mScreenHeight - dScrollY);
                 }
             }
			break;
		}
		postInvalidate();
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
	}
}
