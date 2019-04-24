package com.novelist.mylifenovel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class TypeWriter extends EditText {

    private static boolean printing = false;
    private static CharSequence mText;
    private int mIndex;
    private long mDelay = 100;
    private Handler mHandler = new Handler();
    private int autoTransitionTime;
    private CountDownTimer cdt;

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

                try {cdt.cancel();} catch (Exception ignored) {}
                cdt = new CountDownTimer(autoTransitionTime, autoTransitionTime) {
                        public void onTick(long millisUntilFinished) { }
                        public void onFinish() {
                            gameA.next(null);
                        }
                    };
                cdt.start();
            }

        }
    };

    ActivityGame gameA;
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

    public static void printAll(EditText output){
        printing = false;
        output.setText(mText);
    }
}

