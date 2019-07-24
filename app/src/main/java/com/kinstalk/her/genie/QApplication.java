package com.kinstalk.her.genie;

import android.app.Application;
import android.content.Intent;

import com.kinstalk.her.genie.service.GenieService;

/**
 * @author Gc
 * @date 2019/7/22
 */
public class QApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, GenieService.class));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
