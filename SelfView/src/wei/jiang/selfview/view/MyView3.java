package wei.jiang.selfview.view;

import java.util.Currency;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class MyView3 extends View {

	private int mRectCount = 10;
	private int mWidth;
	private int mRectWidth;
	private int mRectHeight;
	private double offset = 5;
	private Paint mPaint;
	private LinearGradient mLinearGradient;

	public MyView3(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyView3(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}

	public MyView3(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = getWidth();
		mRectHeight = getHeight();
		mRectWidth = (int) (mWidth * 0.6 / mRectCount);

		mLinearGradient = new LinearGradient(0, 0, 0, mRectHeight,
				Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP);
		mPaint.setShader(mLinearGradient);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < mRectCount; i++) {
			double random = Math.random();
			float currentHeight = (float) (mRectHeight * random);
			;
			canvas.drawRect(
					(float) (mWidth * 0.4 / 2 + mRectWidth * i + offset),
					currentHeight, (float) (mWidth * 0.4 / 2 + mRectWidth
							* (i + 1)), mRectHeight, mPaint);
		}

		postInvalidateDelayed(300);

	}

}
