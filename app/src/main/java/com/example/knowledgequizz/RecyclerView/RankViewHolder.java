package com.example.knowledgequizz.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledgequizz.R;

public class RankViewHolder extends RecyclerView.ViewHolder {
    TextView name;

    public RankViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.username);
    }
}
