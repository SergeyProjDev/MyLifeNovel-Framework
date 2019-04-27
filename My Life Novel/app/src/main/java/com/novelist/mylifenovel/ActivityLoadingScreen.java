package com.novelist.mylifenovel;

import android.content.Intent;
import android.os.Bundle;

public class ActivityLoadingScreen extends General{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MusicPlayer.startMusic(R.raw.menu_music, this);
        startActivity(new Intent(this, ActivityMainMenu.class));
        finish();

    }
}
