package com.kinstalk.her.genie.service;

import com.tencent.xiaowei.info.QLoveResponseInfo;

import kinstalk.com.qloveaicore.IAICoreInterface;

/**
 * @author Gc
 * @date 2019/7/22
 */
public interface IRemoteServiceListener {
    /**
     * AI识别结果回调
     *
     * @param jsonResult json结果
     */
    void onJsonResult(String jsonResult);

    void handleQLoveResponseInfo(String voiceId, QLoveResponseInfo rspData, byte[] extendData);

    /**
     * 唤醒后语音精灵动画的控制接口
     *
     * @param command 指令
     * @param data    具体处理数据
     * @return void
     */
    void handleWakeupEvent(int command, String data);

    void onAIServiceConnected(IAICoreInterface aiInterface);

    void onAIServiceDisConnected();

    void onTTSPlayBegin(String s);

    void onTTSPlayEnd(String s);

    void onTTSPlayError(String s, int i, String s1);
}
