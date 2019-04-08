package com.novelist.mylifenovel;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        new General().MakeFullscreen(this);

        try{
            if (getIntent().getExtras().getBoolean("from game"))
                findViewById(R.id.resume).setVisibility(View.VISIBLE);
        } catch (Exception e){ }

        //try{ MusicPlayer.startMusic(R.raw.menu_music, this); }catch (Exception ex){}
    }






    public void NewGame(View view) {
        new General().ClickEvent(this);
        startActivity(new Intent(this, GameActivity.class));
    }


    public void ContinueGame(View view) {
        new General().ClickEvent(this);

        Toast.makeText(this, "Not implemented serialization get", Toast.LENGTH_SHORT).show();
    }


    public void OpenSettings(View view) {
        new General().ClickEvent(this);
        startActivity(new Intent(this, Settings.class));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void ExitGame(View view) {
        new General().ClickEvent(this);
        this.finishAffinity();
    }


    public void ShowAbout(View view) {
        new General().ClickEvent(this);
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void ResumeGame(View view) {
        new General().ClickEvent(this);
        super.onBackPressed();
    }

/*
    @Override
    protected void onPause() {
        try{
            super.onPause();
            MusicPlayer.mediaPlayer.stop();
            MusicPlayer.mediaPlayer.release();
        }catch (Exception ex){}
    }
*/
}
