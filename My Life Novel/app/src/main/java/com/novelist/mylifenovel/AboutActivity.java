package com.novelist.mylifenovel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        new General().MakeFullscreen(this);
    }


    public void GoBack(View view) {
        new General().ClickEvent(this);
        super.onBackPressed();
    }
}
