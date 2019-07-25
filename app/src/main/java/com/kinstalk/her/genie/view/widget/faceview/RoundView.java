package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.TextureView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author Gc
 * @date 2019/7/16
 */
public class RoundView extends TextureView implements TextureView.SurfaceTextureListener, Handler.Callback {

    private static final String TAG = "RoundView";

    private static final int DEFAULT_ANIM_DURATION = 400;
    private static final int DEFAULT_PAINT_COLOR = Color.parseColor("#A1FDFF");
    private static final int ROTATE_OFFSET = 270;

    //loading
    public static final int MESSAGE_DRAW_LOADING = 0;

    private Paint mArcPaint;
    private RectF mArcRectF;
    private Rect mArcRect;

    private float mStartAngle, mSweepAngle;
    private int mStrokeColor;
    private int mAnimDuration;
    private float mBackgroundStrokeWidth;

    private float[] mCenterPos;
    private ValueAnimator mValueAnimator, mRotationAnimator;
    private float mHalfSize;

    //Surface Config
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    private boolean isQuitHandlerThreadWhenDestroy = true;

    public RoundView(Context context, int defaultPaintWidth) {
        super(context);
        mBackgroundStrokeWidth = defaultPaintWidth;
        mStrokeColor = DEFAULT_PAINT_COLOR;
        mAnimDuration = DEFAULT_ANIM_DURATION;
        mCenterPos = new float[2];

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setDither(true);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeJoin(Paint.Join.BEVEL);
        mArcPaint.setStrokeWidth(mBackgroundStrokeWidth);
        mArcPaint.setColor(mStrokeColor);
        mArcPaint.setMaskFilter(new BlurMaskFilter(4, BlurMaskFilter.Blur.SOLID));
        setLayerType(View.LAYER_TYPE_HARDWARE, mArcPaint);

        setAlpha(0.9f);
        setSurfaceTextureListener(this);
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
        mArcRect = new Rect((int) mArcRectF.left, (int) mArcRectF.top, (int) mArcRectF.left, (int) mArcRectF.right);

        int[] colors = {0xFFA1FDFF, 0xFF0073FF, 0x000073FF};
        SweepGradient sweepGradient = new SweepGradient(mHalfSize, mHalfSize, colors, new float[]{0.2f, 0.5f, 0.9f});
        Matrix matrix = new Matrix();
        matrix.setRotate(ROTATE_OFFSET - 10, mHalfSize, mHalfSize);
        sweepGradient.setLocalMatrix(matrix);
        mArcPaint.setShader(sweepGradient);
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

    /**
     * 背景转圈动画
     */
    private void startBackgroundAnimator() {
        if (mRotationAnimator == null) {
            mRotationAnimator = ValueAnimator.ofFloat(0, 360f);
            mRotationAnimator.setInterpolator(new QLinearInterpolator());
            mRotationAnimator.setDuration(2500);
            mRotationAnimator.setStartDelay(200);
            mRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mRotationAnimator.setRepeatMode(ValueAnimator.RESTART);
            mRotationAnimator.addUpdateListener(valueAnimator -> {
                if (!valueAnimator.isRunning()) {
                    return;
                }
                //旋转TextureView
                setRotation((float) valueAnimator.getAnimatedValue());
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
                    if (mHandler != null)
                        mHandler.sendEmptyMessage(MESSAGE_DRAW_LOADING);
                }
            });
        }
        mRotationAnimator.start();
        mValueAnimator.start();
    }

    private class QLinearInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float v) {
            return v;
        }
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
        setLayerType(LAYER_TYPE_NONE, null);
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
        setRotation(0);
        startCirculationAnimator();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread(TAG, 999);
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper(), this);
            isQuitHandlerThreadWhenDestroy = true;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (isQuitHandlerThreadWhenDestroy) {
            mHandlerThread.quit();
            mHandlerThread = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MESSAGE_DRAW_LOADING:
                drawLoading();
                return true;
        }
        return false;
    }

    /**
     * loading
     */
    private void drawLoading() {
        Canvas canvas = this.lockCanvas(mArcRect);
        canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);
        canvas.drawArc(mArcRectF, mStartAngle, mSweepAngle, false, mArcPaint);
        unlockCanvasAndPost(canvas);
    }
}
