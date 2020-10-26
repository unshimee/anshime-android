package com.wtm.anshime.storage;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wtm.anshime.utils.Constants.APP_PREF;
import static com.wtm.anshime.utils.Constants.PREF_INIT_ERROR;


public class AppPreference {

    private static AppPreference instance;
    private Context context;
    private SharedPreferences preferences;

    public AppPreference(){ }

    public void setContext(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance() {
        if(instance == null){
            instance = new AppPreference();
        }
        return instance;
    }

    public void writeToPrefs(String key, String value) throws Exception {
        if(context == null || preferences == null){
            throw new Exception(PREF_INIT_ERROR);
        }
        preferences.edit().putString(key, value).apply();
    }

    public String readFromPrefs(String key) throws Exception{
        if(context == null || preferences == null){
            throw new Exception(PREF_INIT_ERROR);
        }
        String result = preferences.getString(key, "data not found");
        return result;
    }
}
