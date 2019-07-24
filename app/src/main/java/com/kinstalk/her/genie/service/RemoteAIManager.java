package com.kinstalk.her.genie.service;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.kinstalk.her.genie.support.constants.AIConstants;
import com.tencent.xiaowei.info.QLoveResponseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import kinstalk.com.qloveaicore.IAICoreInterface;
import kinstalk.com.qloveaicore.ICmdCallback;

/**
 * @author Gc
 * @date 2019/7/22
 */
public class RemoteAIManager {

    private static final String TAG = "Gc";

    public static final String REMOTE_SERVICE_PACKAGE = "kinstalk.com.qloveaicore";
    public static final String REMOTE_SERVICE_CLASS = "kinstalk.com.qloveaicore.QAICoreService";

    public static final String LOCAL_SERVICE_PACKAGE = "com.kinstalk.her.genie";
    public static final String LOCAL_SERVICE_CLASS = "com.kinstalk.her.genie.service.GenieService";

    private static final int MSG_RECONNECT_REMOTE = 0x1;
    private static final int MSG_HANDLE_CMD = 0x2;

    private IAICoreInterface mAiService;

    final Context mContext;

    private static RemoteAIManager sInstance;

    private IRemoteServiceListener mAIResultListener;

    private RemoteAIManager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static synchronized RemoteAIManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemoteAIManager(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * AiService连接
     */
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (mAIResultListener != null) {
                mAIResultListener.onAIServiceConnected(mAiService);
            }
            mAiService = IAICoreInterface.Stub.asInterface(service);

            try {
                mAiService.registerService(getRemoteServiceParams(), new ICmdCallback.Stub() {

                    @Override
                    public String processCmd(String json) {
                        if (mAIResultListener != null) {
                            mAIResultListener.onJsonResult(json);
                        }
                        return "";
                    }

                    @Override
                    public void handleQLoveResponseInfo(String voiceId, QLoveResponseInfo qLoveResponseInfo, byte[] extendData) {
                        if (mAIResultListener != null) {
                            mAIResultListener.handleQLoveResponseInfo(voiceId, qLoveResponseInfo, extendData);
                        }
                    }

                    /**
                     * 唤醒后语音精灵动画的控制接口
                     * @param command 指令
                     * @param data 具体处理数据
                     * @return void
                     */
                    @Override
                    public void handleWakeupEvent(int command, String data) {
                        if (mAIResultListener != null) {
                            mAIResultListener.handleWakeupEvent(command, data);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mAIResultListener != null) {
                mAIResultListener.onAIServiceDisConnected();
            }
            mAiService = null;

            // TODO: 2019/7/22 强制页面退出

            mRemoteAiManagerHandler.removeMessages(MSG_RECONNECT_REMOTE);
            mRemoteAiManagerHandler.sendEmptyMessageDelayed(MSG_RECONNECT_REMOTE, 10000);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mRemoteAiManagerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_RECONNECT_REMOTE:
                    bindRemoteAIService();
                    break;
                case MSG_HANDLE_CMD:
                    break;
            }
        }
    };

    /**
     * 获取远程Service绑定解绑参数
     *
     * @return
     */
    private String getRemoteServiceParams() {
        JSONObject bindParams = new JSONObject();
        try {
            bindParams.put(AIConstants.AI_JSON_FIELD_TYPE, "waterAnim");
            bindParams.put(AIConstants.AI_JSON_FIELD_PACKAGE, LOCAL_SERVICE_PACKAGE);
            bindParams.put(AIConstants.AI_JSON_FIELD_SERVICECLASS, LOCAL_SERVICE_CLASS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bindParams.toString();
    }

    /**
     * 绑定AiService
     *
     * @return
     */
    public boolean bindRemoteAIService() {
        Intent intent = new Intent();
        intent.setClassName(REMOTE_SERVICE_PACKAGE, REMOTE_SERVICE_CLASS);
        return mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑Remote Service
     */
    public void unBindRemoteService() {
        try {
            if (mAiService != null) {
                mAiService.unRegisterService(getRemoteServiceParams());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mAiService = null;
        mContext.unbindService(mConn);
    }

    /**
     * 设置Ai结果接口
     *
     * @param listener
     */
    public void setAIResultListener(IRemoteServiceListener listener) {
        this.mAIResultListener = listener;
    }
}
