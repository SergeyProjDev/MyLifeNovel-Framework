package com.novelist.mylifenovel;

import android.database.Cursor;
import android.util.TypedValue;
import android.view.View;

class ComponentWorker {

    ActivityGame ga;
    ComponentWorker(ActivityGame activity){
        ga = activity;
    }

    boolean animatedText = true;
    private int musicPlaying;

    public void initComponents(){
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
        ga.toMenu = ga.findViewById(R.id.homeBtn);
        ga.settings = ga.findViewById(R.id.settingsBtn);
        ga.menuBackground = ga.findViewById(R.id.background);
        ga.hideUI = ga.findViewById(R.id.hideElements);
        ga.backShot = ga.findViewById(R.id.backShot);
        ga.burgerMenu = ga.findViewById(R.id.showOrHideAll);
        ga.output = ga.findViewById(R.id.outputLayout);

        // init text sizes
        ga.output.setTextSize(TypedValue.COMPLEX_UNIT_SP, new DataSettingsClass().getTextSize(ga));
        ga.says.setTextSize(TypedValue.COMPLEX_UNIT_SP, new DataSettingsClass().getTextSize(ga));

        // text speed
        int textSpeed = new DataSettingsClass().getTextSpeed(ga);
        if (textSpeed == 0)
            animatedText = false;
        else {
            ga.output.setCharacterDelay(textSpeed*3);
            ga.output.setAutoTransitionTime(new DataSettingsClass().getTransitionTime(ga));
            animatedText = true;
        }
    }


    public void putText(String str){
        ga.hideUI.setVisibility(View.VISIBLE);

        // choice `#(n1)/(n2)`
        if ((str.charAt(0)) =='#') {
            ga.hideUI.setVisibility(View.INVISIBLE);

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

        // GoTo choice `<n>`
        if ((str.charAt(0)) == '<') {
            int choice = Integer.parseInt(str.substring(str.indexOf("<") + 1, str.indexOf(">")));
            ga.applyQuery("select * from "+ ActivityGame.day+" where choice = " + choice + ";");

            str = str.substring(str.indexOf(">") + 1);
        }

        if (animatedText) ga.output.animateText(str, ga);
            else ga.output.setText(str);

    }
    public void putSays(String str){
        if (str == null) {
            ga.says.setText("");
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
    public void putSprites(String str) {
        if (str == null) {
            ga.sprite1.setVisibility(View.INVISIBLE);
            ga.sprite2.setVisibility(View.INVISIBLE);
            ga.sprite3.setVisibility(View.INVISIBLE);
            return;
        }

        // one sprite
        if (str.matches("[0-9]+")) {
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite1.setBackgroundResource(parseHelper(str));
        }

        // two sprites
        if (str.matches("[0-9]+[ ][0-9]+")) {
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite2.setVisibility(View.VISIBLE);

            ga.sprite1.setBackgroundResource(parseHelper(str));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite2.setBackgroundResource(parseHelper(str));
        }

        // three sprites
        if (str.matches("[0-9]+[ ][0-9]+[ ][0-9]+")) {
            ga.sprite1.setVisibility(View.VISIBLE);
            ga.sprite2.setVisibility(View.VISIBLE);
            ga.sprite3.setVisibility(View.VISIBLE);

            ga.sprite1.setBackgroundResource(parseHelper(str));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite2.setBackgroundResource(parseHelper(str));
            str = str.substring(0, str.indexOf(" ")+1);
            ga.sprite3.setBackgroundResource(parseHelper(str));
        }
    }
    public void putBG(String str){
        Cursor queryResult;
        int id, imgRes_id;
        String image;

        id = Integer.parseInt(str);
        if (id != ga.bgFlag){ // don`t update bg
            ga.bgFlag = id;
            String query = "select * from backgrounds where id ="+id+";";
            queryResult =  ga.db.rawQuery(query, null);
            queryResult.move(1);
            image = queryResult.getString(1);
            imgRes_id = ga.getResources().getIdentifier(image,"drawable",ga.getPackageName());

            ga.backGr.setBackgroundResource(imgRes_id);
        }
    }
    public void putMusic(String str){
        int id = Integer.parseInt(str);

        if (id == musicPlaying) return;

        Cursor queryResult;

        String query = "select * from music where id ="+id+";";
        queryResult =  ga.db.rawQuery(query, null);
        queryResult.move(1);
        String res = queryResult.getString(1);
        int res_id  = ga.getResources().getIdentifier(res, "raw", ga.getPackageName());

        musicPlaying = id;

        MusicPlayer.startMusic(res_id, ga);
    }


    public void startPlayingMusic(){
        String query = "select * from music where id ="+musicPlaying+";";
        Cursor queryResult =  ga.db.rawQuery(query, null);
        queryResult.move(1);
        String res = queryResult.getString(1);
        int res_id  = ga.getResources().getIdentifier(res, "raw", ga.getPackageName());
        MusicPlayer.startMusic(res_id, ga);
    }


    private int parseHelper(String str){
        int id;
        try{
            id = Integer.parseInt(str.substring(0, str.indexOf(" ")));
        } catch (Exception ignored){
            id = Integer.parseInt(str);
        }
        String query = "select * from sprites where id ="+id+";";
        Cursor queryResult =  ga.db.rawQuery(query, null);
        queryResult.move(1);
        return ga.getResources().getIdentifier(queryResult.getString(1),"drawable", ga.getPackageName());
    }
}
