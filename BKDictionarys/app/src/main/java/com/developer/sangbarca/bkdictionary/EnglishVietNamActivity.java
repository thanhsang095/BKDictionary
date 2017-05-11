package com.developer.sangbarca.bkdictionary;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.Fragment.FragmentAnhViet;
import com.developer.sangbarca.bkdictionary.Fragment.FragmentGhiChu;
import com.developer.sangbarca.bkdictionary.Fragment.ViewPagerAdapter;
import com.developer.sangbarca.bkdictionary.Helper.BKRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.developer.sangbarca.bkdictionary.MainActivity.url;

public class EnglishVietNamActivity extends AppCompatActivity {
    ProgressBar pbXulySecond;
    TabLayout myTabLayout;
    ViewPager myViewPager;

    Bundle bundle = new Bundle();
    FragmentAnhViet fragmentAnhViet = new FragmentAnhViet();
    FragmentGhiChu fragmentGhiChu = new FragmentGhiChu();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();

    BKRepo dataOfline = new BKRepo();

    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_viet_nam);
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        AnhXa();


        viewPagerAdapter.AddFragment(fragmentAnhViet, "ANH-VIỆT");
        viewPagerAdapter.AddFragment(fragmentGhiChu, "GHI CHÚ");

        myViewPager.setAdapter(viewPagerAdapter);
        myTabLayout.setupWithViewPager(myViewPager);

        pbXulySecond.setVisibility(View.INVISIBLE);

        Intent myIntent = getIntent();


        if(myIntent != null){
            final String name = myIntent.getStringExtra("name");
            if(!dataOfline.isEmpty()) {
                int id = dataOfline.search(name).get(0).getPrimaryKey();
                bundle.putString("getName", dataOfline.search(name).get(0).getName().toString());
                bundle.putString("getPronouce", dataOfline.search(name).get(0).getPronounce().toString());
                bundle.putString("getCategory", dataOfline.findCategoryByDictId(id).get(0).getName().toString());
                bundle.putString("getTuLoai", dataOfline.findMeanByDictId(id).get(0).getNameVi());
                bundle.putString("getMean", dataOfline.findMeanByDictId(id).get(0).getMean());
                if(dataOfline.findExampleByDictId(id).size() == 0){
                    bundle.putString("getExample", "updating ...");
                }else {
                    bundle.putString("getExample", dataOfline.findExampleByDictId(id).get(0));
                }

                fragmentAnhViet.setArguments(bundle);
                transaction.commit();
            }
        }else {
            Toast.makeText(this, "null intent!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView sViewSecond = (SearchView) menu.findItem(R.id.menuSearch).getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    public class mAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbXulySecond.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(url+params[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                if(jsonArray != null){
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void AnhXa(){
        pbXulySecond = (ProgressBar) findViewById(R.id.progressBarXuLySecond);
        myTabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
    }
}
