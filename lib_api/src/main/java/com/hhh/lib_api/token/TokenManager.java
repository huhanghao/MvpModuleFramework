package com.hhh.lib_api.token;

import android.content.Context;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;


/**
 * Created by nova on 12/10/2017.
 */

public class TokenManager extends TrayPreferences {

    private static final String NAME = "xlt";

    private static TokenManager INSTANCE;

    private static final String KEY_TOKEN = "token";

    private TokenManager(@NonNull Context context) {
        super(context, NAME, 1);
    }

    public static void init(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new TokenManager(ctx);
        }
    }

    public static TokenManager getInstance() {
        return INSTANCE;
    }

    public void setToken(String token) {
        put(KEY_TOKEN, token);
    }

    public String getToken() {
        return getString(KEY_TOKEN, null);
    }

}
