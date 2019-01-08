package com.humblecarrotstudios.android.drwords;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.humblecarrotstudios.android.drwords.data.WordDetails;
import com.humblecarrotstudios.android.drwords.data.WordResult;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<String> {

    final static String BASE_URL = "https://wordsapiv1.p.mashape.com/words";
    public static com.humblecarrotstudios.android.drwords.WordsAdapter mAdapter;
    public static List<WordResult> resultsList;
    public static WordDetails wordDetails;
    public static String userInput;
    Intent intent;
    public static Gson gson;
    public static String myJsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText wordEditText = (EditText) findViewById(R.id.word_field);
        final Button resultsButton = (Button) findViewById(R.id.word_button);
        resultsButton.setBackgroundColor(Color.parseColor("#64DD17"));

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultsList = new ArrayList<>();
                userInput = wordEditText.getText().toString().trim();

                if (!isConnected()) {
                    Toast.makeText(MainActivity.this, "No internet connection. Please connect " +
                            "to the internet and try again.", Toast.LENGTH_SHORT).show();
                } else if (userInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a word" +
                            ".", Toast.LENGTH_SHORT).show();
                } else {
                    getLoaderManager().initLoader(0, null, MainActivity.this);
                }
            }
        });

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
//        System.out.println("running onCreateLoader()...");
        return new com.humblecarrotstudios.android.drwords.WordResultLoader(this, BASE_URL + "/" + userInput);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String jsonResponse) {

        myJsonResponse = jsonResponse;
        gson = new GsonBuilder().create();
        wordDetails = gson.fromJson(jsonResponse, WordDetails.class);

        resultsList = wordDetails.getResultsList();
        mAdapter = new WordsAdapter(MainActivity.this, resultsList);

        if (resultsList != null && !resultsList.isEmpty()) {
            mAdapter.clear();
            mAdapter.addAll(resultsList);
            getLoaderManager().destroyLoader(0);

            intent = new Intent(MainActivity.this, com.humblecarrotstudios.android.drwords.ResultsActivity.class);
            intent.putExtra("callingActivity", "MainActivity");
            intent.putExtra("userInput", userInput);
//            startActivityForResult(intent, 2);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "No results for that word, please try again" +
                    ".", Toast.LENGTH_SHORT).show();
            getLoaderManager().destroyLoader(0);

        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        mAdapter.clear();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connectedYes = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return connectedYes;
    }




}
