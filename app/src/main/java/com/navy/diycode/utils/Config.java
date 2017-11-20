package com.navy.diycode.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

import com.gcssloop.diycode_sdk.utils.ACache;

import java.io.Serializable;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/11/20.
 */

public class Config {
    private volatile static Config mConfig;

    private Config(Context context) {
        mDiskCache = ACache.get(context, "config");
    }

    public static Config init(Context context) {
        if (null == mConfig) {
            synchronized (Config.class) {
                if (null == mConfig) {
                    mConfig = new Config(context);
                }
            }
        }
        return mConfig;
    }

    public static Config getSingleInstance() {
        return mConfig;
    }
    //--- 首页状态 -------------------------------------------------------------------------------

    private String Key_MainViewPager_Position = "Key_MainViewPager_Position";
    private static LruCache<String, Object> mLruCache = new LruCache<>(1 * M);
    private static ACache mDiskCache;

    public Integer getMainViewPagerPosition() {
        return getData(Key_MainViewPager_Position, 0);
    }

    public <T extends Serializable> T getData(@NonNull String key, @Nullable T defaultValue) {
        T result = (T) mLruCache.get(key);
        if (result != null) {
            return result;
        }
        result = (T) mDiskCache.getAsObject(key);
        if (result != null) {
            mLruCache.put(key, result);
            return result;
        }
        return defaultValue;
    }
}
