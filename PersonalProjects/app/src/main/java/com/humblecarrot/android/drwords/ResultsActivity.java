package com.humblecarrot.android.drwords;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.humblecarrot.android.drwords.data.WordDetails;
import com.humblecarrot.android.drwords.data.WordResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    private WordsAdapter mAdapter;
    public static List<WordResult> resultsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_list);

        ListView resultsListView = (ListView) findViewById(R.id.results_list);
        resultsList = MainActivity.resultsList;
        mAdapter = mAdapter = MainActivity.mAdapter;
        resultsListView.setAdapter(mAdapter);


        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResultsActivity.this, SingleResultActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });



        //         List<WordResult> wordResults =  extractResultsFromJson(jsonResult);

    }



}
