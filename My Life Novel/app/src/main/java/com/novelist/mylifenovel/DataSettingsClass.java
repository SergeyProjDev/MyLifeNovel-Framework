package com.novelist.mylifenovel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



class DataSettingsClass{

    // const
    private final String SETTINGS = "Settings";
    enum TYPES{ INT, STR, BOOL, FLOAT }

    // new const here
    private final String CLICK_SOUND_EXISTENCE = "ClickExist";
    private final String MUSIC_LEVEL = "MusicLvl";
    private final String TEXT_SIZE = "TextSize";



    public void setClickVol(int exist, Activity a){
        save(CLICK_SOUND_EXISTENCE, exist, a);
    }
    public int getClickVol(Activity a){
        return load(CLICK_SOUND_EXISTENCE, 50, TYPES.INT, a);
    }


    public void setMusicVol(int lvl, Activity a){
        save(MUSIC_LEVEL, lvl, a);
    }
    public int getMusicVol(Activity a){
        return load(MUSIC_LEVEL, 50, TYPES.INT, a);
    }


    public void setTextSize(int choice, Activity a){
        int textSize = 14;

        if (choice == 0) textSize = 12; // small
        if (choice == 1) textSize = 16; // medium
        if (choice == 2) textSize = 18; // big

        save(TEXT_SIZE, textSize, a);
    }
    public int getTextSize(Activity a){
        return load(TEXT_SIZE, 14, TYPES.INT, a);
    }



    private <T> void save(String paramName, T data, Activity a){
        SharedPreferences.Editor editor = a.getSharedPreferences(SETTINGS,
                                                Context.MODE_WORLD_WRITEABLE).edit();

        if (data instanceof String) editor.putString(paramName, (String) data);
        if (data instanceof Integer) editor.putInt(paramName, (Integer) data);
        if (data instanceof Boolean) editor.putBoolean(paramName, (Boolean) data);
        if (data instanceof Float) editor.putFloat(paramName, (Float) data);

        editor.apply();
    }
    private <T> T load(String paramName, T defaultValue, TYPES type, Activity a) {

        if (type.equals(TYPES.INT))
            return (T)(Object) a.getSharedPreferences(SETTINGS,
                    Context.MODE_PRIVATE).getInt(paramName, (Integer) defaultValue);
        if (type.equals(TYPES.STR))
            return (T) a.getSharedPreferences(SETTINGS,
                    Context.MODE_PRIVATE).getString(paramName, (String)defaultValue);
        if (type.equals(TYPES.BOOL))
            return (T)(Object) a.getSharedPreferences(SETTINGS,
                    Context.MODE_PRIVATE).getBoolean(paramName, (Boolean) defaultValue);
        if (type.equals(TYPES.FLOAT))
            return (T)(Object) a.getSharedPreferences(SETTINGS,
                    Context.MODE_PRIVATE).getFloat(paramName, (Float) defaultValue);

        return null;
    }
}
