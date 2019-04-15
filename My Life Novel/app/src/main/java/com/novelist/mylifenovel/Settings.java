package com.novelist.mylifenovel;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Settings extends AppCompatActivity {

    ArrayList<TextView> textSizes; // {small, medium, big}

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
    }


    public void SaveSettings(View view) {
        new General().ClickEvent(this); // click sound
        // ToDo when SaveBtn pressed
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
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
            if (tv.getText().equals(textSize.getText())) {
                // ToDo when other text size selected
                textSize.setTypeface(null,  Typeface.BOLD);
            }
                else textSize.setTypeface(null,  Typeface.NORMAL);
        }
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
