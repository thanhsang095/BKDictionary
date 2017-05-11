package com.developer.sangbarca.bkdictionary.Helper;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.developer.sangbarca.bkdictionary.Models.Category;
import com.developer.sangbarca.bkdictionary.Models.Dict;
import com.developer.sangbarca.bkdictionary.Models.DictResponse;
import com.developer.sangbarca.bkdictionary.Models.MeanDictResponse;
import com.developer.sangbarca.bkdictionary.Models.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class BKRepo {

    private CategoryRepo mCategoryRepo;
    private ExampleRepo mExampleRepo;
    private OptionalRepo mOptionalRepo;
    private DictRepo mDictRepo;

    public BKRepo() {
        mCategoryRepo = new CategoryRepo();
        mExampleRepo = new ExampleRepo();
        mOptionalRepo = new OptionalRepo();
        mDictRepo = new DictRepo();
    }

    public void clear(){
        Log.d("TEST", "Clear ");
        mCategoryRepo.clear();
        mDictRepo.clear();
        mOptionalRepo.clear();
        mExampleRepo.clear();
    }

    public boolean backup(List<Category> cats, List<Optional>optionals, List<Dict> dicts){



        for(Category cat : cats){
            if(mCategoryRepo.insertCategory(cat)==-1) {
                return false;
            }
        }

        for(Optional optional : optionals){
            if(mOptionalRepo.insertOptional(optional)==-1){
                return false;
            }else{
                Log.d("Test", "Insert optional ");
            }
        }


        if(!mDictRepo.insertDict(dicts)){
            return false;
        }

        return true;
    }

    public ArrayList<DictResponse> search(String keyword){
        return mDictRepo.search(keyword);
    }

    public List<Category> findCategoryByDictId(int id) {
        return mCategoryRepo.findByDictId(id);
    }

    public List<String> findExampleByDictId(int id){
        return mDictRepo.findExampleByDictId(id );
    }

    public List<MeanDictResponse> findMeanByDictId(int id ){
        return mOptionalRepo.findOptionlByDictId(id);
    }

    public DictResponse get(int id){
        return mDictRepo.getDict(id);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        boolean b = checkEmpty(db,DBHelper.TABLE_DICTS);
        DBManger.getInstance().closeDatabase();
        return b;
    }

    public boolean checkEmpty(SQLiteDatabase db, String table){
        return DatabaseUtils.queryNumEntries(db, table) == 0;
    }
}
