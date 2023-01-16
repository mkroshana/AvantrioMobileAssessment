package com.avantrio.mobileassessment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass
{
    private static final String USER_PREF = "user_logs";
    private final SharedPreferences appShared;
    private final SharedPreferences.Editor prefsEditor;

    public SharedPreferenceClass(Context context)
    {
        appShared = context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
        this.prefsEditor = appShared.edit();
    }

    // int
    public int getValue_int(String key) {
        return appShared.getInt(key, 0);
    }
    public void setValue_int(String key, int value) {
        prefsEditor.putInt(key, value).commit();
    }

    public void allClear()
    {
        prefsEditor.remove("adapterPosition").commit();
    }
}
