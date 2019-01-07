package com.humblecarrot.android.drwords;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.humblecarrot.android.drwords.data.SingleResultExpandableListDataPump;
import com.humblecarrot.android.drwords.data.WordDetails;
import com.humblecarrot.android.drwords.data.WordResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleResultActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<String> {

    final public static String WordBeingSearchedText = "Word Being Searched";
    final public static String DefinitionText = "Definition";
    final public static String PartOfSpeechText = "Part Of Speech";
    final public static String SynonymsText = "Synonyms";
    final public static String AntonymsText = "Antonyms";
    final public static String ExamplesText = "Examples";
    final public static String DerivativesText = "Derivatives";
    final public static String TypeOfText = "Type Of";
    final public static String HasTypesText = "Has Types";
    final public static String PartOfText = "Part Of";
    final public static String HasPartsText = "Has Parts";
    final public static String InstanceOfText = "Instance Of";
    final public static String HasInstancesText = "Has Instances";
    final public static String SimilarToText = "Similar To";
    final public static String AlsoText = "Also";
    final public static String EntailsText = "Entails";
    final public static String MemberOfText = "Member Of";
    final public static String HasMembersText = "Has Members";
    final public static String SubstanceOfText = "Substance Of";
    final public static String HasSubstancesText = "Has Substances";
    final public static String InCategoryText = "In Category";
    final public static String HasCategoriesText = "Has Categories";
    final public static String UsageOfText = "Usage Of";
    final public static String HasUsagesText = "Has Usages";
    final public static String InRegionText = "In Region";
    final public static String RegionOfText = "Region Of";
    final public static String PertainsToText = "Pertains To";

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListGroupNames;
    private Map<String, List<String>> expandableListChildDetails;
    public static WordDetails wordDetails;

    final static String BASE_URL = "https://wordsapiv1.p.mashape.com/words";
    public static WordsAdapter mAdapter;
    public static List<WordResult> resultsList;
    public static String userInput;
    private int wordPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_result_layout);

    }

    @Override
    protected void onStart() {
        super.onStart();

        userInput = getIntent().getStringExtra("userInput");
        setTitle(userInput);
        wordDetails = ResultsActivity.wordDetails;
        resultsList = wordDetails.getResultsList();

        TextView singleResultTitle = (TextView) findViewById(R.id.singleResultTitle);
        singleResultTitle.setText("Single Result Information");

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String parentText = expandableListGroupNames.get(groupPosition);

                if (
                        parentText == PartOfSpeechText ||
                                parentText == TypeOfText ||
                                parentText == SynonymsText ||
                                parentText == HasTypesText ||
                                parentText == DerivativesText ||
                                parentText == PartOfText ||
                                parentText == HasPartsText ||
                                parentText == InstanceOfText ||
                                parentText == HasInstancesText ||
                                parentText == SimilarToText ||
                                parentText == AlsoText ||
                                parentText == EntailsText ||
                                parentText == MemberOfText ||
                                parentText == HasMembersText ||
                                parentText == SubstanceOfText ||
                                parentText == HasSubstancesText ||
                                parentText == InCategoryText ||
                                parentText == HasCategoriesText ||
                                parentText == UsageOfText ||
                                parentText == HasUsagesText ||
                                parentText == InRegionText ||
                                parentText == RegionOfText ||
                                parentText == PertainsToText

                        ) {

                    String childText = expandableListChildDetails.get(
                            expandableListGroupNames.get(groupPosition)).get(
                            childPosition);

                    resultsList = new ArrayList<>();
                    userInput = childText;

                    if (!isConnected()) {
                        Toast.makeText(SingleResultActivity.this, "No internet connection. Please connect " +
                                "to the internet and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        getLoaderManager().initLoader(0, null, SingleResultActivity.this);
                    }


                }
                return false;
            }
        });

        updateViewAndAdapter();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new WordResultLoader(this, BASE_URL + "/" + userInput);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String jsonResponse) {

        Gson gson = new GsonBuilder().create();
        wordDetails = gson.fromJson(jsonResponse, WordDetails.class);

        resultsList = wordDetails.getResultsList();
        mAdapter = new WordsAdapter(SingleResultActivity.this, new ArrayList<WordResult>());
        mAdapter.clear();
        if (resultsList != null && !resultsList.isEmpty()) {
            mAdapter.addAll(resultsList);
            getLoaderManager().destroyLoader(0);

//            updateViewAndAdapter();

            Intent intent = new Intent(SingleResultActivity.this, ResultsActivity.class);
            intent.putExtra("callingActivity", "SingleResultActivity");
            intent.putExtra("userInput", userInput);
//            startActivityForResult(intent, 0);
            startActivity(intent);
//            this.finish();
        } else {
            Toast.makeText(SingleResultActivity.this, "No results for that word, please try again" +
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

    private void updateViewAndAdapter() {
        wordPosition = getIntent().getIntExtra("position", -1);
        expandableListChildDetails = SingleResultExpandableListDataPump.getData
                (wordDetails, wordPosition);
        expandableListGroupNames = new ArrayList<String>(expandableListChildDetails.keySet());
        expandableListAdapter = new SingleResultExpandableListAdapter(this, expandableListGroupNames, expandableListChildDetails);
        expandableListView.setAdapter(expandableListAdapter);
    }
}