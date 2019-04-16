package com.novelist.mylifenovel;

import android.app.Activity;
import android.media.MediaPlayer;

import static android.media.ToneGenerator.MAX_VOLUME;


public class MusicPlayer extends Activity {

    public static MediaPlayer mediaPlayer;

    public static void startMusic(int id, Activity activity){

        try {
            mediaPlayer.stop();
        }catch(Exception ignored){}

        try{
            mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), id);
            mediaPlayer.setLooping(true);
            float volume = (float) (1 - (Math.log(MAX_VOLUME - new DataSettingsClass().getVolumeLvl(activity)) / Math.log(MAX_VOLUME)));
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.start();
        }catch (Exception ignored){}

    }
}