package com.novelist.mylifenovel;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class GameActivity extends AppCompatActivity {

    //menu btns
    ImageView menuBackground;
    ImageView save;
    ImageView toMenu;
    ImageView settings;

    //database controls
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    Cursor queryRes;

    //output controls
    EditText output;
    TextView says;
    ImageView sprite1;
    ImageView sprite2;
    ImageView sprite3;
    ImageView choiceSprite;
    ImageView choice1Sprite; TextView choice1Text;
    ImageView choice2Sprite; TextView choice2Text;
    ConstraintLayout backGr;

    //menu enable or disable
    boolean show = false;

    //with choices works
    int choiceFirst, choiceSecond;
    int bgFlag;

    //class helper
    ComponentWorker screen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //fullscreen
        new General().MakeFullscreen(this);

        //click sound disable
        findViewById(R.id.back).setSoundEffectsEnabled(false);

        //init helper class
        screen = new ComponentWorker();

        //initialization controls
        screen.initComponents(this);

        //try connect to db
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseHelper.create_db();
            db = databaseHelper.open();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        //default queryRes content
        applyNewQuery("select * from day1;");
        queryRes.moveToFirst();
        queryRes.moveToPrevious();

        //show first shot
        next(null);
    }



    public void next(View view) {
        try {
            queryRes.moveToNext();

            String text        = queryRes.getString(2);
            String speaker_id  = queryRes.getString(3);
            String drawSprites = queryRes.getString(4);
            String music_id    = queryRes.getString(5);
            String backgr_id   = queryRes.getString(6);

            screen.putText   (text,       this);
            screen.putSays   (speaker_id, this);
            screen.putSprites(drawSprites,this);
            screen.putBackgr (backgr_id,  this);
            screen.putMusic  (music_id,    this);

        } catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    //buttons events
    public void showOrHide(View view) {
        new General().ClickEvent(this);
        show = !show;
        if (show){
            save.setVisibility(View.VISIBLE);
            toMenu.setVisibility(View.VISIBLE);
            settings .setVisibility(View.VISIBLE);
            menuBackground.setVisibility(View.VISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            toMenu.setVisibility(View.INVISIBLE);
            settings .setVisibility(View.INVISIBLE);
            menuBackground.setVisibility(View.INVISIBLE);
        }

    }

    public void save(View view) {
        new General().ClickEvent(this);
        Toast.makeText(this, "Not implemented Save", Toast.LENGTH_SHORT).show();
    }

    public void settings(View view) {
        new General().ClickEvent(this);
        startActivity(new Intent(this, Settings.class));
    }

    public void home(View view) {
        new General().ClickEvent(this);
        Intent i = new Intent(this, MainMenu.class);
        i.putExtra("from game", true);
        startActivity(i);
    }

    public void onBackPressed() {
        home(null);
    }


    public void ChoiceOne(View view) {
        applyNewQuery("select * from day1 where choice = "+choiceFirst+";");
        afterChoice();
    }
    public void ChoiceTwo(View view) {
        applyNewQuery("select * from day1 where choice = "+choiceSecond+";");
        afterChoice();
    }
    private void afterChoice(){
        choice1Sprite.setVisibility(View.INVISIBLE);
        choice2Sprite.setVisibility(View.INVISIBLE);

        choice1Text.setVisibility(View.INVISIBLE);
        choice2Text.setVisibility(View.INVISIBLE);

        choiceSprite.setVisibility(View.INVISIBLE);

        backGr.setEnabled(true);
        next(null);
    }


    public void applyNewQuery(String querySQL){
        queryRes =  db.rawQuery(querySQL, null);
    }


    public void BackShot(View view) {
        queryRes.moveToPrevious();
        queryRes.moveToPrevious();
        next(view);
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
        screen.startPlayingMusic(this);
    }


}



class ComponentWorker {
    public void initComponents(GameActivity ga){
        ga.output = ga.findViewById(R.id.output);
        ga.says = ga.findViewById(R.id.says);
        ga.sprite1 = ga.findViewById(R.id.sprite1);
        ga.sprite2 = ga.findViewById(R.id.sprite2);
        ga.sprite3 = ga.findViewById(R.id.sprite3);
        ga.choiceSprite = ga.findViewById(R.id.choiceSprite);
        ga.choice1Sprite = ga.findViewById(R.id.choice1Sprite);
        ga.choice1Text = ga.findViewById(R.id.choice1Text);
        ga.choice2Sprite = ga.findViewById(R.id.choice2Sprite);
        ga.choice2Text = ga.findViewById(R.id.choice2Text);
        ga.backGr = ga.findViewById(R.id.back);
        ga.save = ga.findViewById(R.id.saveBtn);
        ga.toMenu = ga.findViewById(R.id.homeBtn);
        ga.settings = ga.findViewById(R.id.settingsBtn);
        ga.menuBackground = ga.findViewById(R.id.background);
    }



    //DB values
    public void putText(String str, GameActivity ga){
        if ((str.charAt(0)) =='#') {
            ga.choiceSprite.setVisibility(View.VISIBLE);

            String first = str.substring(1, str.indexOf("/"));
            ga.choiceFirst = Integer.parseInt(first.substring(first.indexOf("(")+1,first.indexOf(")")));
            first = first.substring(first.indexOf(")")+1);

            String second = str.substring(str.indexOf("/")+1);
            ga.choiceSecond = Integer.parseInt(second.substring(second.indexOf("(")+1,second.indexOf(")")));
            second = second.substring(second.indexOf(")")+1);

            ga.choice1Text.setText(first);
            ga.choice2Text.setText(second);

            ga.choice1Sprite.setVisibility(View.VISIBLE);
            ga.choice2Sprite.setVisibility(View.VISIBLE);
            ga.choice1Text.setVisibility(View.VISIBLE);
            ga.choice2Text.setVisibility(View.VISIBLE);

            ga.backGr.setEnabled(false);
            return;
        }
        if ((str.charAt(0)) == '<') {
            int choice = Integer.parseInt(str.substring(str.indexOf("<") + 1, str.indexOf(">")));
            ga.applyNewQuery("select * from day1 where choice = " + choice + ";");

            str = str.substring(str.indexOf(">") + 1);
        }
        ga.output.setText(str);
    }

    public void putSays(String str, GameActivity ga){
        if (str == null) {
            ga.says.setVisibility(View.INVISIBLE);
            return;
        }

        int id = Integer.parseInt(str);

        Cursor queryResult;
        queryResult =  ga.db.rawQuery("select * from characters where id ="+id+";", null);
        queryResult.move(1);

        String name = queryResult.getString(1);

        ga.says.setVisibility(View.VISIBLE);
        ga.says.setText(name);
    }


    public void putSprites(String str, GameActivity ga) {
        if (str == null) {
            ga.sprite1.setVisibility(View.INVISIBLE);
            ga.sprite2.setVisibility(View.INVISIBLE);
            ga.sprite3.setVisibility(View.INVISIBLE);
            return;
        }

        if (str.matches("[0-9]+")) { //one sprite
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite1.setBackgroundResource(parseHelper(str, ga));
        }

        if (str.matches("[0-9]+[ ][0-9]+")) { //two sprites
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite2.setVisibility(View.VISIBLE);

            ga.sprite1.setBackgroundResource(parseHelper(str, ga));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite2.setBackgroundResource(parseHelper(str, ga));
        }

        if (str.matches("[0-9]+[ ][0-9]+[ ][0-9]+")) { //three sprites
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite2.setVisibility(View.VISIBLE);
            ga.sprite3.setVisibility(View.VISIBLE);

            ga.sprite1.setBackgroundResource(parseHelper(str, ga));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite2.setBackgroundResource(parseHelper(str, ga));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite3.setBackgroundResource(parseHelper(str, ga));
        }
    }


    public void putBackgr(String str, GameActivity ga){
        Cursor queryResult;
        int id, imageResource_id;
        String image;

        id = Integer.parseInt(str);
        if (id != ga.bgFlag){ //don`t update bg
            ga.bgFlag = id;

            queryResult =  ga.db.rawQuery("select * from backgrounds where id ="+id+";", null);
            queryResult.move(1);
            image = queryResult.getString(1);
            imageResource_id  = ga.getResources().getIdentifier(image, "drawable", ga.getPackageName());

            ga.backGr.setBackgroundResource(imageResource_id);
        }
    }


    private int musicPlaying;
    public void putMusic(String str, GameActivity ga){
        int id = Integer.parseInt(str);

        if (id == musicPlaying) return;

        Cursor queryResult;

        queryResult =  ga.db.rawQuery("select * from music where id ="+id+";", null);
        queryResult.move(1);
        String res = queryResult.getString(1);
        int res_id  = ga.getResources().getIdentifier(res, "raw", ga.getPackageName());

        musicPlaying = id;

        MusicPlayer.startMusic(res_id, ga);
    }

    public void startPlayingMusic(GameActivity ga){
        Cursor queryResult =  ga.db.rawQuery("select * from music where id ="+musicPlaying+";", null);
        queryResult.move(1);
        String res = queryResult.getString(1);
        int res_id  = ga.getResources().getIdentifier(res, "raw", ga.getPackageName());
        MusicPlayer.startMusic(res_id, ga);
    }



    private int parseHelper(String str, GameActivity ga){
        Cursor queryResult;
        int id, imageResource_id;
        String image;

        try{
            id = Integer.parseInt(str.substring(0, str.indexOf(" ")));
        } catch (Exception ex){ id = Integer.parseInt(str);}

        queryResult =  ga.db.rawQuery("select * from sprites where id ="+id+";", null);
        queryResult.move(1);
        image = queryResult.getString(1);
        imageResource_id  = ga.getResources().getIdentifier(image, "drawable", ga.getPackageName());
        return imageResource_id;
    }
}