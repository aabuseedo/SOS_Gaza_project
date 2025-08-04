package com.example.sosgaza.core;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPrefs {
    private static AppSharedPrefs instance;
    private final SharedPreferences sharedPreferences;

    private AppSharedPrefs(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(Constants.APP_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static synchronized AppSharedPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new AppSharedPrefs(context);
        }
        return instance;
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }
}
