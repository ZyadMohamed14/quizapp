package com.hanynemr.g4dquizapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesHelper {

    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_WINNER_PLAYERS = "winner_players";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveWinnerPlayers(List<Player> winnerPlayers) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Convert list to JSON
        String json = gson.toJson(winnerPlayers);
        editor.putString(KEY_WINNER_PLAYERS, json);
        editor.apply(); // Save data asynchronously
    }

    public List<Player> getWinnerPlayers() {
        // Retrieve JSON string
        String json = sharedPreferences.getString(KEY_WINNER_PLAYERS, null);
        if (json == null) {
            return new ArrayList<>(); // Return empty list if no data found
        }
        // Convert JSON to list of Player objects
        Type type = new TypeToken<List<Player>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
