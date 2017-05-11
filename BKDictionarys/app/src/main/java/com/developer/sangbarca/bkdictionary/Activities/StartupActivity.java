package com.developer.sangbarca.bkdictionary.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.developer.sangbarca.bkdictionary.Helper.BKAsynTask;
import com.developer.sangbarca.bkdictionary.Helper.BKRepo;
import com.developer.sangbarca.bkdictionary.MainActivity;
import com.developer.sangbarca.bkdictionary.Models.BackUpResponse;
import com.developer.sangbarca.bkdictionary.R;
import com.developer.sangbarca.bkdictionary.Rest.BKCallBack;
import com.developer.sangbarca.bkdictionary.Rest.ClientApi;
import com.developer.sangbarca.bkdictionary.Rest.DictApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartupActivity extends AppCompatActivity implements BKCallBack, Callback<BackUpResponse> {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        progressBar = (ProgressBar) findViewById(R.id.progressBarBackup) ;
        progressBar.setVisibility(View.INVISIBLE);
        BKRepo bkRepo = new BKRepo();
        bkRepo.clear();
        DictApi apiService =
                ClientApi.getClient().create(DictApi.class);
        Call<BackUpResponse> call = apiService.backupData();

        if(bkRepo.isEmpty()){

            if(isNetworkAvailable()) {
                progressBar.setVisibility(View.VISIBLE);
                call.enqueue(this);
            }else{
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Kết nối");
                alertDialog.setMessage("Vui lòng kiểm tra lại mạng ?");
                alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }

        }else{
            Log.d("Test","DỮ liệu đã có -> onUpdated");
            this.onUpdated(bkRepo);
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("Test ", "Có mạng ");
            return true;
        }
        return false;
    }


    @Override
    public void onUpdated(BKRepo bkRepo) {
        progressBar.setVisibility(View.INVISIBLE);
        Intent inten = new Intent(this, MainActivity.class);
        startActivity(inten);
        finish();
    }

    @Override
    public void onFailed(String message) {
        Log.d("Test ", message);
    }

    @Override
    public void onResponse(Call<BackUpResponse> call, Response<BackUpResponse> response) {
        BKAsynTask bkAsynTask = new BKAsynTask(this);
        bkAsynTask.execute(response.body());
    }

    @Override
    public void onFailure(Call<BackUpResponse> call, Throwable t) {

    }
}
