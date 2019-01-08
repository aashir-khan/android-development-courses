package com.humblecarrotstudios.android.drwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.humblecarrotstudios.android.drwords.data.WordDetails;
import com.humblecarrotstudios.android.drwords.data.WordResult;


import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    private WordsAdapter mAdapter = null;
    public static List<WordResult> resultsList;
    public static WordDetails wordDetails;
    public static String userInput;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_list);

        resultsList = new ArrayList<>();
        wordDetails = new WordDetails();

        userInput = getIntent().getStringExtra("userInput");


        String callingActivityName = getIntent().getStringExtra("callingActivity");
        String userInputExtra = getIntent().getStringExtra("userInput");
        userInput = userInputExtra;
        if (callingActivityName == null || callingActivityName.equals
                ("SingleResultActivity")) {
            wordDetails = SingleResultActivity.wordDetails;
            resultsList = wordDetails.getResultsList();
            mAdapter = SingleResultActivity.mAdapter;
//            intent.putExtra("wantGetWordDetailDetails", true);
        } else if (callingActivityName.equals("MainActivity")) {

            wordDetails = MainActivity.gson.fromJson(MainActivity.myJsonResponse, WordDetails.class);
            resultsList = wordDetails.getResultsList();
            mAdapter = new WordsAdapter(ResultsActivity.this, resultsList);
//            intent.putExtra("wantGetWordDetailDetails", false);
        }

        setTitle("Word Information Results");

        TextView resultsTitleView = (TextView) findViewById(R.id.results_title);
        resultsTitleView.setText("RESULTS FOR \"" + userInput + "\"");

        ListView resultsListView = (ListView) findViewById(R.id.results_list);


        resultsListView.setAdapter(mAdapter);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(ResultsActivity.this, SingleResultActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("userInput", userInput);
//                startActivityForResult(intent, 1);
                startActivity(intent);
            }
        });
    }


}
