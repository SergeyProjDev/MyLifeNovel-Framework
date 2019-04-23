package com.novelist.mylifenovel;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import static android.media.ToneGenerator.MAX_VOLUME;


public class General extends AppCompatActivity {

    protected void MakeFullscreen(Activity myActivityReference){
        myActivityReference.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // hide navigation bar
        if (Build.VERSION.SDK_INT > 10) {
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (android.os.Build.VERSION.SDK_INT >= 19)
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            myActivityReference.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
        else
            myActivityReference.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    protected void ClickEvent(final Activity activity){
        new Thread(new Runnable() {
            public void run() {
                MediaPlayer mp = MediaPlayer.create(activity.getApplicationContext(), R.raw.click);
                int progress = new DataSettingsClass().getClickVol(activity);
                float vol = (float)(1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                mp.setVolume(vol, vol);
                mp.start();
            }
        }).start();
    }

    @Override
    public void startActivity(Intent intent) {
        try{
            super.startActivity(intent);
            overridePendingTransition(R.anim.activity_open_enter, 0);
        } catch (Exception ignored){}
    }

    @Override
    public void finish() {
        try{
            super.finish();
            overridePendingTransition(R.anim.activity_close_bottom,0);
        } catch (Exception ignored){}
    }

    @Override
    protected void onPause() {
        try{
            super.onPause();
            MusicPlayer.mediaPlayer.stop();
            MusicPlayer.mediaPlayer.release();
        }catch (Exception ignored){}
    }

    @Override
    protected void onResume(){
        super.onResume();

        try{
            MusicPlayer.startMusic(R.raw.menu_music, this);
        }catch (Exception ignored){}

        try{
            overridePendingTransition(R.anim.activity_open_enter, 0);
        }catch (Exception ignored){}
    }

}
