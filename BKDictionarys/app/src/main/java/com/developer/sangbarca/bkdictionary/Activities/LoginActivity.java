package com.developer.sangbarca.bkdictionary.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText txtName, txtPass;
    private Button btn;
    public static final String TAG = "test";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        session = new Session(this);
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "Có mạng ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void setView(){
        txtName = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPassword);
        btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(isNetworkAvailable()){
            switch (v.getId()){
                case R.id.btnLogin:
                    login(txtName.getText().toString(), txtPass.getText().toString());
                    break;
            }
        }else{
            Toast.makeText(this, "Kiểm tra kết nối mạng !", Toast.LENGTH_SHORT).show();
        }



    }

    public void login(final String username, String password){

        UserApi apiService =
                ClientApi.getClient().create(UserApi.class);
        Call<UserResponse> call = apiService.login(username, password);
        final ProgressDialog progressDoalog = new ProgressDialog(this);

        progressDoalog.setMax(100);
        progressDoalog.setMessage("Đăng nhập....");
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
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công" + response.body().getId(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    progressDoalog.dismiss();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                // Log error here since request failed
                progressDoalog.dismiss();
                Log.e(TAG, t.toString());
            }
        });
    }

}
