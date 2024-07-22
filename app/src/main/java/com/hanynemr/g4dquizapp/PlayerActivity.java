package com.hanynemr.g4dquizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements TextWatcher {
    EditText nameText;
    Button playButton;
    TextView winner_tv,winner_label;
    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_WINNER_PLAYERS = "winner_players";
    ArrayList<Player> players=new ArrayList<>();
    Player p=new Player();
    PlayerWinnerAdapter adapter;
    ListView listView;
    byte score;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    PreferencesHelper preferencesHelper;
    SharedPreferences.Editor editor;
    List<Player> winnerPlayers=new ArrayList<>();
    List<Player> prefList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);
        nameText=findViewById(R.id.nameText);
        playButton=findViewById(R.id.playButton);
        listView = findViewById(R.id.lv_winners);
        winner_label=findViewById(R.id.tv_winner_label);
        nameText.addTextChangedListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      //  preferencesHelper=new PreferencesHelper(this);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();


    }

    @Override
    protected void onStart() {
        super.onStart();

        winnerPlayers=getWinnerPlayers();
        Log.d("benz","winnerPlayers"+winnerPlayers);
        if(winnerPlayers.isEmpty()||winnerPlayers==null){
         //   winner_tv.setText("");
            winner_label.setText("");
        }else{
            // Create the adapter

            adapter = new PlayerWinnerAdapter(this, winnerPlayers);

            // Find the ListView and set the adapter

            listView.setAdapter(adapter);


        }
    }

    public void play(View view) {

        p.setName(nameText.getText().toString().toLowerCase());

        if (players.contains(p)) {
            int index=players.indexOf(p);
            Toast.makeText(this, "player played with score "+players.get(index).getScore(), Toast.LENGTH_SHORT).show();
            return;
        }



        Intent a=new Intent(this, GameActivity.class);
        a.putExtra("name",nameText.getText().toString());
//        startActivity(a);
        startActivityForResult(a,1000);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (data!=null){

                score = data.getByteExtra("score", (byte) -1);
                Toast.makeText(this, "return score "+score, Toast.LENGTH_SHORT).show();
                players.add(new Player(nameText.getText().toString().toLowerCase(),score));
                if(score==4) {
                    winnerPlayers.add(new Player(nameText.getText().toString().toLowerCase(), score));
                    saveWinnerPlayers(winnerPlayers);
                }


                nameText.setText("");
            }
        }
    } public void saveWinnerPlayers(List<Player> winnerPlayers) {

        editor = sharedPreferences.edit();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    protected void onDestroy() {
        saveWinnerPlayers(winnerPlayers);
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length()==0)
            playButton.setEnabled(false);
        else
            playButton.setEnabled(true);
    }

    public void stats(View view) {
        Intent a=new Intent(this, StatActivity.class);
//        a.putExtra("names",names);
//        a.putExtra("scores",scores);
        a.putExtra("players",players);
        startActivity(a);

    }
}