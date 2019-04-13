package com.novelist.mylifenovel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new General().MakeFullscreen(this);
    }


    public void SaveSettings(View view) {
        new General().ClickEvent(this);
        Toast.makeText(this, "Not implemented Save", Toast.LENGTH_SHORT).show();
    }

    public void GoBack(View view) {
        new General().ClickEvent(this);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        try{
            super.onPause();
            MusicPlayer.mediaPlayer.stop();
            MusicPlayer.mediaPlayer.release();
        }catch (Exception ex){}
    }

    @Override
    protected void onResume(){
        super.onResume();
        MusicPlayer.startMusic(R.raw.menu_music, this);
    }
}
