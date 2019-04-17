package com.novelist.mylifenovel;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH;
    private static String DB_NAME = "MyLifeNovel.db";
    private static final int SCHEMA = 1; // DB version

    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }

    @Override public void onCreate(SQLiteDatabase db) { }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }

    void create_db(){
        try {
            File file = new File(DB_PATH);
            this.getReadableDatabase();
            // DB in thread getted
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            // path to new DB
            String outFileName = DB_PATH;

            // byte copy data to new DB
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
                myOutput.write(buffer, 0, length);

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch(Exception ignored){ }
    }
    public SQLiteDatabase open()throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
