package com.developer.sangbarca.bkdictionary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.Activities.HistoryActivity;
import com.developer.sangbarca.bkdictionary.Activities.LoginActivity;
import com.developer.sangbarca.bkdictionary.Activities.RegisterActivity;
import com.developer.sangbarca.bkdictionary.Helper.BKRepo;
import com.developer.sangbarca.bkdictionary.Helper.RequestCode;
import com.developer.sangbarca.bkdictionary.Helper.Session;
import com.developer.sangbarca.bkdictionary.Models.UserResponse;
import com.developer.sangbarca.bkdictionary.Rest.ClientApi;
import com.developer.sangbarca.bkdictionary.Rest.UserApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Session session;
    SearchView sView;
    ListView lvOption;
    ListView lvFilter;
    ProgressBar pbXuLy;
    ArrayList<String> arrayHistory = new ArrayList<>();
    public static String url = "https://bk-dict.herokuapp.com/api/dict?key=";
    OptionAdapter adapter;
    ArrayList<Option> arrayOption;
    ArrayList<SearchResuls> arrayWord;
    SearchReultAdapter adapterFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        BKRepo bkRepo = new BKRepo();

        if(!bkRepo.isEmpty()){

            Log.d("Test Main","findCategory " + bkRepo.findCategoryByDictId(1));
            Log.d("Test Main","findExample " + bkRepo.findExampleByDictId(1));
            Log.d("Test Main","findMean  " + bkRepo.findMeanByDictId(1));
        }

    }

    private void setView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        session = new Session(this);
        setMenu();

        AnhXa();
        arrayWord = new ArrayList<SearchResuls>();
        pbXuLy.setVisibility(View.INVISIBLE);

        sView.setIconifiedByDefault(false);
        //-------------------------------------------------------------------//
        arrayOption = new ArrayList<>();
        arrayOption.add(new Option(R.drawable.dictionaryy, "Từ điển Việt Anh"));
        arrayOption.add(new Option(R.drawable.book, "Dịch văn bản"));
        arrayOption.add(new Option(R.drawable.translate, "Tra nhanh"));
        arrayOption.add(new Option(R.drawable.star, "Từ của bạn"));
        arrayOption.add(new Option(R.drawable.musical, "Học từ vựng qua bài hát"));
        arrayOption.add(new Option(R.drawable.settings, "Cài đặt"));

        adapter = new OptionAdapter(this, R.layout.dong_option, arrayOption);
        lvOption.setAdapter(adapter);
        //-----------------------------------------------------------------------//

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if(newText.length() > 0){
                    lvFilter.setVisibility(View.VISIBLE);
                    arrayWord.clear(); //delete this code if use search online
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new myOflineSearch().execute(newText);
                        }
                    });
                }else {
                    lvFilter.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

        adapterFilter = new SearchReultAdapter(this, R.layout.dong_search, arrayWord);
        lvFilter.setAdapter(adapterFilter);

        lvFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EnglishVietNamActivity.class);
                SearchResuls word = arrayWord.get(position);
                arrayHistory.add(word.name);
                intent.putExtra("name", word.name);
                startActivity(intent);
            }
        });
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

    ////   search offline //////
    public class myOflineSearch extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbXuLy.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            BKRepo data = new BKRepo();
            for(int i = 0; i < data.search(params[0]).size(); i++) {
                publishProgress(data.search(params[0]).get(i).getName());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            arrayWord.add(new SearchResuls(R.drawable.stars, values[0]));
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pbXuLy.setVisibility(View.INVISIBLE);
            adapterFilter.notifyDataSetChanged();
        }
    }


    /////// search online ///////
    public class myAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbXuLy.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(url+params[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                pbXuLy.setVisibility(View.INVISIBLE);
                arrayWord.clear();
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonWord = jsonArray.getJSONObject(i);
                    String name = jsonWord.getString("name");
                    arrayWord.add(new SearchResuls(R.drawable.stars, name));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapterFilter.notifyDataSetChanged();
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try{
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }

    private void AnhXa() {
        pbXuLy = (ProgressBar) findViewById(R.id.progressBarXuLy);
        lvFilter = (ListView) findViewById(R.id.listViewFilter);
        sView = (SearchView) findViewById(R.id.searchView);
        lvOption = (ListView) findViewById(R.id.listView);
    }

    public void setMenu(){
        // Check login
        Log.d(LoginActivity.TAG, session.getToken() + "adada");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        View header=navigationView.getHeaderView(0);
        TextView txt = (TextView) header.findViewById(R.id.txt_draw_name);

        if(session.getToken().isEmpty()){
            txt.setText("Xin chào Khách");
            menu.findItem(R.id.nav_dict).setVisible(false);
            menu.findItem(R.id.nav_history).setVisible(false);
            menu.findItem(R.id.nav_exit).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_signup).setVisible(true);

        }else{
            txt.setText("Xin chào " + session.get(Session.USERNAME));
            menu.findItem(R.id.nav_dict).setVisible(true);
            menu.findItem(R.id.nav_history).setVisible(true);
            menu.findItem(R.id.nav_exit).setVisible(true);
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_signup).setVisible(false);

        }
    }


    public void logout(){
        UserApi apiService =
                ClientApi.getClient().create(UserApi.class);
        Call<UserResponse> call = apiService.logout(session.get(Session.USER_ID));
        final ProgressDialog progressDoalog = new ProgressDialog(this);

        progressDoalog.setMax(100);
        progressDoalog.setMessage("Đăng xuất....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse>call, Response<UserResponse> response) {

                if(response.body().getStatus() == RequestCode.SUCCESS){
                    session.destroy();
                    progressDoalog.dismiss();
                    setMenu();
                    Toast.makeText(MainActivity.this, "Đăng xuất thành công" + response.body().getId(), Toast.LENGTH_SHORT).show();
                }else{
                    progressDoalog.dismiss();
                    Toast.makeText(MainActivity.this, "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse>call, Throwable t) {
                // Log error here since request failed
                progressDoalog.dismiss();
                Log.e(LoginActivity.TAG, t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putStringArrayListExtra("history", arrayHistory);
            startActivity(intent);

        } else if (id == R.id.nav_dict) {

        } else if (id == R.id.nav_login) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_signup) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_exit){
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
