package com.kinstalk.her.genie.data.model;

import java.io.Serializable;

/**
 * @author Gc
 * @date 2019/7/22
 */
public class VoiceData implements Serializable {

    public int cmd;

    public String text;

    public String textAnswer;

    public String appName;

    public String voiceid;

    public VoiceData(int cmd, String text, String textAnswer, String appName, String voiceid) {
        this.cmd = cmd;
        this.text = text;
        this.textAnswer = textAnswer;
        this.appName = appName;
        this.voiceid = voiceid;
    }

    public int getCmd() {
        return cmd;
    }

    public String getSpeakText() {
        return text;
    }

    public String getAnswerText() {
        return textAnswer;
    }

    public String getAppName() {
        return appName;
    }

    public String getVoiceid() {
        return voiceid;
    }

    @Override
    public String toString() {
        return "VoiceData{" +
                "cmd=" + cmd +
                ", text='" + text + '\'' +
                ", textAnswer='" + textAnswer + '\'' +
                ", appName='" + appName + '\'' +
                ", voiceid='" + voiceid + '\'' +
                '}';
    }
}
