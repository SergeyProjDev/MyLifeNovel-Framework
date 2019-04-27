package com.novelist.mylifenovel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityThanksForMaterials extends General {

    LinearLayout output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_for_materials);
        MakeFullscreen(this);

        output = findViewById(R.id.outputLayout);

        addFromDB();
    }


    public void GoBack(View view) {
        ClickEvent(this); // click sound
        super.onBackPressed();
    }

    private void addFromDB(){
        SQLiteDatabase db;

        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();

        try{
            String query = "SELECT * FROM resources;";
            Cursor queryRes =  db.rawQuery(query, null);
            queryRes.move(1);

            for (int i = 1; i <= queryRes.getCount(); ++i){
                TextView textView = new TextView(this);
                textView.setText(i + ". " +queryRes.getString(1));
                output.addView(textView);
                queryRes.moveToNext();
            }
        }  catch (Exception ex) { Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show(); }


    }
}
