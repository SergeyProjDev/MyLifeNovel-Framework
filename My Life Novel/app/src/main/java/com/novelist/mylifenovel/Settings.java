package com.novelist.mylifenovel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class Settings extends AppCompatActivity {

    ArrayList<TextView> textSizes; // {small, medium, big}

    protected SeekBar volumeClickLevel;
    protected SeekBar volumeMusicLevel;

    public Activity contextSettings = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new General().MakeFullscreen(this);

        textSizes = new ArrayList<>(Arrays.asList(
                (TextView) findViewById(R.id.small),
                (TextView) findViewById(R.id.medium),
                (TextView) findViewById(R.id.big))
            );
        // init
        volumeClickLevel = findViewById(R.id.volume2);
        volumeMusicLevel = findViewById(R.id.volume);
        new SettingsDynamicVolumeChange(this).setForMusic(volumeMusicLevel);
        new SettingsDynamicVolumeChange(this).setForClick(volumeClickLevel);

        // get values
        volumeClickLevel.setProgress(new DataSettingsClass().getClickVolumeLvl(this));
        volumeMusicLevel.setProgress(new DataSettingsClass().getVolumeLvl(this));
    }


    public void SaveSettings(View view) {
        new General().ClickEvent(this); // click sound

        new DataSettingsClass().setClickLvl(volumeClickLevel.getProgress(), this);
        new DataSettingsClass().setVolumeLvl(volumeMusicLevel.getProgress(), this);

        GoBack(null);
    }

    public void GoBack(View view) {
        new General().ClickEvent(this); // click sound
        super.onBackPressed();
    }



    public void SmallText(View view) {
        makeItBold(textSizes.get(0));
    }
    public void MediumText(View view){
        makeItBold(textSizes.get(1));
    }
    public void BigText(View view)   {
        makeItBold(textSizes.get(2));
    }

    private void makeItBold(TextView tv){
        new General().ClickEvent(this); // click sound
        for (TextView textSize:textSizes) {
            if (tv.getText().equals(textSize.getText())) textSize.setTypeface(null,  Typeface.BOLD);
                else textSize.setTypeface(null,  Typeface.NORMAL);
        }
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
        MusicPlayer.startMusic(R.raw.menu_music, this);
    }
}
