package com.kinstalk.her.genie.view.widget.progress;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author Gc
 * @date 2019/7/18
 */
public class FaceView extends RelativeLayout {
    private static final int DEFAULT_PAINT_WIDTH = 15;

    private RoundView mBackgroundView;
    private EyesView mEyesView;
    private int mBackgroundSize, mEyesSize;
    /**
     * 屏幕中心点坐标
     */
    private int mCenterX, mCenterY, mEyeCenterX;

    public FaceView(Context context) {
        super(context);
    }

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBackgroundSize = dp2px(250);
        mEyesSize = dp2px(250);
        RelativeLayout.LayoutParams paramsBackground = new RelativeLayout.LayoutParams(mBackgroundSize, mBackgroundSize);
        RelativeLayout.LayoutParams paramsEyes = new RelativeLayout.LayoutParams(mEyesSize, mEyesSize);
        paramsBackground.addRule(RelativeLayout.CENTER_IN_PARENT);
        paramsEyes.addRule(RelativeLayout.CENTER_IN_PARENT);
        mBackgroundView = new RoundView(context, DEFAULT_PAINT_WIDTH);
        mEyesView = new EyesView(context, DEFAULT_PAINT_WIDTH);
        addView(mBackgroundView, paramsBackground);
        addView(mEyesView, paramsEyes);
    }

    /**
     * 开始初始化动画
     */
    public void startLoadingAnimator() {
        mBackgroundView.startCirculationAnimator();
        mBottom = mBackgroundView.getRealBottom();

        postDelayed(() -> {
            mEyesView.startInitializeAnimator();
        }, 200);

        postDelayed(() -> {
            mEyesView.startBlinkAnimator();
        }, 1000);
    }

    /**
     * 重启动画
     */
    public void restart() {
        mBackgroundView.restart();
        mEyesView.restartBlinkAnimator();
    }

    /**
     * dp to px
     *
     * @param dpValue dp
     * @return px
     */
    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private float mBottom;

    /**
     * 开始点头动画
     */
    public void startNodAnimator() {

        //低头
        AnimatorSet yieldOnlyBgAnimator = new AnimatorSet();
        yieldOnlyBgAnimator.setDuration(100);
        yieldOnlyBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 25)).with(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", -25));
        yieldOnlyBgAnimator.start();

        AnimatorSet yieldOnlyEyeAnimator = new AnimatorSet();
        yieldOnlyEyeAnimator.setDuration(100);
        yieldOnlyEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "rotationX", -25)).with(ObjectAnimator.ofFloat(mEyesView, "translationY", 25)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.125f));
        yieldOnlyEyeAnimator.start();

        //抬头+跳跃
        AnimatorSet riseAndJumpBgAnimator = new AnimatorSet();
        riseAndJumpBgAnimator.setDuration(150).setStartDelay(100);
        riseAndJumpBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", -75));
        riseAndJumpBgAnimator.start();

        AnimatorSet riseAndJumpEyeAnimator = new AnimatorSet();
        riseAndJumpEyeAnimator.setDuration(150).setStartDelay(100);
        riseAndJumpEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mEyesView, "translationY", -80)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.0f));
        riseAndJumpEyeAnimator.start();

        //低头+下落
        AnimatorSet yieldAndFallBgAnimator = new AnimatorSet();
        yieldAndFallBgAnimator.setDuration(100).setStartDelay(250);
        yieldAndFallBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", -25)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 25));
        yieldAndFallBgAnimator.start();

        AnimatorSet yieldAndFallEyeAnimator = new AnimatorSet();
        yieldAndFallEyeAnimator.setDuration(100).setStartDelay(250);
        yieldAndFallEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 25)).with(ObjectAnimator.ofFloat(mEyesView, "rotationX", -30)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.125f));
        yieldAndFallEyeAnimator.start();
//
        //抬头+跳跃
        AnimatorSet riseAndJumpBgAnimator2 = new AnimatorSet();
        riseAndJumpBgAnimator2.setDuration(150).setStartDelay(350);
        riseAndJumpBgAnimator2.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", -75));
        riseAndJumpBgAnimator2.start();

        AnimatorSet riseAndJumpEyeAnimator2 = new AnimatorSet();
        riseAndJumpEyeAnimator2.setDuration(150).setStartDelay(350);
        riseAndJumpEyeAnimator2.play(ObjectAnimator.ofFloat(mEyesView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mEyesView, "translationY", -80)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.0f));
        riseAndJumpEyeAnimator2.start();

        //低头+下落
        AnimatorSet yieldAndFallBgAnimator2 = new AnimatorSet();
        yieldAndFallBgAnimator2.setDuration(100).setStartDelay(500);
        yieldAndFallBgAnimator2.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", -25)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 25));
        yieldAndFallBgAnimator2.start();

        AnimatorSet yieldAndFallEyeAnimator2 = new AnimatorSet();
        yieldAndFallEyeAnimator2.setDuration(100).setStartDelay(500);
        yieldAndFallEyeAnimator2.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 25)).with(ObjectAnimator.ofFloat(mEyesView, "rotationX", -30)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.125f));
        yieldAndFallEyeAnimator2.start();

        //抬头
        AnimatorSet riseOnlyBgAnimator = new AnimatorSet();
        riseOnlyBgAnimator.setDuration(100).setStartDelay(600);
        riseOnlyBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 0));
        riseOnlyBgAnimator.start();

        AnimatorSet riseOnlyEyeAnimator = new AnimatorSet();
        riseOnlyEyeAnimator.setDuration(100).setStartDelay(600);
        riseOnlyEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 0)).with(ObjectAnimator.ofFloat(mEyesView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.0f));
        riseOnlyEyeAnimator.start();
    }

    /**
     * 开始摇头动画
     */
    public void startShakeAnimator() {
        mBackgroundView.animate().x(mCenterX).rotationYBy(-25).setDuration(100);
        mEyesView.animate().x(mEyeCenterX).rotationYBy(-25).setDuration(100);
        postDelayed(() -> {
            mBackgroundView.animate().x(mCenterX).rotationYBy(50).setDuration(100);
            mEyesView.animate().x(mEyeCenterX).rotationYBy(50).setDuration(100);
        }, 100);
        postDelayed(() -> {
            mBackgroundView.animate().x(mCenterX).rotationYBy(-50).setDuration(100);
            mEyesView.animate().x(mEyeCenterX).rotationYBy(-50).setDuration(100);
        }, 200);
        postDelayed(() -> {
            mBackgroundView.animate().x(mCenterX).rotationYBy(50).setDuration(100);
            mEyesView.animate().x(mEyeCenterX).rotationYBy(50).setDuration(100);
        }, 300);
        postDelayed(() -> {
            mBackgroundView.animate().x(mCenterX).rotationYBy(-50).setDuration(100);
            mEyesView.animate().x(mEyeCenterX).rotationYBy(-50).setDuration(100);
        }, 400);
        postDelayed(() -> {
            mBackgroundView.animate().x(mCenterX).rotationY(0).setDuration(100);
            mEyesView.animate().x(mEyeCenterX).rotationY(0).setDuration(100);
        }, 500);
    }

    public void setDisplay(float centerX, float centerY) {
        this.mCenterX = (int) (centerX - mBackgroundSize / 2f) + DEFAULT_PAINT_WIDTH * 2; //加上默认笔刷的宽度
        this.mCenterY = (int) (centerY - mBackgroundSize / 2f) + DEFAULT_PAINT_WIDTH * 2;
        this.mEyeCenterX = (int) (centerX - mEyesSize / 2f) + DEFAULT_PAINT_WIDTH * 2;
    }
}
