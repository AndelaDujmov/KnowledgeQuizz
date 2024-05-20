package com.example.knowledgequizz.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledgequizz.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView datetime, percentage, score;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        this.datetime = itemView.findViewById(R.id.datetime);
        this.percentage = itemView.findViewById(R.id.percentage);
        this.score = itemView.findViewById(R.id.score);
    }
}
