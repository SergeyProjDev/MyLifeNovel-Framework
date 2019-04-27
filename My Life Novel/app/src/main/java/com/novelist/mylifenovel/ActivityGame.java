package com.novelist.mylifenovel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ActivityGame extends General {

    /* menu btns */
    ImageView menuBackground;
    ImageView toMenu;
    ImageView settings;
    ImageView backShot;
    ImageView hideUI;
    ImageView burgerMenu;

    /* database controls */
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    Cursor queryRes;
    String currentQuery;

    /* output controls */
    TypeWriter output;
    TextView says;
    ImageView sprite1;
    ImageView sprite2;
    ImageView sprite3;
    ImageView choiceSprite;
    ImageView choice1Sprite; TextView choice1Text;
    ImageView choice2Sprite; TextView choice2Text;
    ConstraintLayout backGr;

    /* menu enable/disable */
    boolean show = false;

    /* with choices works */
    int choiceFirst, choiceSecond;
    int bgFlag;

    /* class helper */
    ComponentWorker screen;

    /* ui shown flag */
    boolean UIComponentsVisible = false;

    /* content control */
    public static String day = "day1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MakeFullscreen(this);

        // init helper class & init components
        screen = new ComponentWorker(this);
        screen.initComponents();

        // try connect to db
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseHelper.create_db();
            db = databaseHelper.open();
        } catch (Exception ex) { Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show(); }

        // start new game
        currentQuery = "select * from "+day+";";
        applyQuery(currentQuery);
        queryRes.moveToFirst();
        queryRes.moveToPrevious();

        // if game loaded (deserialize)
        try{
            if (getIntent().getExtras().getBoolean("load")){
                DataSQLClass d = (DataSQLClass) new ObjectInputStream(getBaseContext()
                                                 .openFileInput("data.dat")).readObject();
                applyQuery(d.sqlquery);
                queryRes.move(d.counter);
            }
        }catch (Exception ignored){ }

        // show first shot
        next(null);
    }



    public void next(View view) {

        if (TypeWriter.isPrinting()) {
            TypeWriter.printAll(output);
            return;
        }

        // if UI was hidden -> show UI and Return
        if (UIComponentsVisible){
            ShowElements();
            return;
        }

        try {
            queryRes.moveToNext();

            String text = queryRes.getString(2);
            String speaker_id = queryRes.getString(3);
            String drawSprites = queryRes.getString(4);
            String music_id = queryRes.getString(5);
            String backgr_id = queryRes.getString(6);

            screen.putText   (text);
            screen.putSays   (speaker_id);
            screen.putSprites(drawSprites);
            screen.putBG     (backgr_id);
            screen.putMusic  (music_id);

        } catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }



    //buttons events
    public void showOrHide(View view) {
        ClickEvent(this); // click sound

        show = !show;
        if (show){
            toMenu.setVisibility(View.VISIBLE);
            settings .setVisibility(View.VISIBLE);
            menuBackground.setVisibility(View.VISIBLE);
        }
        else{
            toMenu.setVisibility(View.INVISIBLE);
            settings .setVisibility(View.INVISIBLE);
            menuBackground.setVisibility(View.INVISIBLE);
        }

    }

    public void settings(View view) {
        startActivity(new Intent(this, ActivitySettings.class));
    }

    public void home(View view) {
        ClickEvent(this); // click sound

        Intent i = new Intent(this, ActivityMainMenu.class);
        i.putExtra("from game", true);
        startActivity(i);
    }



    public void onBackPressed() {
        saveGame();
        home(null);
    }



    public void ChoiceOne(View view) {
        currentQuery = "select * from "+day+" where choice = "+choiceFirst+";";
        applyQuery(currentQuery);
        afterChoice();
    }
    public void ChoiceTwo(View view) {
        currentQuery = "select * from "+day+" where choice = "+choiceSecond+";";
        applyQuery(currentQuery);
        afterChoice();
    }
    private void afterChoice(){
        // here choice UI components become invisible

        choice1Sprite.setVisibility(View.INVISIBLE);
        choice2Sprite.setVisibility(View.INVISIBLE);

        choice1Text.setVisibility(View.INVISIBLE);
        choice2Text.setVisibility(View.INVISIBLE);

        choiceSprite.setVisibility(View.INVISIBLE);

        backGr.setEnabled(true);
        next(null);
    }



    public void applyQuery(String querySQL){
        queryRes =  db.rawQuery(querySQL, null);
    }


    public void BackShot(View view) {
        queryRes.moveToPrevious();
        queryRes.moveToPrevious();
        next(view);
    }


    public void HideElements(View view) {
        output.setVisibility(View.INVISIBLE);
        says.setVisibility(View.INVISIBLE);

        menuBackground.setVisibility(View.INVISIBLE);
        burgerMenu.setVisibility(View.INVISIBLE);
        toMenu.setVisibility(View.INVISIBLE);
        settings.setVisibility(View.INVISIBLE);

        hideUI.setVisibility(View.INVISIBLE);
        backShot.setVisibility(View.INVISIBLE);

        UIComponentsVisible = true;
    }
    public void ShowElements() {
        output.setVisibility(View.VISIBLE);
        if (!says.getText().equals(""))
            says.setVisibility(View.VISIBLE);

        burgerMenu.setVisibility(View.VISIBLE);

        hideUI.setVisibility(View.VISIBLE);
        backShot.setVisibility(View.VISIBLE);

        UIComponentsVisible = false;
    }


    @Override
    protected void onPause() {
         super.onPause();
         try {
             MusicPlayer.mediaPlayer.stop();
             MusicPlayer.mediaPlayer.release();
         }catch (Exception ignored){ }

         try {
            saveGame();
         }catch (Exception ex){ Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show(); }
    }

    @Override
    protected void onResume(){
        super.onResume();

        screen.startPlayingMusic();
        screen.initComponents(); // to confirm changes
    }

    private void saveGame(){
        DataSQLClass d = new DataSQLClass();
        d.sqlquery = currentQuery;
        d.counter = queryRes.getPosition();
        try{
            FileOutputStream fos = getBaseContext().openFileOutput("data.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(d);
            os.close();
            fos.close();
        }
        catch (Exception ex){ Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show(); }
    }
}