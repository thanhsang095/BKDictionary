package com.developer.sangbarca.bkdictionary.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.Helper.RequestCode;
import com.developer.sangbarca.bkdictionary.Helper.Session;
import com.developer.sangbarca.bkdictionary.Models.UserResponse;
import com.developer.sangbarca.bkdictionary.R;
import com.developer.sangbarca.bkdictionary.Rest.ClientApi;
import com.developer.sangbarca.bkdictionary.Rest.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NHUT on 5/7/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText txtName, txtPass;
    private Button btn;
    public static final String TAG2 = "test";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setView();
        session = new Session(this);
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Toast.makeText(this, "Có mạng ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    public void setView(){
        txtName = (EditText) findViewById(R.id.txtrUsername);
        txtPass = (EditText) findViewById(R.id.textPass);
        btn = (Button) findViewById(R.id.btnSubmit);
        btn.setOnClickListener(this);
    }
    public void onClick(View v) {

        if(isNetworkAvailable()){
            switch (v.getId()){
                case R.id.btnSubmit:
                    if(txtName.getText().toString().matches(""))
                    {
                        Toast.makeText(this,"Please Enter Username",Toast.LENGTH_SHORT).show();
                        break;

                    }
                    else {
                        regis(txtName.getText().toString(), txtPass.getText().toString());
                        break;
                    }
            }
        }else{
            Toast.makeText(this, "Kiểm tra kết nối mạng !", Toast.LENGTH_SHORT).show();
        }



    }




    public void regis(final String username, String password){

        UserApi apiService =
                ClientApi.getClient().create(UserApi.class);
        Call<UserResponse> call = apiService.login(username, password);
        final ProgressDialog progressDoalog = new ProgressDialog(this);

        progressDoalog.setMax(100);
        progressDoalog.setMessage("Please Wait..");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse>call, Response<UserResponse> response) {

                if(response.body().getStatus() == RequestCode.SUCCESS){
                    session.putToken(response.body().getToken());
                    session.put(Session.USERNAME, username);
                    session.put(Session.USER_ID, response.body().getId());
                    progressDoalog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công" + response.body().getId(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    progressDoalog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                // Log error here since request failed
                progressDoalog.dismiss();
                Log.e(TAG2, t.toString());
            }
        });
    }

}
