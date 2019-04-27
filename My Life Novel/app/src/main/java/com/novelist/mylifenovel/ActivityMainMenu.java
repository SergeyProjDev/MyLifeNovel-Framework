package com.novelist.mylifenovel;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ObjectInputStream;

public class ActivityMainMenu extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MakeFullscreen(this);

        // draw resume png
        try{
            if (getIntent().getExtras().getBoolean("from game"))
                findViewById(R.id.resume).setVisibility(View.VISIBLE);
        } catch (Exception ignored){ }

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
        ClickEvent(this); // click sound
        startActivity(new Intent(this, ActivityGame.class));
    }



    public void ContinueGame(View view) {
        ClickEvent(this); //click sound
        Intent i = new Intent(this, ActivityGame.class);
        i.putExtra("load", true);
        startActivity(i);
    }



    public void OpenSettings(View view) {
        startActivity(new Intent(this, ActivitySettings.class));
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void ExitGame(View view) {
        ClickEvent(this); // click sound
        this.finishAffinity();
    }



    public void ShowAbout(View view) {
        ClickEvent(this); // click sound
        startActivity(new Intent(this, ActivityAbout.class));
    }


    public void ResumeGame(View view) {
        ClickEvent(this); // click sound
        super.onBackPressed();
    }

    @Override
    public void onBackPressed(){
        ClickEvent(this);
    }
}
