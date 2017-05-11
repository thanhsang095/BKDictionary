package com.developer.sangbarca.bkdictionary.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.EnglishVietNamActivity;
import com.developer.sangbarca.bkdictionary.R;
import com.developer.sangbarca.bkdictionary.SearchResuls;
import com.developer.sangbarca.bkdictionary.SearchReultAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ListView lvHistory;
    SearchView sViewHistory;
    ArrayList<SearchResuls> arrayList;
    ArrayList<String> arrayName;
    SearchReultAdapter adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lvHistory = (ListView) findViewById(R.id.listViewHistory);
        sViewHistory = (SearchView) findViewById(R.id.searchViewHistory);

        sViewHistory.setIconifiedByDefault(false);
        arrayList = new ArrayList<>();
        arrayName = new ArrayList<>();

        Intent myIntent = getIntent();
        if(myIntent != null){
            arrayName = myIntent.getStringArrayListExtra("history");
            for(int i = 0; i < arrayName.size(); i++) {
                arrayList.add(new SearchResuls(R.drawable.stars, arrayName.get(i)));
            }
            adapterSearch = new SearchReultAdapter(this, R.layout.dong_search, arrayList);
            lvHistory.setAdapter(adapterSearch);

            sViewHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapterSearch.filter(newText.trim());
                    return false;
                }
            });

            lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HistoryActivity.this, EnglishVietNamActivity.class);
                    String name = arrayName.get(position);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });
        }else {
            Toast.makeText(this, "null intent!", Toast.LENGTH_SHORT).show();
        }
    }
}
