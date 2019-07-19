package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Random;


/**
 * @author Gc
 * @date 2019/7/18
 */
public class FaceView extends RelativeLayout {
    private static final int DEFAULT_PAINT_WIDTH = 15;
    private static final int ACTION_INIT_ANIMATOR = 0;
    private static final int ACTION_BLINK_ANIMATOR = 1;

    private RoundView mBackgroundView;
    private EyesView mEyesView;
    private int mBackgroundSize, mEyesSize;

    /**
     * 动画播放中
     */
    private boolean mAnimationPlaying;

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
        mHandler.sendEmptyMessageDelayed(ACTION_INIT_ANIMATOR, 200);
        mHandler.sendEmptyMessageDelayed(ACTION_BLINK_ANIMATOR, 1000);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTION_INIT_ANIMATOR:
                    mEyesView.startInitializeAnimator();
                    break;
                case ACTION_BLINK_ANIMATOR:
                    if (!mAnimationPlaying) {
                        mEyesView.startBlinkAnimator();
                    }
                    //5-20s随机眨眼
                    mHandler.sendEmptyMessageDelayed(ACTION_BLINK_ANIMATOR, (new Random().nextInt(10) + 5) * 1000);
                    break;
            }
        }
    };

    /**
     * 重启动画
     */
    public void restart() {
        mAnimationPlaying = false;
        mBackgroundView.restart();
        mEyesView.restartBlinkAnimator();
    }

    public void release(){
        mHandler.removeMessages(ACTION_BLINK_ANIMATOR);
        mBackgroundView.release();
        mEyesView.release();
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
        yieldOnlyEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 25));
        yieldOnlyEyeAnimator.start();

        //抬头+跳跃
        AnimatorSet riseAndJumpBgAnimator = new AnimatorSet();
        riseAndJumpBgAnimator.setDuration(150).setStartDelay(100);
        riseAndJumpBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", -75));
        riseAndJumpBgAnimator.start();

        AnimatorSet riseAndJumpEyeAnimator = new AnimatorSet();
        riseAndJumpEyeAnimator.setDuration(150).setStartDelay(100);
        riseAndJumpEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", -80)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 0.7f));
        riseAndJumpEyeAnimator.start();

        //低头+下落
        AnimatorSet yieldAndFallBgAnimator = new AnimatorSet();
        yieldAndFallBgAnimator.setDuration(100).setStartDelay(250);
        yieldAndFallBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", -25)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 25));
        yieldAndFallBgAnimator.start();

        AnimatorSet yieldAndFallEyeAnimator = new AnimatorSet();
        yieldAndFallEyeAnimator.setDuration(100).setStartDelay(250);
        yieldAndFallEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 25)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.0f));
        yieldAndFallEyeAnimator.start();
//
        //抬头+跳跃
        AnimatorSet riseAndJumpBgAnimator2 = new AnimatorSet();
        riseAndJumpBgAnimator2.setDuration(150).setStartDelay(350);
        riseAndJumpBgAnimator2.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", -75));
        riseAndJumpBgAnimator2.start();

        AnimatorSet riseAndJumpEyeAnimator2 = new AnimatorSet();
        riseAndJumpEyeAnimator2.setDuration(150).setStartDelay(350);
        riseAndJumpEyeAnimator2.play(ObjectAnimator.ofFloat(mEyesView, "translationY", -80)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 0.7f));
        riseAndJumpEyeAnimator2.start();

        //低头+下落
        AnimatorSet yieldAndFallBgAnimator2 = new AnimatorSet();
        yieldAndFallBgAnimator2.setDuration(100).setStartDelay(500);
        yieldAndFallBgAnimator2.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", -25)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 25));
        yieldAndFallBgAnimator2.start();

        AnimatorSet yieldAndFallEyeAnimator2 = new AnimatorSet();
        yieldAndFallEyeAnimator2.setDuration(100).setStartDelay(500);
        yieldAndFallEyeAnimator2.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 25)).with(ObjectAnimator.ofFloat(mEyesView, "scaleY", 1.0f));
        yieldAndFallEyeAnimator2.start();

        //抬头
        AnimatorSet riseOnlyBgAnimator = new AnimatorSet();
        riseOnlyBgAnimator.setDuration(100).setStartDelay(600);
        riseOnlyBgAnimator.play(ObjectAnimator.ofFloat(mBackgroundView, "rotationX", 0)).with(ObjectAnimator.ofFloat(mBackgroundView, "translationY", 0));
        riseOnlyBgAnimator.start();

        AnimatorSet riseOnlyEyeAnimator = new AnimatorSet();
        riseOnlyEyeAnimator.setDuration(100).setStartDelay(600);
        riseOnlyEyeAnimator.play(ObjectAnimator.ofFloat(mEyesView, "translationY", 0));
        riseOnlyEyeAnimator.start();

        mAnimationPlaying = true;
        riseOnlyEyeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAnimationPlaying = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * 开始摇头动画
     */
    public void startShakeAnimator() {
        //左摇
        ObjectAnimator leftShakeBgAnimator = ObjectAnimator.ofFloat(mBackgroundView, "translationX", -25);
        leftShakeBgAnimator.setDuration(100);
        leftShakeBgAnimator.start();

        ObjectAnimator leftShakeEyeAnimator = ObjectAnimator.ofFloat(mEyesView, "translationX", -70);
        leftShakeEyeAnimator.setDuration(100);
        leftShakeEyeAnimator.start();

        //右摇
        ObjectAnimator rightShakeBgAnimator = ObjectAnimator.ofFloat(mBackgroundView, "translationX", 20f);
        rightShakeBgAnimator.setDuration(150).setStartDelay(100);
        rightShakeBgAnimator.start();

        ObjectAnimator rightShakeEyeAnimator = ObjectAnimator.ofFloat(mEyesView, "translationX", 55);
        rightShakeEyeAnimator.setDuration(150).setStartDelay(100);
        rightShakeEyeAnimator.start();

        //左摇
        ObjectAnimator leftShakeBgAnimator2 = ObjectAnimator.ofFloat(mBackgroundView, "translationX", -15f);
        leftShakeBgAnimator2.setDuration(150).setStartDelay(250);
        leftShakeBgAnimator2.start();

        ObjectAnimator leftShakeEyeAnimator2 = ObjectAnimator.ofFloat(mEyesView, "translationX", -40);
        leftShakeEyeAnimator2.setDuration(150).setStartDelay(250);
        leftShakeEyeAnimator2.start();

        //右摇
        ObjectAnimator rightShakeBgAnimator2 = ObjectAnimator.ofFloat(mBackgroundView, "translationX", 15f);
        rightShakeBgAnimator2.setDuration(150).setStartDelay(400);
        rightShakeBgAnimator2.start();

        ObjectAnimator rightShakeEyeAnimator2 = ObjectAnimator.ofFloat(mEyesView, "translationX", 25f);
        rightShakeEyeAnimator2.setDuration(150).setStartDelay(400);
        rightShakeEyeAnimator2.start();

        //还原
        ObjectAnimator reverseShakeBgAnimator = ObjectAnimator.ofFloat(mBackgroundView, "translationX", 0);
        reverseShakeBgAnimator.setDuration(100).setStartDelay(550);
        reverseShakeBgAnimator.start();

        ObjectAnimator reverseShakeEyeAnimator = ObjectAnimator.ofFloat(mEyesView, "translationX", 0);
        reverseShakeEyeAnimator.setDuration(100).setStartDelay(550);
        reverseShakeEyeAnimator.start();

        mAnimationPlaying = true;
        reverseShakeBgAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAnimationPlaying = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
