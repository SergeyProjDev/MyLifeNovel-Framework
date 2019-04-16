package com.novelist.mylifenovel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

class DataSettingsClass{

    private final String SETTINGS = "Settings";
    private final String CLICK_SOUND_EXISTENCE = "ClickExist";
    private final String MUSIC_LEVEL = "MusicLvl";

    public int getClickVolumeLvl(Activity a){
        return loadInt(CLICK_SOUND_EXISTENCE, a);
    }
    public void setClickLvl(int exist, Activity a){
        saveInt(CLICK_SOUND_EXISTENCE, exist, a);
    }
    public int getVolumeLvl(Activity a){
        return loadInt(MUSIC_LEVEL, a);
    }
    public void setVolumeLvl(int lvl, Activity a){
        saveInt(MUSIC_LEVEL, lvl, a);
    }




    /*
    private void saveBool(String paramName, boolean data, Activity a) {
        SharedPreferences.Editor editor = a.getSharedPreferences(SETTINGS, Context.MODE_WORLD_WRITEABLE).edit();
        editor.putBoolean(paramName, data);
        editor.apply();
    }
    private boolean loadBool(String paramName, Activity a) {
        return a.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE).getBoolean(paramName, true);
    }
    */

    private void saveInt(String paramName, int data, Activity a){
        SharedPreferences.Editor editor = a.getSharedPreferences(SETTINGS, Context.MODE_WORLD_WRITEABLE).edit();
        editor.putInt(paramName, data);
        editor.apply();
    }
    private int loadInt(String paramName, Activity a) {
        return a.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE).getInt(paramName, 50);
    }

}
