package com.novelist.mylifenovel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class TypeWriter extends EditText {

    private static boolean printing = false;
    private static CharSequence mText;
    private int mIndex;
    private long mDelay = 100; // Default 100ms delay
    private Thread typingSec;
    private Handler mHandler = new Handler();


    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            if(mIndex <= mText.length() && isPrinting()){
                setText(mText.subSequence(0, mIndex++));
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(final CharSequence text) {
        mText = text;
        mIndex = 0;
        printing = true;


        typingSec = new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(mDelay*text.length());
                    printing = false;
                }catch (Exception ignored){}
            }
        });
        typingSec.start();

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    public static boolean isPrinting(){
        return printing;
    }

    public static void printAll(EditText output){
        printing = false;
        output.setText(mText);
    }
}

