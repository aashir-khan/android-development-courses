package com.humblecarrot.android.drwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.humblecarrot.android.drwords.data.WordDetails;
import com.humblecarrot.android.drwords.data.WordResult;


import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    private WordsAdapter mAdapter = null;
    public static List<WordResult> resultsList;
    private Intent intent;
    public static WordDetails wordDetails;
    private String userInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_list);

        resultsList = new ArrayList<>();
        wordDetails = new WordDetails();

        userInput = getIntent().getStringExtra("userInput");

        intent = new Intent(ResultsActivity.this, SingleResultActivity.class);

        ListView resultsListView = (ListView) findViewById(R.id.results_list);

        String callingActivityName = getIntent().getStringExtra("callingActivity");
        if (callingActivityName == null || callingActivityName.equals
                ("SingleResultActivity")) {
            wordDetails = SingleResultActivity.wordDetails;
            resultsList = wordDetails.getResultsList();
            mAdapter = SingleResultActivity.mAdapter;
//            intent.putExtra("wantGetWordDetailDetails", true);
        } else if (callingActivityName.equals("MainActivity")) {

            wordDetails = MainActivity.wordDetails;
            resultsList = wordDetails.getResultsList();
            mAdapter = MainActivity.mAdapter;
//            intent.putExtra("wantGetWordDetailDetails", false);
        }

        resultsListView.setAdapter(mAdapter);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("position", position);
                intent.putExtra("userInput", userInput);
                startActivity(intent);
            }
        });

    }
}
