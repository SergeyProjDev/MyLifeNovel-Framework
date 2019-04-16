package com.novelist.mylifenovel;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import static android.media.ToneGenerator.MAX_VOLUME;

public class SettingsDynamicVolumeChange {

    Activity activity;
    SettingsDynamicVolumeChange(Activity a){
        activity = a;
    }

    public void setForMusic(SeekBar sb){
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float volume = (float) (1 - (Math.log(MAX_VOLUME - seekBar.getProgress()) / Math.log(MAX_VOLUME)));
                MusicPlayer.mediaPlayer.setVolume(volume, volume);
            }
        });
    }

    public void setForClick(SeekBar sb){

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float volume = (float) (1 - (Math.log(MAX_VOLUME - seekBar.getProgress()) / Math.log(MAX_VOLUME)));
                MediaPlayer mp = MediaPlayer.create(activity, R.raw.click);
                mp.setVolume(volume, volume);
                mp.start();
            }
        });
    }
}
