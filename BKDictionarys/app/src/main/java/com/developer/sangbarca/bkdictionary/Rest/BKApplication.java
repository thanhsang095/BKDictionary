package com.developer.sangbarca.bkdictionary.Rest;

import android.app.Application;
import android.content.Context;

import com.developer.sangbarca.bkdictionary.Helper.DBManger;
import com.developer.sangbarca.bkdictionary.Helper.DBSchema;

/**
 * Created by nhat on 06/05/2017.
 */

public class BKApplication extends Application {


    private static Context context;
    private static DBSchema dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBSchema();
        DBManger.initInstance(dbHelper);
    }

    public static Context getContext(){
        return context;
    }

}
