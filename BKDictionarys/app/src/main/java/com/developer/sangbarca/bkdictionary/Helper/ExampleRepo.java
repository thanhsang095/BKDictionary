package com.developer.sangbarca.bkdictionary.Helper;

import android.database.sqlite.SQLiteDatabase;

import com.developer.sangbarca.bkdictionary.Models.Category;
import com.developer.sangbarca.bkdictionary.Models.Example;

/**
 * Created by nhat on 07/05/2017.
 */

public class ExampleRepo {

    public ExampleRepo() {
    }



    public long insertExample(Example obj){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        long id = db.insert(
                DBHelper.TABLE_EXAMPLES,
                null,
                obj.getValues()
        );
        DBManger.getInstance().closeDatabase();

        return id;
    }

    public int updateCategory(Category cate) {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        int id= db.update(DBHelper.TABLE_EXAMPLES, cate.getValues(), DBHelper.TB_DICTS.ID + DBHelper.WHERE_CONDITION,
                new String[] { cate.getId() });

        DBManger.getInstance().closeDatabase();
        return id;
    }

    public void clear(){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        db.delete(DBHelper.TABLE_EXAMPLES, null, null);
        DBManger.getInstance().closeDatabase();
    }

}
