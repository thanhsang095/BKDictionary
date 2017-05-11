package com.developer.sangbarca.bkdictionary.Helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.developer.sangbarca.bkdictionary.Models.Category;
import com.developer.sangbarca.bkdictionary.Models.Dict;
import com.developer.sangbarca.bkdictionary.Models.DictResponse;
import com.developer.sangbarca.bkdictionary.Models.MeanDict;
import com.developer.sangbarca.bkdictionary.Models.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class DictRepo {

    private CategoryRepo mCategoryRepo;

    private OptionalRepo mOptionalRepo;

    public DictRepo() {
        mCategoryRepo = new CategoryRepo();
        mOptionalRepo = new OptionalRepo();
    }


    public boolean insertDict(List<Dict> dicts){

        boolean result = true;


        for(Dict dict : dicts) {


            SQLiteDatabase db = DBManger.getInstance().openDatabase();

            long id = db.insert(
                    DBHelper.TABLE_DICTS,
                    null,
                    dict.getValues()
            );
            if (id == -1) {
                result = false;
                break;
            }
            DBManger.getInstance().closeDatabase();

            List<String> categories = dict.getCategory();
            for (String cat : categories) {
                Category ca = mCategoryRepo.getCategory(cat);
                if (ca != null) {
                    Log.d("Test Insert ", "cat " + ca.getName() + " " + ca.getId() + " id_dict = " + id);
                    db = DBManger.getInstance().openDatabase();
                    long cat_id = ca.getPrimaryKey();
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.TB_DICTS_CATEGORIES.DICT_ID, id);
                    values.put(DBHelper.TB_DICTS_CATEGORIES.CATEGORIES_ID, cat_id);
                    if (db.insert(DBHelper.TABLE_DICTS_CATEGORIES, null, values) == -1) {
                        result = false;
                        break;
                    }
                    DBManger.getInstance().closeDatabase();
                }
            }

            List<String> examples = dict.getExample();

            for (String example : examples) {
                db = DBManger.getInstance().openDatabase();
                ContentValues values = new ContentValues();
                values.put(DBHelper.TB_EXAMPLES.DICTS_ID, id);
                values.put(DBHelper.TB_EXAMPLES.CONTENT, example);
                if (db.insert(DBHelper.TABLE_EXAMPLES, null, values) == -1) {
                    result = false;
                    break;
                }
                DBManger.getInstance().closeDatabase();
            }

            List<MeanDict> meanDicts = dict.getOptional();
            Log.d("Test Insert ", "tesr meandict " + meanDicts.size());

            for (MeanDict meanDict : meanDicts) {
                Optional op = mOptionalRepo.getOptional(meanDict.getName());
                if (op != null) {
                    long optional_id = op.getPrimaryKey();
                    Log.d("Test Insert ", "cat " + op.getName() + " id_dict = " + id);
                    for (String mean : meanDict.getContent()) {
                        db = DBManger.getInstance().openDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DBHelper.TB_MEANDICT.DICT_ID, id);
                        values.put(DBHelper.TB_MEANDICT.OPTIONAL_ID, optional_id);
                        values.put(DBHelper.TB_MEANDICT.CONTENT, mean);
                        if (db.insert(DBHelper.TABLE_MEANDICT, null, values) == -1) {
                            result = false;
                            break;
                        }else{
                            Log.d("Test Insert ", "zo");
                        }
                        DBManger.getInstance().closeDatabase();
                    }
                }
            }

            result = true;
        }
        return result;
    }


//    List<String> categories = dict.getCategory();
//
//                for (String cat : categories) {
//                    Category ca = mCategoryRepo.getCategory(cat);
//                    if(ca != null) {
//                        long cat_id = ca.getPrimaryKey();
//                        ContentValues values = new ContentValues();
//                        values.put(DBHelper.TB_DICTS_CATEGORIES.DICT_ID, id);
//                        values.put(DBHelper.TB_DICTS_CATEGORIES.CATEGORIES_ID, cat_id);
//                        if (db.insert(DBHelper.TABLE_DICTS_CATEGORIES, null, values) == -1) {
//                            throw new InsertException("Lỗi insert Dict Category");
//                        }
//                    }
//                }
//
//                List<String> examples = dict.getExample();
//
//                for (String example : examples) {
//                    ContentValues values = new ContentValues();
//                    values.put(DBHelper.TB_EXAMPLES.DICTS_ID, id);
//                    values.put(DBHelper.TB_EXAMPLES.CONTENT, example);
//                    if (db.insert(DBHelper.TABLE_EXAMPLES, null, values) == -1) {
//                        throw new InsertException("Lỗi insert TABLE_EXAMPLES");
//                    }
//                }
//
//                List<MeanDict> meanDicts = dict.getOptional();
//                for (MeanDict meanDict : meanDicts) {
//                    Optional op = mOptionalRepo.getOptional(meanDict.getId());
//                    if(op != null) {
//                        long optional_id = op.getPrimaryKey();
//                        for (String mean : meanDict.getContent()) {
//                            ContentValues values = new ContentValues();
//                            values.put(DBHelper.TB_MEANDICT.DICT_ID, id);
//                            values.put(DBHelper.TB_MEANDICT.OPTIONAL_ID, optional_id);
//                            values.put(DBHelper.TB_MEANDICT.CONTENT, mean);
//                            if (db.insert(DBHelper.TABLE_MEANDICT, null, values) == -1) {
//                                throw new InsertException("Lỗi insert TABLE_MEANDICT");
//                            }
//                        }
//                    }
//                }
//
//                result = true;

    public class InsertException extends Exception{

        public InsertException(String message) {
            super(message);
        }
    }

    public boolean  isEmpty() {

        return getAllDicts().isEmpty();
    }


    public ArrayList<DictResponse> search(String key) {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        ArrayList<DictResponse> notes = new ArrayList<>();
        Cursor c = db.query(
                DBHelper.TABLE_DICTS,
                null,
                DBHelper.TB_DICTS.NAME + DBHelper.LIKE_CONDITION,
                new String[]{"%" + key + "%"},
                null,
                null,
                DBHelper.TB_DICTS.PRIMARY_KEY + DBHelper.SORT_DESC
        );
        if ( c.moveToFirst()) {

            do {
                DictResponse note = new DictResponse();
                note.setPrimaryKey(c.getInt( c.getColumnIndexOrThrow( DBHelper.TB_DICTS.PRIMARY_KEY )));
                note.setId(c.getString( c.getColumnIndexOrThrow( DBHelper.TB_DICTS.ID )));
                note.setName(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.NAME)));
                note.setPronounce(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.PRONOUNCE)));
                notes.add(note);
            } while (c.moveToNext());
            c.close();


        }
        DBManger.getInstance().closeDatabase();
        return notes;
    }


    public List<String> findExampleByDictId(int id ){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_EXAMPLES
                + " WHERE " + DBHelper.TB_EXAMPLES.DICTS_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> examples = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                examples.add(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_EXAMPLES.CONTENT)));
            } while (cursor.moveToNext());
        }
        DBManger.getInstance().closeDatabase();
        return examples;
    }

    /*

                // Tìm tất cả các category

                String selectQuery = "SELECT  ct.* FROM " + DBHelper.TABLE_CATEGORIES + "ct JOIN "
                        + DBHelper.TABLE_DICTS_CATEGORIES + " ON " + DBHelper.TB_CATEGORIES.PRIMARY_KEY + " = " + DBHelper.TB_DICTS_CATEGORIES.CATEGORIES_ID
                        + " WHERE " + DBHelper.TB_DICTS_CATEGORIES.DICT_ID + " = " + note.getPrimaryKey();
                Cursor cursorCate = db.rawQuery(selectQuery, null);

                ArrayList<Category> categories =new ArrayList<>();
                while (!cursorCate.isAfterLast()) {
                    Category obj = new Category();
                    obj.setPrimaryKey(cursorCate.getInt( cursorCate.getColumnIndexOrThrow( DBHelper.TB_CATEGORIES.PRIMARY_KEY )));
                    obj.setId(cursorCate.getString( cursorCate.getColumnIndexOrThrow( DBHelper.TB_CATEGORIES.ID )));
                    obj.setName(cursorCate.getString(cursorCate.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.NAME)));
                    obj.setDescription(cursorCate.getString(cursorCate.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.DESCRIPTION)));
                    categories.add(obj);
                }

                cursorCate.close();
                note.setCategory(categories);

                // Tìm tất cả các example

                selectQuery = "SELECT  * FROM " + DBHelper.TABLE_EXAMPLES
                        + " WHERE " + DBHelper.TB_EXAMPLES.DICTS_ID + " = " + note.getPrimaryKey();
                Cursor cursorExam = db.rawQuery(selectQuery, null);

                ArrayList<String> examples = new ArrayList<>();
                while (!cursorExam.isAfterLast()) {
                    examples.add(cursorExam.getString(cursorExam.getColumnIndexOrThrow(DBHelper.TB_EXAMPLES.CONTENT)));
                }
                note.setExample(examples);

                cursorExam.close();
                // Lấy tất cả các optional

                // B1 Tìm tất cả các meanDict
                selectQuery = "SELECT * FROM " + DBHelper.TABLE_MEANDICT + " JOIN " + DBHelper.TABLE_OPTIONALS
                        + " ON " + DBHelper.TB_MEANDICT.OPTIONAL_ID + " = " + DBHelper.TB_OPTIONALS.PRIMARY_KEY
                        + " WHERE " + DBHelper.TB_EXAMPLES.DICTS_ID + " = " + note.getPrimaryKey();
                Cursor cursorMean= db.rawQuery(selectQuery, null);
                ArrayList<MeanDictResponse> meanDictResponses = new ArrayList<>();
                while(!cursorMean.isAfterLast()) {
                    MeanDictResponse meanDictResponse = new MeanDictResponse();
                    meanDictResponse.setMean(cursorMean.getString(cursorMean.getColumnIndexOrThrow(DBHelper.TB_MEANDICT.CONTENT)));
                    meanDictResponse.setNameVi(cursorMean.getString(cursorMean.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME_VI)));
                    meanDictResponse.setName(cursorMean.getString(cursorMean.getColumnIndexOrThrow(DBHelper.TB_OPTIONALS.NAME)));
                    meanDictResponses.add(meanDictResponse);
                }

                cursorMean.close();
                note.setOptional(meanDictResponses);

                // Sau khi thêm => node mới


     */

    public ArrayList<DictResponse> getAllDicts() {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        ArrayList<DictResponse> notes = new ArrayList<>();
        Cursor c = db.query(
                DBHelper.TABLE_DICTS,
                null,
                null,
                null,
                null,
                null,
                DBHelper.TB_DICTS.PRIMARY_KEY + DBHelper.SORT_DESC
        );
        if ( c!= null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                DictResponse note = new DictResponse();
                note.setPrimaryKey(c.getInt(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.PRIMARY_KEY)));
                note.setId(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.ID)));
                note.setName(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.NAME)));
                note.setPronounce(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.PRONOUNCE)));
                notes.add(note);
                c.moveToNext();
            }
            c.close();

        }
        DBManger.getInstance().closeDatabase();
        return notes;
    }



    public DictResponse getDict(int id){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        DictResponse note = null;
        Cursor c = db.query(
                DBHelper.TABLE_DICTS,
                null,
                DBHelper.TB_DICTS.ID + DBHelper.WHERE_CONDITION,
                new String[]{Integer.toString(id)},
                null,
                null,
                null
        );
        if ( c!= null) {
            c.moveToFirst();
            note = new DictResponse();
            note.setPrimaryKey(c.getInt(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.PRIMARY_KEY)));
            note.setId(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.ID)));
            note.setName(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.NAME)));
            note.setPronounce(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_DICTS.PRONOUNCE)));
            c.close();

        }
        DBManger.getInstance().closeDatabase();
        return note;
    }

    public void clear(){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        db.delete(DBHelper.TABLE_DICTS, null, null);
        db.delete(DBHelper.TABLE_MEANDICT, null, null);
        db.delete(DBHelper.TABLE_DICTS_CATEGORIES, null, null);
        DBManger.getInstance().closeDatabase();
    }


}
