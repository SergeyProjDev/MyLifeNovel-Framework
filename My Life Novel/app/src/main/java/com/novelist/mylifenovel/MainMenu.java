package com.novelist.mylifenovel;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ObjectInputStream;

public class MainMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        new General().MakeFullscreen(this);

        // draw resume png
        try{
            if (getIntent().getExtras().getBoolean("from game"))
                findViewById(R.id.resume).setVisibility(View.VISIBLE);
        } catch (Exception ignored){ }


        // start music
        try {
            MusicPlayer.startMusic(R.raw.menu_music, this);
        } catch (Exception ignored){}


        // enable loadBtn (if file with saves don`t exist)
        Button load = findViewById(R.id.continueBtn);
        try{
            new ObjectInputStream(getBaseContext().openFileInput("data.dat")).readObject();
            load.setEnabled(true);
            load.setTextColor(Color.BLACK);
        } catch (Exception ignored){
            load.setEnabled(false);
            load.setTextColor(Color.GRAY);
        }
    }



    public void NewGame(View view) {
        new General().ClickEvent(this); // click sound
        startActivity(new Intent(this, GameActivity.class));
    }



    public void ContinueGame(View view) {
        new General().ClickEvent(this); //click sound
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("load save", true);
        startActivity(i);
    }



    public void OpenSettings(View view) {
        startActivity(new Intent(this, Settings.class));
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void ExitGame(View view) {
        new General().ClickEvent(this); // click sound
        this.finishAffinity();
    }



    public void ShowAbout(View view) {
        new General().ClickEvent(this); // click sound
        startActivity(new Intent(this, AboutActivity.class));
    }


    public void ResumeGame(View view) {
        new General().ClickEvent(this); // click sound
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
