package com.humblecarrot.android.drwords.data;

import java.util.ArrayList;
import java.util.List;

public class WordDetails {

    private String word;
    private List<WordResult> results;

    public WordDetails() {
        this.results = new ArrayList<WordResult>();
    }

    public WordDetails(List<WordResult> results) {
        this.results = results;
    }

    public List<WordResult> getResultsList() {
        return results;
    }

    public void setResults(List<WordResult> results) {
        this.results = results;
    }

    public String getWord() {
        return word;
    }
}
