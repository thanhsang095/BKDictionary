package com.developer.sangbarca.bkdictionary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.developer.sangbarca.bkdictionary.Helper.BKRepo;
import com.developer.sangbarca.bkdictionary.R;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        BKRepo bkRepo = new BKRepo();
        if(!bkRepo.isEmpty()){
            Log.d("Test","DỮ liệu đã có -> onUpdated");
        }
    }
}
