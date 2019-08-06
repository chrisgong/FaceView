package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.Animator;
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
    private static final int DEFAULT_PAINT_WIDTH = 20;
    private static final int ACTION_INIT_ANIMATOR = 0;
    private static final int ACTION_BLINK_ANIMATOR = 1;

    private RoundView mBackgroundView;
    private EyesView mEyesView;
    private int mBackgroundSize, mEyesSize;

    /**
     * 动画播放中
     */
    private boolean mAnimationPlaying;

    /**
     * 是否已经初始化过
     */
    private boolean mInitialized;

    public FaceView(Context context) {
        super(context);
    }

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mBackgroundSize = dp2px(160);
        mEyesSize = dp2px(160);
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
        if (!mInitialized) {
            mInitialized = true;
            mBackgroundView.startCirculationAnimator();
            mHandler.sendEmptyMessageDelayed(ACTION_INIT_ANIMATOR, 200);
            mHandler.sendEmptyMessageDelayed(ACTION_BLINK_ANIMATOR, 1000);
        }
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

    public void release() {
//        setLayerType(LAYER_TYPE_NONE, null);
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
        this.post(() -> {
            //低头
            AnimatorHelper.getInstance().getYieldOnlyBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getYieldOnlyEyeAnimator(mEyesView).start();

            //抬头+跳跃
            AnimatorHelper.getInstance().getRiseAndJumpBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getRiseAndJumpEyeAnimator(mEyesView).start();

            //低头+下落
            AnimatorHelper.getInstance().getYieldAndFallBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getYieldAndFallEyeAnimator(mEyesView).start();

            //抬头+跳跃
            AnimatorHelper.getInstance().getRiseAndJumpBgAnimator2(mBackgroundView).start();
            AnimatorHelper.getInstance().getRiseAndJumpEyeAnimator2(mEyesView).start();

            //低头+下落
            AnimatorHelper.getInstance().getYieldAndFallBgAnimator2(mBackgroundView).start();
            AnimatorHelper.getInstance().getYieldAndFallEyeAnimator2(mEyesView).start();

            //抬头
            AnimatorHelper.getInstance().getRiseOnlyBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getRiseOnlyEyeAnimator(mEyesView).start();

            mAnimationPlaying = true;
            AnimatorHelper.getInstance().getRiseOnlyEyeAnimator(mEyesView).addListener(new Animator.AnimatorListener() {
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
        });
    }

    /**
     * 开始摇头动画
     */
    public void startShakeAnimator() {
        this.post(() -> {
            //左摇
            AnimatorHelper.getInstance().getLeftShakeBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getLeftShakeEyeAnimator(mEyesView).start();

            //右摇
            AnimatorHelper.getInstance().getRightShakeBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getRightShakeEyeAnimator(mEyesView).start();

            //左摇
            AnimatorHelper.getInstance().getLeftShakeBgAnimator2(mBackgroundView).start();
            AnimatorHelper.getInstance().getLeftShakeEyeAnimator2(mEyesView).start();

            //右摇
            AnimatorHelper.getInstance().getRightShakeBgAnimator2(mBackgroundView).start();
            AnimatorHelper.getInstance().getRightShakeEyeAnimator2(mEyesView).start();

            //还原
            AnimatorHelper.getInstance().getReverseShakeBgAnimator(mBackgroundView).start();
            AnimatorHelper.getInstance().getReverseShakeEyeAnimator(mEyesView).start();

            mAnimationPlaying = true;
            AnimatorHelper.getInstance().getReverseShakeEyeAnimator(mEyesView).addListener(new Animator.AnimatorListener() {
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
        });
    }
}
