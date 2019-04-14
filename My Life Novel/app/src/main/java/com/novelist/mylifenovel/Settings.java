package com.novelist.mylifenovel;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new General().MakeFullscreen(this);

        small = findViewById(R.id.small);
        medium = findViewById(R.id.medium);
        big = findViewById(R.id.big);
    }


    public void SaveSettings(View view) {
        new General().ClickEvent(this);
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
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



    TextView small, medium, big;

    public void SmallText(View view) {
        small.setTypeface(null, Typeface.BOLD);
        medium.setTypeface(null, Typeface.NORMAL);
        big.setTypeface(null, Typeface.NORMAL);

        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }
    public void MediumText(View view) {
        small.setTypeface(null, Typeface.NORMAL);
        medium.setTypeface(null, Typeface.BOLD);
        big.setTypeface(null, Typeface.NORMAL);

        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }
    public void BigText(View view) {
        small.setTypeface(null, Typeface.NORMAL);
        medium.setTypeface(null, Typeface.NORMAL);
        big.setTypeface(null, Typeface.BOLD);

        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }
}
