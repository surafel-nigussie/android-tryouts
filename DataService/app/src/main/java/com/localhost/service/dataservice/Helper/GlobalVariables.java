package com.localhost.service.dataservice.Helper;

import android.app.Application;

public class GlobalVariables extends Application {
    private static String Token = null;

    public static String getToken() {
        return Token;
    }

    public static void setToken(String token) {
        Token = token;
    }
}
