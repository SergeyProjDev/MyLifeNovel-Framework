package com.novelist.mylifenovel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



class DataSettingsClass{

    // const
    private final String SETTINGS = "ActivitySettings";
    enum TYPES{ INT, STR, BOOL, FLOAT }

    // text sizes
    public final static int TEXT_SMALL = 12;
    public final static int TEXT_MEDIUM = 16;
    public final static int TEXT_BIG = 18;


    // new const here
    private final String CLICK_SOUND_EXISTENCE = "ClickExist";
    private final String MUSIC_LEVEL = "MusicLvl";
    private final String TEXT_SIZE = "TextSize";
    private final String TEXT_SPEED = "TextSpeed";



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
        int textSize = TEXT_MEDIUM;

        if (choice == 0) textSize = TEXT_SMALL; // small
        if (choice == 1) textSize = TEXT_MEDIUM; // medium
        if (choice == 2) textSize = TEXT_BIG; // big

        save(TEXT_SIZE, textSize, a);
    }
    public int getTextSize(Activity a){
        return load(TEXT_SIZE, TEXT_MEDIUM, TYPES.INT, a);
    }


    public void setTextSpeed(int speed, Activity a){
        int speedCounted = 100-speed;
        save(TEXT_SPEED, speedCounted, a);
    }
    public int getTextSpeed(Activity a){
        return load(TEXT_SPEED, 50, TYPES.INT, a);
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
