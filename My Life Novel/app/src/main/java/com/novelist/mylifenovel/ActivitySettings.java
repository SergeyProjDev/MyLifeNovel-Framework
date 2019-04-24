package com.novelist.mylifenovel;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;



public class ActivitySettings extends General {

    // choice {small, medium, big} text
    ArrayList<TextView> textSizes;
    int choicedText;

    // components
    protected SeekBar volumeClickLevel;
    protected SeekBar volumeMusicLevel;
    protected SeekBar textSpeed;
    protected SeekBar transitionTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MakeFullscreen(this);

        // init
        textSizes = new ArrayList<>(Arrays.asList(
                (TextView) findViewById(R.id.small),
                (TextView) findViewById(R.id.medium),
                (TextView) findViewById(R.id.big))
            );
        volumeClickLevel = findViewById(R.id.volume2);
        volumeMusicLevel = findViewById(R.id.volume);
        textSpeed = findViewById(R.id.textSpeed);
        transitionTime = findViewById(R.id.autoContinue);

        // dynamic listeners
        new SettingsDynamicVolumeChange(this).setForMusic(volumeMusicLevel);
        new SettingsDynamicVolumeChange(this).setForClick(volumeClickLevel);

        // text size make selected bold
        switch (new DataSettingsClass().getTextSize(this)){
            case DataSettingsClass.TEXT_SMALL: makeItBold(textSizes.get(0)); break;
            case DataSettingsClass.TEXT_MEDIUM:makeItBold(textSizes.get(1)); break;
            case DataSettingsClass.TEXT_BIG:   makeItBold(textSizes.get(2)); break;
        }

        // seekBar progress
        volumeClickLevel.setProgress(new DataSettingsClass().getClickVol(this)); // click lvl
        volumeMusicLevel.setProgress(new DataSettingsClass().getMusicVol(this)); // music lvl
        textSpeed.setProgress(100-new DataSettingsClass().getTextSpeed(this)); // text speed
        transitionTime.setProgress(new DataSettingsClass().getTransitionTime(this)); // transition time
    }


    public void SaveSettings(View view) {
        ClickEvent(this); // click sound

        new DataSettingsClass().setClickVol(volumeClickLevel.getProgress(), this); // mus lvl
        new DataSettingsClass().setMusicVol(volumeMusicLevel.getProgress(), this); // click lvl
        new DataSettingsClass().setTextSize(choicedText, this); // text size
        new DataSettingsClass().setTextSpeed(textSpeed.getProgress(), this); // text speed
        new DataSettingsClass().setTransitionTime(transitionTime.getProgress(), this); // auto transition

        GoBack();
    }

    public void GoBack() {
        ClickEvent(this); // click sound
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
}
