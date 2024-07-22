package com.hanynemr.g4dquizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlayerWinnerAdapter extends ArrayAdapter<Player> {

    public PlayerWinnerAdapter(Context context, List<Player> players) {
        super(context, 0, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Player player = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.player_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvScore = convertView.findViewById(R.id.score);


        // Populate the data into the template view using the data object
        tvName.setText(player.getName());
        tvScore.setText("");


        // Return the completed view to render on screen
        return convertView;
    }
}
