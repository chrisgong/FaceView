package com.kinstalk.her.genie.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.kinstalk.her.genie.MainActivity;
import com.kinstalk.her.genie.data.model.VoiceData;
import com.kinstalk.her.genie.support.utils.GeneralUtils;
import com.tencent.xiaowei.info.QLoveResponseInfo;

import kinstalk.com.qloveaicore.AICoreDef;
import kinstalk.com.qloveaicore.IAICoreInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.kinstalk.her.genie.support.constants.AIConstants.ACTION_TEXT_CHANGE;
import static com.kinstalk.her.genie.support.constants.AIConstants.FILED_VOICE_DATA;

/**
 * @author Gc
 * @date 2019/7/22
 */
public class GenieService extends Service implements IRemoteServiceListener {

    private static final String TAG = "Gc";

    private RemoteAIManager mAIManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "GenieService started");
        mAIManager = RemoteAIManager.getInstance(getApplicationContext());
        mAIManager.setAIResultListener(this);
        mAIManager.bindRemoteAIService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onJsonResult(String jsonResult) {

    }

    @Override
    public void handleQLoveResponseInfo(String voiceId, QLoveResponseInfo rspData, byte[] extendData) {

    }

    @Override
    public void handleWakeupEvent(int command, String data) {
        switch (command) {
            case AICoreDef.WATER_ANIM_CMD_WAKEUP:
                startGenieActivity();
                break;
            case AICoreDef.WATER_ANIM_CMD_SHOW_TEXT:
                updateSpeakTextUI(new Gson().fromJson(data, VoiceData.class));
                break;
        }
    }

    /**
     * 刷新UI-用户说的内容
     *
     * @param data
     */
    private void updateSpeakTextUI(VoiceData data) {
        Intent intent = new Intent(ACTION_TEXT_CHANGE);
        intent.putExtra(FILED_VOICE_DATA, data);
        sendBroadcast(intent);
    }

    /**
     * 打开精灵页面
     */
    private void startGenieActivity() {
        if(!GeneralUtils.isGenieActivityTop(getApplicationContext())) {
            Intent intent = new Intent();
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAIServiceConnected(IAICoreInterface aiInterface) {
        Log.e(TAG, "onAIServiceConnected: ");
    }

    @Override
    public void onAIServiceDisConnected() {
        Log.e(TAG, "onAIServiceDisConnected: ");
    }

    @Override
    public void onTTSPlayBegin(String s) {

    }

    @Override
    public void onTTSPlayEnd(String s) {

    }

    @Override
    public void onTTSPlayError(String s, int i, String s1) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAIManager.unBindRemoteService();
    }

    private void process(int cmd) {

    }
}
