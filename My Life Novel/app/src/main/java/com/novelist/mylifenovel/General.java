package com.novelist.mylifenovel;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class General extends AppCompatActivity {

    public void MakeFullscreen(Activity myActivityReference){
        myActivityReference.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hide navigation bar
        if (Build.VERSION.SDK_INT > 10) {
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (isImmersiveAvailable())
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


            myActivityReference.getWindow().getDecorView().setSystemUiVisibility(flags);
        } else
            myActivityReference.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    public static boolean isImmersiveAvailable() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    public void ClickEvent(final Activity myActivityReference){
        new Thread(new Runnable() {
            public void run() {
                MediaPlayer.create(myActivityReference.getApplicationContext(), R.raw.click).start();
            }
        }).start();

    }
}
