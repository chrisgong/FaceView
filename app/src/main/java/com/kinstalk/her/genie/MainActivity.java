package com.kinstalk.her.genie;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.kinstalk.her.genie.view.widget.progress.FaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.faceView)
    FaceView faceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> faceView.startLoadingAnimator(), 1000);
    }

    @OnClick(R.id.btn_play)
    void onPlay() {
        faceView.restart();
    }

    @OnClick(R.id.btn_nod)
    void onNod() {
        faceView.startNodAnimator();
    }

    @OnClick(R.id.btn_shake)
    void onShake() {
        faceView.startShakeAnimator();
    }
}
