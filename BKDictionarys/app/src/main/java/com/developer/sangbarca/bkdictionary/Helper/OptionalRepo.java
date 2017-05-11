package com.developer.sangbarca.bkdictionary.Helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.sangbarca.bkdictionary.Models.MeanDictResponse;
import com.developer.sangbarca.bkdictionary.Models.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class OptionalRepo {

    public OptionalRepo() {
    }
    public long insertOptional(Optional obj){

        SQLiteDatabase db = DBManger.getInstance().openDatabase();

        long id = db.insert(
                DBHelper.TABLE_OPTIONALS,
                null,
                obj.getValues()

        );

        DBManger.getInstance().closeDatabase();
        return id;
    }

    public List<MeanDictResponse> findOptionlByDictId(int id ){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM " + DBHelper.TABLE_MEANDICT + " md JOIN " + DBHelper.TABLE_OPTIONALS
                + " op ON md." + DBHelper.TB_MEANDICT.OPTIONAL_ID + " = op." + DBHelper.TB_OPTIONALS.PRIMARY_KEY
                + " WHERE md." + DBHelper.TB_EXAMPLES.DICTS_ID + " = " + id;
        Cursor cursor= db.rawQuery(selectQuery, null);
        ArrayList<MeanDictResponse> meanDictResponses = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                MeanDictResponse meanDictResponse = new MeanDictResponse();
                meanDictResponse.setMean(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_MEANDICT.CONTENT)));
                meanDictResponse.setNameVi(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME_VI)));
                meanDictResponse.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME)));
                meanDictResponses.add(meanDictResponse);
            } while (cursor.moveToNext());
        }
        DBManger.getInstance().closeDatabase();
        return meanDictResponses;
    }

    public Optional getOptional(String key) {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        Optional obj = null;
        String query = "SELECT * FROM " + DBHelper.TABLE_OPTIONALS + " WHERE " + DBHelper.TB_OPTIONALS.ID + " = '" + key + "'";
        Cursor c = db.rawQuery(query, null);
        if ( c.moveToFirst()) {
            obj = new Optional();
            obj.setPrimaryKey(c.getInt(c.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.PRIMARY_KEY)));
            obj.setId(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.ID)));
            obj.setName(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME)));
            obj.setNameVi(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME_VI)));
            c.close();
        }
        DBManger.getInstance().closeDatabase();
        return obj;

    }

    public void clear(){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        db.delete(DBHelper.TABLE_OPTIONALS, null, null);
        DBManger.getInstance().closeDatabase();
    }

}
