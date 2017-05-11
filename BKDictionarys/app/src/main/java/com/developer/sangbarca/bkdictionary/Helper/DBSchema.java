package com.developer.sangbarca.bkdictionary.Helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.sangbarca.bkdictionary.Rest.BKApplication;

/**
 * Created by nhat on 07/05/2017.
 */

public class DBSchema extends SQLiteOpenHelper {



    public DBSchema() {
        super(BKApplication.getContext(), DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBHelper.CREATE_TABLE_DICTS);
        db.execSQL(DBHelper.CREATE_TABLE_EXAMPLES);
        db.execSQL(DBHelper.CREATE_TABLE_CATEGORIES);
        db.execSQL(DBHelper.CREATE_TABLE_OPTIONALS);
        db.execSQL(DBHelper.CREATE_TABLE_DICTS_CATEGORIES);
        db.execSQL(DBHelper.CREATE_TABLE_MEANDICT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_DICTS);
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_CATEGORIES);
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_OPTIONALS);
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_EXAMPLES);
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_DICTS_CATEGORIES);
        db.execSQL(DBHelper.DROP_TABLE + DBHelper.TABLE_MEANDICT);

        onCreate(db);
    }



    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
