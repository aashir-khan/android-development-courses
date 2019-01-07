package com.humblecarrot.android.drwords;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.humblecarrot.android.drwords.data.SingleResultExpandableListDataPump;
import com.humblecarrot.android.drwords.data.WordDetails;
import com.humblecarrot.android.drwords.data.WordResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SingleResultActivity extends AppCompatActivity {

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
    private WordDetails wordDetails = MainActivity.wordDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_result_layout);

        int wordPosition = getIntent().getIntExtra("position",-1);


        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListChildDetails = SingleResultExpandableListDataPump.getData
                (wordDetails, wordPosition);
        expandableListGroupNames = new ArrayList<String>(expandableListChildDetails.keySet());
        expandableListAdapter = new SingleResultExpandableListAdapter(this, expandableListGroupNames, expandableListChildDetails);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String parentText = expandableListGroupNames.get(groupPosition);

                if (
                        parentText == PartOfSpeechText ||
                        parentText == TypeOfText ||
                        parentText == SynonymsText ||
                        parentText == HasTypesText ||
                        parentText == TypeOfText ||
                        parentText == DerivativesText
                        ) {

                    String childText = expandableListChildDetails.get(
                            expandableListGroupNames.get(groupPosition)).get(
                            childPosition);

                    Toast.makeText(
                            getApplicationContext(),
                            childText, Toast.LENGTH_SHORT).show();
                }





                return false;
            }
        });




    }
}
