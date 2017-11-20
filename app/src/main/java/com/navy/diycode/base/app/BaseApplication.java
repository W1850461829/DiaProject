
package com.navy.diycode.base.app;

import android.app.Application;


import com.gcssloop.diycode_sdk.api.Diycode;
import com.navy.diycode.utils.Config;
import com.navy.diycode.utils.CrashHandler;
import com.squareup.leakcanary.LeakCanary;


public class BaseApplication extends Application {

    public static final String client_id = "7024a413";
    public static final String client_secret = "8404fa33ae48d3014cfa89deaa674e4cbe6ec894a57dbef4e40d083dbbaa5cf4";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        CrashHandler.getInstance().init(this);

        Diycode.init(this, client_id, client_secret);

        Config.init(this);
    }
}
