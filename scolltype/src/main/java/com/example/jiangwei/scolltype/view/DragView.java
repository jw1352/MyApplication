package com.example.jiangwei.scolltype.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by jiangwei on 2015/11/15.
 */
public class DragView extends View {
    private int lastX;
    private int lastY;
    private Scroller scroller;
    private int startScrollX;
    private int startScrollY;
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
           /*     lastX = rawX;
                lastY = rawY;*/
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
       /*         int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
*/


               // Log.d("test", "lastX : " + lastX);
                //Log.d("test", "left : " + getLeft());
               // Log.d("test", "offsetx : " + offsetX);
               /*layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);*/

                /*offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);*/

               /* ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = getLeft() + offsetX;
                params.topMargin = getTop() + offsetY;
                setLayoutParams(params);*/

                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View)getParent();
                scroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY());
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }


    }
}
