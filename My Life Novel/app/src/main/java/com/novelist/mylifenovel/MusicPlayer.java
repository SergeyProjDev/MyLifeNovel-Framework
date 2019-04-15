package com.novelist.mylifenovel;

import android.app.Activity;
import android.media.MediaPlayer;


public class MusicPlayer extends Activity {

    static MediaPlayer mediaPlayer;

    public static void startMusic(int id, Activity ga){

        try {
            mediaPlayer.stop();
        }catch(Exception ignored){}

        try{
            mediaPlayer = MediaPlayer.create(ga.getApplicationContext(), id);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            //mediaPlayer.setVolume(
            //      new DataSettingsClass().volumeMusic,
            //      new DataSettingsClass().volumeMusic
            // );
        }catch (Exception ignored){}

    }
}