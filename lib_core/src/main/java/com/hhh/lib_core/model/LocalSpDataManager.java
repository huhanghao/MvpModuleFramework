package com.hhh.lib_core.model;

import com.hhh.lib_core.utils.ResUtils;

import net.grandcentrix.tray.AppPreferences;

public class LocalSpDataManager {
    private static LocalSpDataManager mLocalSpDataManager;
    public static AppPreferences sp;

    public static LocalSpDataManager getManager() {
        if (sp == null) {
            sp = new AppPreferences(ResUtils.getAppContext());
            mLocalSpDataManager = new LocalSpDataManager();
        }
        return mLocalSpDataManager;
    }

    public AppPreferences getSp(){
        return sp;
    }

    public int getIntData(String key) {
        return sp.getInt(key, 0);
    }

    public String getStringData(String key) {
        return sp.getString(key, "");
    }


    public boolean getBooleanData(String key) {
        return sp.getBoolean(key, false);
    }

    public float getFloatData(String key) {
        return sp.getFloat(key, 0f);
    }

    public long getLongData(String key) {
        return sp.getLong(key, 0l);
    }
}