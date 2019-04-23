package com.novelist.mylifenovel;

import android.os.Bundle;
import android.view.View;

public class ActivityAbout extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        MakeFullscreen(this);
    }


    public void GoBack(View view) {
        ClickEvent(this); // click sound
        super.onBackPressed();
    }

}
