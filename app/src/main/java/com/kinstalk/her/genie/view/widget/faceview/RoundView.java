package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * @author Gc
 * @date 2019/7/16
 */
public class RoundView extends View {

    private Paint mArcPaint;
    private RectF mArcRectF;

    private static final int DEFAULT_ANIM_DURATION = 400;
    private static final int DEFAULT_PAINT_COLOR = Color.parseColor("#A1FDFF");
    private static final int ROTATE_OFFSET = 270;
    private float mStartAngle, mSweepAngle, mRoteAngle;
    private int mStrokeColor;
    private int mAnimDuration;
    private float mBackgroundStrokeWidth;

    private float[] mCenterPos;
    private ValueAnimator mValueAnimator, mRotationAnimator;
    private float mHalfSize;

    public RoundView(Context context, int defaultPaintWidth) {
        super(context);
        mBackgroundStrokeWidth = defaultPaintWidth;
        init();
    }

    private void init() {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // get attrs
        mStrokeColor = DEFAULT_PAINT_COLOR;
        mAnimDuration = DEFAULT_ANIM_DURATION;

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeWidth(mBackgroundStrokeWidth);
        mArcPaint.setColor(mStrokeColor);

        mCenterPos = new float[2];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsRotate) {
            canvas.rotate(mRoteAngle, mHalfSize, mHalfSize);
        }

        canvas.drawArc(mArcRectF, mStartAngle, mSweepAngle, false, mArcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width < height) ? width : height;
        mHalfSize = size / 2f;
        setMeasuredDimension(size, size);
        initBackgroundConfig(size, size);
    }

    /**
     * 初始化背景绘制配置
     *
     * @param width
     * @param height
     */
    private void initBackgroundConfig(int width, int height) {
        mCenterPos[0] = (width) / 2f;
        mCenterPos[1] = (height) / 2f;
        mArcRectF = new RectF(mBackgroundStrokeWidth - 5f, mBackgroundStrokeWidth - 5f, (float) width - mBackgroundStrokeWidth + 5f, (float) height - mBackgroundStrokeWidth + 5f);

        int[] colors = {0xFFA1FDFF, 0xFF0073FF, 0x000073FF};
        SweepGradient sweepGradient = new SweepGradient(mHalfSize, mHalfSize, colors, new float[]{0.2f, 0.5f, 0.9f});
        Matrix matrix = new Matrix();
        matrix.setRotate(ROTATE_OFFSET - 10, mHalfSize, mHalfSize);
        sweepGradient.setLocalMatrix(matrix);
        mArcPaint.setShader(sweepGradient);
//        mArcPaint.setShadowLayer(10, 0, 0, Color.WHITE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.end();
        }
    }

    /**
     * 开始循环背景动画
     */
    public void startCirculationAnimator() {
        startBackgroundAnimator();
    }

    private boolean mIsRotate;

    /**
     * 背景转圈动画
     */
    private void startBackgroundAnimator() {
        this.post(() -> {
            if (mRotationAnimator == null) {
                mRotationAnimator = ValueAnimator.ofFloat(0, 360f);
                mRotationAnimator.setInterpolator(new LinearInterpolator());
                mRotationAnimator.setDuration(3000);
                mRotationAnimator.setStartDelay(200);
                mRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mRotationAnimator.setRepeatMode(ValueAnimator.RESTART);
                mRotationAnimator.addUpdateListener(valueAnimator -> {
                    mRoteAngle = (float) valueAnimator.getAnimatedValue();
                    postInvalidate();
                });
            }

            if (mValueAnimator == null) {
                mValueAnimator = ValueAnimator.ofFloat(ROTATE_OFFSET, 360.0f + ROTATE_OFFSET);
                mValueAnimator.setDuration(mAnimDuration);
                mValueAnimator.setInterpolator(new DecelerateInterpolator());
                mValueAnimator.addUpdateListener(animation -> {
                    if (!animation.isRunning()) {
                        return;
                    }

                    if (mSweepAngle >= -320) {
                        mStartAngle = (float) animation.getAnimatedValue();
                        mSweepAngle = ROTATE_OFFSET - mStartAngle;
                        mIsRotate = true;
                        postInvalidate();
                    }
                });
            }
            mRotationAnimator.start();
            mValueAnimator.start();
        });
    }

    /**
     * restartBlinkAnimator anim
     */
    public void restart() {
        restartBackgroundAnimator();
    }

    /**
     * 释放动画
     */
    public void release() {
//        setLayerType(LAYER_TYPE_NONE, null);

        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }

        if (mRotationAnimator != null && mRotationAnimator.isRunning()) {
            mRotationAnimator.cancel();
            setRotation(0);
        }
    }

    /**
     * 重新开始背景动画
     */
    private void restartBackgroundAnimator() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }

        if (mRotationAnimator != null && mRotationAnimator.isRunning()) {
            mRotationAnimator.cancel();
            setRotation(0);
        }
        mStartAngle = 0;
        mSweepAngle = ROTATE_OFFSET;
        startCirculationAnimator();
    }
}
