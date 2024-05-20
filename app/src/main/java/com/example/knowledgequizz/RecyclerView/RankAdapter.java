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

public class RankAdapter extends RecyclerView.Adapter<RankViewHolder> {
    List<String> users = new ArrayList<>();

    public RankAdapter(List<String> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_rank_item, parent, false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        holder.name.setText(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
