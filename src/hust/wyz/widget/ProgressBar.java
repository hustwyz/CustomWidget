
package hust.wyz.widget;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 
 * @author WangYunzhen
 *
 */
public class ProgressBar extends View {

    private static final int DEFAULT_HEIGHT = 25;

    private static final int DEFAULT_WIDTH = 100;

    private static final int DEAULT_DIFF = 10000;

    private ValueAnimator mAnimator;

    private Drawable mDrawableBackground;

    private Drawable mDrawableProgress;

    private int mWidth, mHeight;

    private int progress = 0;

    private int tempProgress = progress;

    private int max = 100;

    private Interpolator mInterpolator;

    private Paint mPaint;

    private float mTextHeight;

    private float mTextSize = 20;

    private int mTextColor;

    private int mTextShadowColor;

    private float mTextShadowX, mTextShadowY, mTextShadowRadius;

    private AnimatorUpdateListener mAnimatorUpdateListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            tempProgress = Integer.parseInt(animation.getAnimatedValue().toString());
            postInvalidate();
        }
    };

    public ProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mInterpolator = new AccelerateDecelerateInterpolator();
        mPaint = new Paint();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.progress_bar);
        if (a != null) {
            mTextSize = (int)a.getDimension(R.styleable.progress_bar_textSize, context
                    .getResources().getDimension(R.dimen.progress_text_size));
            Log.d("TextSize", "" + mTextSize);
            mTextColor = a.getColor(R.styleable.progress_bar_textColor, context.getResources()
                    .getColor(R.color.progress_text_color));
            mTextShadowColor = a.getColor(R.styleable.progress_bar_shadowColor, context
                    .getResources().getColor(R.color.progress_text_shadow_color));
            mTextShadowX = a.getDimension(R.styleable.progress_bar_shadowX, 1);
            mTextShadowY = a.getDimension(R.styleable.progress_bar_shadowY, 1);
            mTextShadowRadius = a.getDimension(R.styleable.progress_bar_shadowRadius, 3);
            mDrawableBackground = a.getDrawable(R.styleable.progress_bar_progressBackground);
            mDrawableProgress = a.getDrawable(R.styleable.progress_bar_progressDrawable);
            a.recycle();
        }
        if (mDrawableBackground == null)
            mDrawableBackground = context.getResources().getDrawable(
                    R.drawable.ic_xuebaprogressbar_background);
        if (mDrawableProgress == null)
            mDrawableProgress = context.getResources().getDrawable(
                    R.drawable.ic_xuebaprogressbar_progress);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setShadowLayer(mTextShadowRadius, mTextShadowX, mTextShadowY, mTextShadowColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mTextHeight = mPaint.ascent();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        tempProgress = progress;
        postInvalidate();
    }

    public void animatorProgress(int progress) {
        int valueStart = getProgress();
        this.progress = progress;
        tempProgress = valueStart;
        startAnimation(valueStart * DEAULT_DIFF, progress * DEAULT_DIFF);
        postInvalidate();
    }

    private void startAnimation(int valueStart, int valueEnd) {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        mAnimator = ValueAnimator.ofInt(valueStart, valueEnd);
        mAnimator.setDuration(1200);
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawableBackground.setBounds(0, 0, mWidth, mHeight);
        mDrawableBackground.draw(canvas);
        mDrawableProgress.setBounds(0, 0,
                mWidth * tempProgress / DEAULT_DIFF / (max > 0 ? max : 1), mHeight);
        mDrawableProgress.draw(canvas);
        canvas.drawText(progress + "/" + max, mWidth / 2, mHeight / 2 - mTextHeight / 2 - mTextSize
                / 8, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measureWidth(int measureSpec) {
        int result = DEFAULT_WIDTH;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = DEFAULT_HEIGHT;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
