package com.novelist.mylifenovel;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;



public class Settings extends AppCompatActivity {

    // choice {small, medium, big} text
    ArrayList<TextView> textSizes;
    int choicedText;

    // components
    protected SeekBar volumeClickLevel;
    protected SeekBar volumeMusicLevel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new General().MakeFullscreen(this);

        // init
        textSizes = new ArrayList<>(Arrays.asList(
                (TextView) findViewById(R.id.small),
                (TextView) findViewById(R.id.medium),
                (TextView) findViewById(R.id.big))
            );
        volumeClickLevel = findViewById(R.id.volume2);
        volumeMusicLevel = findViewById(R.id.volume);

        // dynamic listeners
        new SettingsDynamicVolumeChange(this).setForMusic(volumeMusicLevel);
        new SettingsDynamicVolumeChange(this).setForClick(volumeClickLevel);


        volumeClickLevel.setProgress(new DataSettingsClass().getClickVol(this)); // click lvl
        volumeMusicLevel.setProgress(new DataSettingsClass().getMusicVol(this)); // music lvl

        // text size make selected bold
        switch (new DataSettingsClass().getTextSize(this)){
            case 12: makeItBold(textSizes.get(0));
            case 16: makeItBold(textSizes.get(1));
            case 18: makeItBold(textSizes.get(2));
        }
    }


    public void SaveSettings(View view) {
        new General().ClickEvent(this); // click sound

        new DataSettingsClass().setClickVol(volumeClickLevel.getProgress(), this); // mus lvl
        new DataSettingsClass().setMusicVol(volumeMusicLevel.getProgress(), this); // click lvl
        new DataSettingsClass().setTextSize(choicedText, this); // text size

        GoBack(null);
    }

    public void GoBack(View view) {
        new General().ClickEvent(this); // click sound
        super.onBackPressed();
    }



    public void SmallText (View view) {makeItBold(textSizes.get(0));}
    public void MediumText(View view) {makeItBold(textSizes.get(1));}
    public void BigText   (View view) {makeItBold(textSizes.get(2));}

    private void makeItBold(TextView tv){
        new SettingsDynamicVolumeChange(this).makeClick(volumeClickLevel); // click special sound

        int i = 0;
        for (TextView textSize:textSizes) {
            if (tv.getText().equals(textSize.getText())) {
                choicedText = i;
                textSize.setTypeface(null,  Typeface.BOLD);
            }
                else
                    textSize.setTypeface(null,  Typeface.NORMAL);
            i++;
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
