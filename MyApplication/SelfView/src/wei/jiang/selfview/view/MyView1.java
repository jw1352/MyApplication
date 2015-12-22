package wei.jiang.selfview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 百分比控件
 * @author jiangwei
 *
 */
public class MyView1 extends View{
	
	private int length;
	private int mCircleXy;
	private int mRadius;
	private RectF mArcRectF;
	private Paint mCirclePaint;
	private Paint mTextPaint;
	private Paint mArcRectPaint;
	private int mMeasureWidth;
	private int mMeasureHeight;

	private float mSweepAngle;
	private float mSweepValue = 66;
	private int mShowTextSize = 50;
	

	public MyView1(Context context) {
		super(context);
	}
	
	public MyView1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
		mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
	
		setMeasuredDimension(mMeasureWidth, mMeasureHeight);
		initView();
	}
	
	private void initView() {
		if (mMeasureWidth > mMeasureWidth) {
			length = mMeasureWidth;
		} else {
			length = mMeasureWidth;
		}
		
		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.GRAY);
		
		
		mArcRectPaint = new Paint();
		mArcRectPaint.setColor(Color.RED);
		mArcRectPaint.setStrokeWidth((float)(length * 0.1));
		mArcRectPaint.setStyle(Style.STROKE);
		
		
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setTextSize(mShowTextSize);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		
		mCircleXy = length / 2;
		mRadius = length / 4;
		mArcRectF  = new RectF((float)0.1 * length, (float)0.1 * length, (float)0.9 * length, (float)0.9 * length);
	
		mSweepAngle = mSweepValue / 100 * 360;
	}

	
	//重写onDraw方法绘制自己的view
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		//绘制圆
		canvas.drawCircle(mCircleXy, mCircleXy, mRadius, mCirclePaint);
		
		//绘制弧线
	     canvas.drawArc(mArcRectF, 0, mSweepAngle, false, mArcRectPaint);
		
		//绘制文字
		canvas.drawText("xxx", mCircleXy, mCircleXy + (mShowTextSize / 2), mTextPaint);
		
	}
	
	
}
