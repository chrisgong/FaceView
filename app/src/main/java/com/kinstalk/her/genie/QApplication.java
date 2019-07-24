package com.kinstalk.her.genie;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import com.kinstalk.her.genie.service.GenieService;

/**
 * @author Gc
 * @date 2019/7/22
 */
public class QApplication extends Application {

    private static final boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        setStricMode();
        startService(new Intent(this, GenieService.class));
    }

    private void setStricMode(){
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
