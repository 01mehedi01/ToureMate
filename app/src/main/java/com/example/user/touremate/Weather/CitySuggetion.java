package com.example.user.touremate.Weather;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by User on 1/6/2018.
 */

public class CitySuggetion extends SearchRecentSuggestionsProvider {
    public  static final String AUTHORITY = "com.example.user.touremate.Weather.CitySuggetion";
    public   static final int MODE = DATABASE_MODE_QUERIES;

    public CitySuggetion() {
        setupSuggestions(AUTHORITY,MODE);
    }
}
