package com.kinstalk.her.genie;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.kinstalk.her.genie.view.widget.faceview.FaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.faceView)
    FaceView mFaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> mFaceView.startLoadingAnimator(), 1000);
    }

    @OnClick(R.id.btn_play)
    void onPlay() {
        mFaceView.restart();
    }

    @OnClick(R.id.btn_nod)
    void onNod() {
        mFaceView.startNodAnimator();
    }

    @OnClick(R.id.btn_shake)
    void onShake() {
        mFaceView.startShakeAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFaceView.release();
    }
}
