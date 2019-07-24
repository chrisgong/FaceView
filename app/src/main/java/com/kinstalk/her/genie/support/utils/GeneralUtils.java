package com.kinstalk.her.genie.support.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.kinstalk.her.genie.MainActivity;

/**
 * @author Gc
 * @date 2019/2/11
 */
public class GeneralUtils {

    /**
     * 判断activity是否栈顶
     *
     * @param context
     * @return
     */
    public static boolean isGenieActivityTop(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(MainActivity.class.getName());
    }
}
