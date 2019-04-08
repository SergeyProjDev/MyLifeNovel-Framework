package com.novelist.mylifenovel;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class General extends AppCompatActivity {

    public void MakeFullscreen(Activity myActivityReference){
        myActivityReference.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }




    //public static MediaPlayer mediaPlayer;
    public static void StartMusic(Activity myActivityReference, int id){
        //mediaPlayer = MediaPlayer.create(myActivityReference.getApplicationContext(), id);
        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();
    }

    public void ClickEvent(final Activity myActivityReference){
        new Thread(new Runnable() {
            public void run() {
                MediaPlayer.create(myActivityReference.getApplicationContext(), R.raw.click).start();
            }
        }).start();

    }
}
