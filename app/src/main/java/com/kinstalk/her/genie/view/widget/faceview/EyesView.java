package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author Gc
 * @date 2019/7/18
 */
public class EyesView extends View {
    private Paint mEyePaint;
    private Path mEyePath;

    private RectF mRectLeftEys, mRectRightEys;

    private int mEyeStrokeWidth;

    private float[] mCenterPos, mLeftEyePos, mRightEyePos;
    private static final int DEFAULT_PAINT_COLOR = Color.parseColor("#A1FDFF");
    private ValueAnimator mOpenValueAnimator, mCloseValueAnimator;
    private static final int DEFAULT_ANIM_DURATION = 150;
    private float mEyesRadius;

    public EyesView(Context context, int defaultPaintWidth) {
        super(context);
        mEyeStrokeWidth = defaultPaintWidth * 2;
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mEyePath = new Path();
        mEyePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEyePaint.setStyle(Paint.Style.STROKE);
        mEyePaint.setStrokeCap(Paint.Cap.ROUND);
        mEyePaint.setStrokeJoin(Paint.Join.ROUND);
        mEyePaint.setStyle(Paint.Style.FILL);
        mEyePaint.setColor(DEFAULT_PAINT_COLOR);
        mEyePaint.setStrokeWidth(mEyeStrokeWidth);

        mCenterPos = new float[2];
        mLeftEyePos = new float[2];
        mRightEyePos = new float[2];
        setAlpha(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mRectLeftEys, mEyeStrokeWidth / 2, mEyeStrokeWidth / 2, mEyePaint);
        canvas.drawRoundRect(mRectRightEys, mEyeStrokeWidth / 2, mEyeStrokeWidth / 2, mEyePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
        mCenterPos[0] = width >> 1;
        mCenterPos[1] = height >> 1;

        float radiusX = (width - mEyeStrokeWidth) >> 1;
        float radiusY = (height - mEyeStrokeWidth) >> 1;
        float radius = Math.min(radiusX, radiusY);
        mEyesRadius = mEyeStrokeWidth / 2;

        mEyePath.addCircle(mCenterPos[0], mCenterPos[1], radius, Path.Direction.CW);
        PathMeasure circlePathMeasure = new PathMeasure(mEyePath, true);

        circlePathMeasure.getPosTan(circlePathMeasure.getLength() / 8 * 5, mLeftEyePos, null);
        circlePathMeasure.getPosTan(circlePathMeasure.getLength() / 8 * 7, mRightEyePos, null);
        mLeftEyePos[0] += mEyeStrokeWidth * 2;
        mLeftEyePos[1] += mEyeStrokeWidth << 1;
        mRightEyePos[0] -= mEyeStrokeWidth * 2;
        mRightEyePos[1] += mEyeStrokeWidth << 1;

        resetDefaultEyeCoordinate();
    }

    /**
     * 初始化动画
     */
    public void startInitializeAnimator() {
        if (mOpenValueAnimator == null) {
            mOpenValueAnimator = ValueAnimator.ofFloat(0f, 1f);
            mOpenValueAnimator.setDuration(DEFAULT_ANIM_DURATION);
            mOpenValueAnimator.setInterpolator(new DecelerateInterpolator());
            mOpenValueAnimator.addUpdateListener(animation -> {
                if (!animation.isRunning()) {
                    return;
                }

                float heightMultiple = (float) animation.getAnimatedValue();
                setAlpha(heightMultiple);
                float tempLeftTop = mRectLeftEys.top;
                float tempLeftBottom = mRectLeftEys.bottom;
                float tempRightTop = mRectRightEys.top;
                float tempRightBottom = mRectRightEys.bottom;

                mRectLeftEys.top = tempLeftTop - heightMultiple;
                mRectLeftEys.bottom = tempLeftBottom + heightMultiple;
                mRectRightEys.top = tempRightTop - heightMultiple;
                mRectRightEys.bottom = tempRightBottom + heightMultiple;
                invalidate();
            });
        }
        mOpenValueAnimator.start();
    }

    /**
     * 眨眼动画
     */
    public void startBlinkAnimator() {
        if (mCloseValueAnimator == null) {
            mCloseValueAnimator = ValueAnimator.ofFloat(1.5f, 0f);
            mCloseValueAnimator.setDuration(DEFAULT_ANIM_DURATION);
            mCloseValueAnimator.setInterpolator(new DecelerateInterpolator());
            mCloseValueAnimator.addUpdateListener(animation -> {
                if (!animation.isRunning()) {
                    return;
                }

                float heightMultiple = (float) animation.getAnimatedValue();
                setAlpha(heightMultiple);
                float tempLeftTop = mRectLeftEys.top;
                float tempLeftBottom = mRectLeftEys.bottom;
                float tempRightTop = mRectRightEys.top;
                float tempRightBottom = mRectRightEys.bottom;

                mRectLeftEys.top = tempLeftTop + heightMultiple;
                mRectLeftEys.bottom = tempLeftBottom - heightMultiple;
                mRectRightEys.top = tempRightTop + heightMultiple;
                mRectRightEys.bottom = tempRightBottom - heightMultiple;
                invalidate();
            });

            mCloseValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mOpenValueAnimator.start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
        mCloseValueAnimator.start();
    }

    private void resetDefaultEyeCoordinate() {
        mRectLeftEys = new RectF((int) mLeftEyePos[0] - mEyesRadius, (int) mLeftEyePos[1] - mEyesRadius, (int) (mLeftEyePos[0] + mEyesRadius), (int) (mLeftEyePos[1] + 4 * mEyesRadius));
        mRectRightEys = new RectF((int) mRightEyePos[0] - mEyesRadius, (int) mRightEyePos[1] - mEyesRadius, (int) (mRightEyePos[0] + mEyesRadius), (int) (mRightEyePos[1] + 4 * mEyesRadius));
    }

    /**
     * 重新眨眼
     */
    public void restartBlinkAnimator() {
        if (mOpenValueAnimator != null && mOpenValueAnimator.isRunning()) {
            mOpenValueAnimator.cancel();
        }
        if (mCloseValueAnimator != null && mCloseValueAnimator.isRunning()) {
            mCloseValueAnimator.cancel();
        }
        resetDefaultEyeCoordinate();
        invalidate();
        startInitializeAnimator();
        postDelayed(() -> startBlinkAnimator(), 1000);
    }
}
