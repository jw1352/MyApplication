package wei.jiang.selfview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 闪动的文字
 * 
 * @author jiangwei
 * 
 */
public class MyView2 extends TextView {

	private int mViewWidth;
	private TextPaint mPaint;
	private Matrix mGradientMetrix;
	private int mTranslate;
	private Shader shader;

	public MyView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView2(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mViewWidth = getMeasuredWidth();
		if (mViewWidth > 0) {
			// 获取默认的画笔
			mPaint = getPaint();
			shader = new LinearGradient(0, 0, mViewWidth, 0, new int[] {
					Color.BLUE, 0xffffffff, Color.BLUE }, null,
					Shader.TileMode.CLAMP);
			mPaint.setShader(shader);
			mGradientMetrix = new Matrix();
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (mGradientMetrix != null) {
			mTranslate += mViewWidth/5;
			if (mTranslate > 2* mViewWidth) {
				mTranslate = -mViewWidth;
			}
		}
		
		mGradientMetrix.setTranslate(mTranslate, 0);
		shader.setLocalMatrix(mGradientMetrix);
		postInvalidateDelayed(100);
	}

}
