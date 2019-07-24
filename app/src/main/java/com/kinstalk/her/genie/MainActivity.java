package com.kinstalk.her.genie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.kinstalk.her.genie.data.model.VoiceData;
import com.kinstalk.her.genie.view.widget.faceview.FaceView;
import com.kinstalk.her.genie.view.widget.textview.TyperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kinstalk.her.genie.support.constants.AIConstants.ACTION_TEXT_CHANGE;
import static com.kinstalk.her.genie.support.constants.AIConstants.FILED_VOICE_DATA;

public class MainActivity extends Activity {

    private static final int ACTION_INIT = 0;
    private static final int ACTION_FINISH = 1;

    @BindView(R.id.faceView)
    FaceView mFaceView;

    @BindView(R.id.tv_speak)
    TextView mTvSpeak;

    @BindView(R.id.tv_answer)
    TyperTextView mTvAnswer;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTION_INIT:
                    mFaceView.startLoadingAnimator();
                    break;
                case ACTION_FINISH:
                    MainActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerTextChangeReceiver();
        mHandler.sendEmptyMessageDelayed(ACTION_INIT, 1000);
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
        unregisterTextChangeReceiver();
        mFaceView.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvSpeak.setText("");
        mTvAnswer.setText("");
    }

    /**
     * 注册文字内容广播
     */
    private void registerTextChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TEXT_CHANGE);
        registerReceiver(mTextChangeReceiver, filter);
    }

    /**
     * 解除文字内容接受广播
     */
    private void unregisterTextChangeReceiver() {
        unregisterReceiver(mTextChangeReceiver);
    }

    /**
     * AiCore文本更新广播
     */
    BroadcastReceiver mTextChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_TEXT_CHANGE.equals(intent.getAction())) {
                VoiceData data = (VoiceData) intent.getSerializableExtra(FILED_VOICE_DATA);
                if (data != null) {
                    String speak = data.getSpeakText();
                    String answer = data.getAnswerText();
                    refreshText(speak, answer);
                }
            }
        }
    };

    private void refreshText(String speak, String answer) {
        runOnUiThread(() -> {
            if (!TextUtils.isEmpty(speak)) {
                mTvSpeak.setText(speak);
            }

            if (!TextUtils.isEmpty(answer)) {
                if (answer.contains("对不起") ||
                        answer.contains("别着急") ||
                        answer.contains("研究一下吧") ||
                        answer.contains("不会这个本领") ||
                        answer.contains("这个本领我还在学习中") ||
                        answer.contains("这个超出了我的知识范围")) {
                    mFaceView.startShakeAnimator();
                } else {
                    mFaceView.startNodAnimator();
                }

                mTvAnswer.animateText(answer, () -> {
                    // TODO: 2019/7/24 文字后处理逻辑
                });
            }
        });
    }
}
