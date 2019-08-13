package com.infosmart.registerSmartag.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static Activity activity;

    public static void setActivity(Activity activity) {
        Utils.activity = activity;
    }

    public static SharedPreferences getSharedPreferences() {
        return activity.getSharedPreferences("registration", Context.MODE_PRIVATE);
    }
}
