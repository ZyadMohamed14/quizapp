package com.hanynemr.g4dquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatActivity extends AppCompatActivity {
    TextView statText;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Player> players = (ArrayList<Player>)getIntent().getSerializableExtra("players");

      Collections.sort(players, new Comparator<Player>() {
          @Override
          public int compare(Player o1, Player o2) {
              return Byte.compare(o1.getScore(), o2.getScore());
          }
      });
        PlayerAdapter adapter = new PlayerAdapter(this,players);
        recyclerView.setAdapter(adapter);

    }
}