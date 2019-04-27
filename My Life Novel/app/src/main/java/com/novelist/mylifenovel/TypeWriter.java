package com.novelist.mylifenovel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class TypeWriter extends EditText {


    protected static CharSequence mText;
    protected int mIndex;
    protected long mDelay = 100;
    protected Handler mHandler = new Handler();

    protected static boolean printing = false;
    protected int autoTransitionTime;
    protected CountDownTimer timer;
    protected ActivityGame gameA;


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
            else {
                printing = false;
                autoTransitionTimer();
            }

        }
    };


    public void animateText(final CharSequence text, ActivityGame activity) {
        mText = text;
        mIndex = 0;
        printing = true;
        gameA = activity;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    public void setAutoTransitionTime(int time){
        autoTransitionTime = time * 100;
    }

    public static boolean isPrinting(){
        return printing;
    }

    private void autoTransitionTimer(){
        try {
            timer.cancel();
        } catch (Exception ignored) {}

        timer = new CountDownTimer(autoTransitionTime, autoTransitionTime) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                gameA.next(null);
            }
        };

        timer.start();
    }

    public static void printAll(EditText output){
        printing = false;
        output.setText(mText);
    }
}

