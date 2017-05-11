package com.developer.sangbarca.bkdictionary.Helper;

import android.os.AsyncTask;

import com.developer.sangbarca.bkdictionary.Models.BackUpResponse;
import com.developer.sangbarca.bkdictionary.Rest.BKCallBack;


public class BKAsynTask extends AsyncTask<BackUpResponse ,Void ,Boolean>{

    public BKCallBack callback = null;

    public BKAsynTask(BKCallBack callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(BackUpResponse... params) {
        BackUpResponse backUpResponse = params[0];
        BKRepo bkRepo = new BKRepo();
        if(bkRepo.isEmpty()){
            return bkRepo.backup(backUpResponse.getCategories(),backUpResponse.getOptionals(),backUpResponse.getDicts());
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            callback.onUpdated(new BKRepo());
        else
            callback.onFailed("Chịu chết update ");
    }
}
