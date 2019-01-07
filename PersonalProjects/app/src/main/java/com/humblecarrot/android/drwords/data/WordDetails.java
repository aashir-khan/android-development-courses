package com.humblecarrot.android.drwords.data;

import java.util.ArrayList;
import java.util.List;

public class WordDetails {

    private String word;
    private List<WordResult> results;

    public WordDetails() {
        this.results = new ArrayList<WordResult>();
    }

    public List<WordResult> getResultsList() {
        return results;
    }

    public String getWord() {
        return word;
    }
}
