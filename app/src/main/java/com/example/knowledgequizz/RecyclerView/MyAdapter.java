package com.example.knowledgequizz.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledgequizz.Entities.Game;
import com.example.knowledgequizz.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Game> games = new ArrayList<>();

    public MyAdapter(List<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int curPoints = games.get(position).getAchievedPoints();
        int total = games.get(position).getTotalPoints();
        String date = games.get(position).getPlayedAt();
        holder.score.setText(String.valueOf(curPoints));
        int percentage = (int)((double) curPoints/total * 100) ;
        holder.percentage.setText(percentage + " %");
        holder.score.setText(String.valueOf(curPoints));
        holder.datetime.setText(date);
    }

    @Override
    public int getItemCount() {
           return games.size();
    }
}
