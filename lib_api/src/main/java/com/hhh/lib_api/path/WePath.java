package com.hhh.lib_api.path;

/**
 * Created by nova on 13/10/2017.
 */

public class WePath {
    public String path;
    public boolean auth;

    public WePath(String path) {
        this.path = path;
        this.auth = false;
    }

    public WePath(String path, boolean auth) {
        this.path = path;
        this.auth = auth;
    }

    public WePath() {
        this.path = "";
        this.auth = false;
    }
}
