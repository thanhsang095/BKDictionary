package com.developer.sangbarca.bkdictionary.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.QuickSearchService;
import com.developer.sangbarca.bkdictionary.R;

/**
 * Created by NHUT on 5/7/2017.
 */

public class QuickSearchActivity extends AppCompatActivity implements View.OnClickListener {


    TextView txtMsg;
    ComponentName service;
    Intent intentMyService3;

    BroadcastReceiver receiver;
    Switch swi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_search);
        //txtMsg = (TextView) findViewById(R.id.txtMsg);

        intentMyService3 = new Intent(getBaseContext() , QuickSearchService.class);
        //intentMyService4 = new Intent( MyService.class,SearchService.class);

        txtMsg.setText("MyService started ");
        //findViewById(R.id.btnStopService).setOnClickListener(this);
        //findViewById(R.id.btnStartButton).setOnClickListener(this);
        swi = (Switch) findViewById(R.id.switch1);
        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                if (isChecked) {
                    service = startService(intentMyService3);
                    Toast.makeText(getApplicationContext(), "Turn on Quick Translation", Toast.LENGTH_LONG).show();

                }
                else{
                    try {
                        stopService(intentMyService3);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("matos.action.GOSERVICE3");
        receiver = new MyMainLocalReceiver();
        registerReceiver(receiver, mainFilter);
    }
    /*  public void onClick(View v) {
  // assume: v.getId() == R.id.btnStopService
          if (v.getId() == R.id.btnStopService) {
              try {
                  stopService(intentMyService3);
                  //   txtMsg.setText("After stoping Service: \n" + service.getClassName());
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
          else if (v.getId() == R.id.btnStartButton){

              service = startService(intentMyService3);
              Toast.makeText(getApplicationContext(), "Turn on Quick Translation", Toast.LENGTH_LONG).show();

          }
      }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //stopService(intentMyService3);
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e ("MAIN3-DESTROY>>>", e.getMessage() );
        }
        Log.e ("MAIN3-DESTROY>>>" , "Adios" );
    }

    @Override
    public void onClick(View v) {

    }

    public class MyMainLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent callerIntent) {
            String serviceData = callerIntent.getStringExtra("service3Data");
            Log.e("MAIN>>>", "Data received from Service3: " + serviceData);
            String now = "\nService3Data: > " + serviceData;
            txtMsg.append(now);
        }

    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                startService(intentMyService1);
                txtMsg.setText("MyService1 started\n (see LogCat)");
                break;
            case R.id.btnStopService:
                try {
                    stopService(intentMyService1);
                  // txtMsg.setText("After stopping Service: \n" + service.getClassName());
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        }


    }*/
}
