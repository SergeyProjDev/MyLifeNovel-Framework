package com.novelist.mylifenovel;

import android.content.Intent;
import android.os.Bundle;

public class ActivityLoadingScreen extends General{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayer.startMusic(R.raw.menu_music, this);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) { }


        startActivity(new Intent(this, ActivityMainMenu.class));
        finish();
    }
}
