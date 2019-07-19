package com.kinstalk.her.genie.view.widget.progress;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * @author Gc
 * @date 2019/7/16
 */
public class RoundView extends View {

    private Paint mArcPaint;
    private Path mArcPath;
    private RectF mArcRectF;

    private static final int DEFAULT_ANIM_DURATION = 400;
    private static final int DEFAULT_PAINT_COLOR = Color.parseColor("#A1FDFF");
    private static final int ROTATE_OFFSET = 270;
    private float mStartAngle, mSweepAngle;
    private int mStrokeColor;
    private int mAnimDuration;
    private float mBackgroundStrokeWidth;

    private float[] mCenterPos;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mRotationAnimator;
    private int size;

    public RoundView(Context context, int defaultPaintWidth) {
        super(context);
        mBackgroundStrokeWidth = defaultPaintWidth;
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // get attrs
        mStrokeColor = DEFAULT_PAINT_COLOR;
        mAnimDuration = DEFAULT_ANIM_DURATION;

        mArcPath = new Path();
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
        mArcPath.reset();
        mArcPath.addArc(mArcRectF, mStartAngle, mSweepAngle);
        canvas.drawPath(mArcPath, mArcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        size = (width < height) ? width : height;
        setMeasuredDimension(size, size);
        initBackgroundConfig(width, height);
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
        mArcRectF = new RectF(mBackgroundStrokeWidth, mBackgroundStrokeWidth, width - mBackgroundStrokeWidth, height - mBackgroundStrokeWidth);

        int[] colors = {0xFFA1FDFF, 0xFF0073FF, 0x000073FF};
        SweepGradient sweepGradient = new SweepGradient(size / 2f, size / 2f, colors, new float[]{0.2f, 0.5f, 0.9f});
        Matrix matrix = new Matrix();
        matrix.setRotate(ROTATE_OFFSET - 10, size / 2f, size / 2f);
        sweepGradient.setLocalMatrix(matrix);
        mArcPaint.setShader(sweepGradient);
        mArcPaint.setShadowLayer(3, 0, 0, Color.parseColor("#FFA1FDFF"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = (w < h) ? w : h;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.end();
        }
    }

    public float getRealBottom() {
        return size - mArcRectF.bottom + mBackgroundStrokeWidth;
    }

    /**
     * 开始循环背景动画
     */
    public void startCirculationAnimator() {
        startBackgroundAnimator();
    }

    /**
     * 背景转圈动画
     */
    private void startBackgroundAnimator() {
        mRotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360.0f);
        mRotationAnimator.setInterpolator(new LinearInterpolator());
        mRotationAnimator.setDuration(3000);
        mRotationAnimator.setStartDelay(200);
        mRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRotationAnimator.setRepeatMode(ValueAnimator.RESTART);

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
                invalidate();
            }
        });
        mRotationAnimator.start();
        mValueAnimator.start();
    }

    /**
     * restartBlinkAnimator anim
     */
    public void restart() {
        restartBackgroundAnimator();
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
