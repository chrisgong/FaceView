package com.kinstalk.her.genie.view.widget.faceview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author Gc
 * @date 2019/7/24
 */
public class AnimatorHelper {

    private static AnimatorHelper animatorHelper = null;

    private static AnimatorSet yieldOnlyBgAnimator;

    private static AnimatorSet yieldOnlyEyeAnimator;

    private static AnimatorSet riseAndJumpBgAnimator;

    private static AnimatorSet riseAndJumpEyeAnimator;

    private static AnimatorSet yieldAndFallBgAnimator;

    private static AnimatorSet yieldAndFallEyeAnimator;

    private static AnimatorSet riseAndJumpBgAnimator2;

    private static AnimatorSet riseAndJumpEyeAnimator2;

    private static AnimatorSet yieldAndFallBgAnimator2;

    private static AnimatorSet yieldAndFallEyeAnimator2;

    private static AnimatorSet riseOnlyBgAnimator;

    private static AnimatorSet riseOnlyEyeAnimator;

    private static ObjectAnimator leftShakeBgAnimator;

    private static ObjectAnimator leftShakeEyeAnimator;

    private static ObjectAnimator rightShakeBgAnimator;

    private static ObjectAnimator rightShakeEyeAnimator;

    private static ObjectAnimator leftShakeBgAnimator2;

    private static ObjectAnimator leftShakeEyeAnimator2;

    private static ObjectAnimator rightShakeBgAnimator2;

    private static ObjectAnimator rightShakeEyeAnimator2;

    private static ObjectAnimator reverseShakeBgAnimator;

    private static ObjectAnimator reverseShakeEyeAnimator;

    public AnimatorHelper() {

    }

    public static AnimatorHelper getInstance() {
        if (animatorHelper == null) {
            synchronized (AnimatorHelper.class) {
                if (animatorHelper == null)
                    animatorHelper = new AnimatorHelper();
            }
        }
        return animatorHelper;
    }

    //低头
    public AnimatorSet getYieldOnlyBgAnimator(View view) {
        if(yieldOnlyBgAnimator == null) {
            yieldOnlyBgAnimator = new AnimatorSet();
            yieldOnlyBgAnimator.setDuration(100);
            yieldOnlyBgAnimator.play(ObjectAnimator.ofFloat(view, "translationY", 25)).with(ObjectAnimator.ofFloat(view, "rotationX", -25));
        }
        return yieldOnlyBgAnimator;
    }

    //低头
    public AnimatorSet getYieldOnlyEyeAnimator(View view) {
        if(yieldOnlyEyeAnimator == null) {
            yieldOnlyEyeAnimator = new AnimatorSet();
            yieldOnlyEyeAnimator.setDuration(100);
            yieldOnlyEyeAnimator.play(ObjectAnimator.ofFloat(view, "translationY", 25));
        }
        return yieldOnlyEyeAnimator;
    }

    //抬头+跳跃
    public AnimatorSet getRiseAndJumpBgAnimator(View view) {
        if(riseAndJumpBgAnimator == null){
            riseAndJumpBgAnimator = new AnimatorSet();
            riseAndJumpBgAnimator.setDuration(150).setStartDelay(100);
            riseAndJumpBgAnimator.play(ObjectAnimator.ofFloat(view, "rotationX", 0)).with(ObjectAnimator.ofFloat(view, "translationY", -75));
        }
        return riseAndJumpBgAnimator;
    }

    //抬头+跳跃
    public AnimatorSet getRiseAndJumpEyeAnimator(View view) {
        if(riseAndJumpEyeAnimator == null){
            riseAndJumpEyeAnimator = new AnimatorSet();
            riseAndJumpEyeAnimator.setDuration(150).setStartDelay(100);
            riseAndJumpEyeAnimator.play(ObjectAnimator.ofFloat(view, "translationY", -80)).with(ObjectAnimator.ofFloat(view, "scaleY", 0.7f));
        }
        return riseAndJumpEyeAnimator;
    }

    //低头+下落
    public AnimatorSet getYieldAndFallBgAnimator(View view) {
        if(yieldAndFallBgAnimator == null){
            yieldAndFallBgAnimator = new AnimatorSet();
            yieldAndFallBgAnimator.setDuration(100).setStartDelay(250);
            yieldAndFallBgAnimator.play(ObjectAnimator.ofFloat(view, "rotationX", -25)).with(ObjectAnimator.ofFloat(view, "translationY", 25));
        }
        return yieldAndFallBgAnimator;
    }

    //低头+下落
    public AnimatorSet getYieldAndFallEyeAnimator(View view) {
        if(yieldAndFallEyeAnimator == null){
            yieldAndFallEyeAnimator = new AnimatorSet();
            yieldAndFallEyeAnimator.setDuration(100).setStartDelay(250);
            yieldAndFallEyeAnimator.play(ObjectAnimator.ofFloat(view, "translationY", 25)).with(ObjectAnimator.ofFloat(view, "scaleY", 1.0f));
        }
        return yieldAndFallEyeAnimator;
    }

    //抬头+跳跃
    public AnimatorSet getRiseAndJumpBgAnimator2(View view) {
        if(riseAndJumpBgAnimator2 == null){
            riseAndJumpBgAnimator2 = new AnimatorSet();
            riseAndJumpBgAnimator2.setDuration(150).setStartDelay(350);
            riseAndJumpBgAnimator2.play(ObjectAnimator.ofFloat(view, "rotationX", 0)).with(ObjectAnimator.ofFloat(view, "translationY", -75));
        }
        return riseAndJumpBgAnimator2;
    }

    //抬头+跳跃
    public AnimatorSet getRiseAndJumpEyeAnimator2(View view) {
        if(riseAndJumpEyeAnimator2 == null){
            riseAndJumpEyeAnimator2 = new AnimatorSet();
            riseAndJumpEyeAnimator2.setDuration(150).setStartDelay(350);
            riseAndJumpEyeAnimator2.play(ObjectAnimator.ofFloat(view, "translationY", -80)).with(ObjectAnimator.ofFloat(view, "scaleY", 0.7f));
        }
        return riseAndJumpEyeAnimator2;
    }

    //低头+下落
    public AnimatorSet getYieldAndFallBgAnimator2(View view) {
        if(yieldAndFallBgAnimator2 == null){
            yieldAndFallBgAnimator2 = new AnimatorSet();
            yieldAndFallBgAnimator2.setDuration(100).setStartDelay(500);
            yieldAndFallBgAnimator2.play(ObjectAnimator.ofFloat(view, "rotationX", -25)).with(ObjectAnimator.ofFloat(view, "translationY", 25));
        }
        return yieldAndFallBgAnimator2;
    }

    //低头+下落
    public AnimatorSet getYieldAndFallEyeAnimator2(View view) {
        if(yieldAndFallEyeAnimator2 == null){
            yieldAndFallEyeAnimator2 = new AnimatorSet();
            yieldAndFallEyeAnimator2.setDuration(100).setStartDelay(500);
            yieldAndFallEyeAnimator2.play(ObjectAnimator.ofFloat(view, "translationY", 25)).with(ObjectAnimator.ofFloat(view, "scaleY", 1.0f));
        }
        return yieldAndFallEyeAnimator2;
    }

    //抬头
    public AnimatorSet getRiseOnlyBgAnimator(View view) {
        if(riseOnlyBgAnimator == null){
            riseOnlyBgAnimator = new AnimatorSet();
            riseOnlyBgAnimator.setDuration(100).setStartDelay(600);
            riseOnlyBgAnimator.play(ObjectAnimator.ofFloat(view, "rotationX", 0)).with(ObjectAnimator.ofFloat(view, "translationY", 0));
        }
        return riseOnlyBgAnimator;
    }

    //抬头
    public AnimatorSet getRiseOnlyEyeAnimator(View view) {
        if(riseOnlyEyeAnimator == null){
            riseOnlyEyeAnimator = new AnimatorSet();
            riseOnlyEyeAnimator.setDuration(100).setStartDelay(600);
            riseOnlyEyeAnimator.play(ObjectAnimator.ofFloat(view, "translationY", 0));
        }
        return riseOnlyEyeAnimator;
    }

    //左摇
    public ObjectAnimator getLeftShakeBgAnimator(View view) {
        if(leftShakeBgAnimator == null){
            leftShakeBgAnimator = ObjectAnimator.ofFloat(view, "translationX", -25);
            leftShakeBgAnimator.setDuration(100);
        }
        return leftShakeBgAnimator;
    }

    //左摇
    public ObjectAnimator getLeftShakeEyeAnimator(View view) {
        if(leftShakeEyeAnimator == null) {
            leftShakeEyeAnimator = ObjectAnimator.ofFloat(view, "translationX", -70);
            leftShakeEyeAnimator.setDuration(100);
        }
        return leftShakeEyeAnimator;
    }

    //右摇
    public ObjectAnimator getRightShakeBgAnimator(View view) {
        if(rightShakeBgAnimator == null){
            rightShakeBgAnimator = ObjectAnimator.ofFloat(view, "translationX", 20f);
            rightShakeBgAnimator.setDuration(150).setStartDelay(100);
        }
        return rightShakeBgAnimator;
    }

    //右摇
    public ObjectAnimator getRightShakeEyeAnimator(View view) {
        if(rightShakeEyeAnimator == null){
            rightShakeEyeAnimator = ObjectAnimator.ofFloat(view, "translationX", 55);
            rightShakeEyeAnimator.setDuration(150).setStartDelay(100);
        }
        return rightShakeEyeAnimator;
    }

    //左摇
    public ObjectAnimator getLeftShakeBgAnimator2(View view) {
        if(leftShakeBgAnimator2 == null){
            leftShakeBgAnimator2 = ObjectAnimator.ofFloat(view, "translationX", -15f);
            leftShakeBgAnimator2.setDuration(150).setStartDelay(250);
        }
        return leftShakeBgAnimator2;
    }

    //左摇
    public ObjectAnimator getLeftShakeEyeAnimator2(View view) {
        if(leftShakeEyeAnimator2 == null){
            leftShakeEyeAnimator2 = ObjectAnimator.ofFloat(view, "translationX", -40);
            leftShakeEyeAnimator2.setDuration(150).setStartDelay(250);
        }
        return leftShakeEyeAnimator2;
    }

    //右摇
    public ObjectAnimator getRightShakeBgAnimator2(View view) {
        if(rightShakeBgAnimator2 == null){
            rightShakeBgAnimator2 = ObjectAnimator.ofFloat(view, "translationX", 15f);
            rightShakeBgAnimator2.setDuration(150).setStartDelay(400);
        }
        return rightShakeBgAnimator2;
    }

    //右摇
    public ObjectAnimator getRightShakeEyeAnimator2(View view) {
        if(rightShakeEyeAnimator2 == null){
            rightShakeEyeAnimator2 = ObjectAnimator.ofFloat(view, "translationX", 25f);
            rightShakeEyeAnimator2.setDuration(150).setStartDelay(400);
        }
        return rightShakeEyeAnimator2;
    }

    //还原
    public ObjectAnimator getReverseShakeBgAnimator(View view) {
        if(reverseShakeBgAnimator == null){
            reverseShakeBgAnimator = ObjectAnimator.ofFloat(view, "translationX", 0);
            reverseShakeBgAnimator.setDuration(100).setStartDelay(550);
        }
        return reverseShakeBgAnimator;
    }

    //还原
    public ObjectAnimator getReverseShakeEyeAnimator(View view) {
        if(reverseShakeEyeAnimator == null){
            reverseShakeEyeAnimator = ObjectAnimator.ofFloat(view, "translationX", 0);
            reverseShakeEyeAnimator.setDuration(100).setStartDelay(550);
        }
        return reverseShakeEyeAnimator;
    }
}
