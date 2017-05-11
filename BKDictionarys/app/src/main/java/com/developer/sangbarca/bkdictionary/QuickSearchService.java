package com.developer.sangbarca.bkdictionary;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by NHUT on 5/8/2017.
 */

public class QuickSearchService extends Service {


    private WindowManager windowManager;
    private ImageView chatHead;
    private Boolean _enable = true;



    AutoCompleteTextView actvSearch;
    private final int WRITE_REQUEST_CODE = 0;
    private boolean option = true;

    private ClipboardManager clipboard ;



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        _enable=false;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        chatHead.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                chatHead.setVisibility(View.GONE);
            }

        }, 7000);


        return START_STICKY;



    }
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);

        chatHead.setImageResource(R.drawable.popup);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(chatHead, params);


        chatHead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LayoutInflater layoutInflater   = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow( popupView, WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setFocusable(true);
                popupWindow.update();
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                _enable=true;

                Button endService= (Button) popupView.findViewById(R.id.endService);

                actvSearch = (AutoCompleteTextView)popupView.findViewById(R.id.search);
                clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                actvSearch.setText( clipboard.getPrimaryClip().getItemAt(0).getText().toString());
                actvSearch.setFocusable(true);




                endService.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        stopSelf();
                        Toast.makeText(getApplicationContext(), "Service Terminated", Toast.LENGTH_LONG).show();
                    }
                });



                if(_enable){
                    popupWindow.showAsDropDown(chatHead, 50, -30);
                    _enable=false;
                }
                else if(!_enable) {

                    Log.d("FALSE", "FALSE");

                }



            }
        });
        try {

            chatHead.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // Get current time in nano seconds.

                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(chatHead, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }






}
