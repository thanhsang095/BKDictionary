package com.developer.sangbarca.bkdictionary.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nhat on 06/05/2017.
 */

public class Session  {
    public static final String PREFERENCE = "AKJA9012JCOX";
    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String USER_ID = "id";
    private SharedPreferences sharedPreferences;
    private Context context ;

    public Session(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public void putToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken()
    {
        String token = sharedPreferences.getString(TOKEN, "");
        return token;
    }

    public void destroyToken(){
        this.putToken("");
    }


    public void put(String key, String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String get(String key)
    {
        String token = sharedPreferences.getString(key, "");
        return token;
    }

    public void destroy (){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}
