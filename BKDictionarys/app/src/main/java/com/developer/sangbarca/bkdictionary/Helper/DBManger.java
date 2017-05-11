package com.developer.sangbarca.bkdictionary.Helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nhat on 07/05/2017.
 */


// Xu li bat dong bo

public class DBManger {
    private int openCounter = 0;
    private static DBManger instance;
    private static SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase database;

    public static synchronized void initInstance(SQLiteOpenHelper helper){
        if(instance == null){
            instance = new DBManger();
            databaseHelper = helper;
        }
    }

    public static synchronized DBManger getInstance(){
        if(instance == null) throw new IllegalStateException("Loi khoi tao");
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase(){
        openCounter += 1;
        if(openCounter == 1){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase(){
        openCounter -= 1;
        if(openCounter == 0){
            database.close();
        }
    }

}
