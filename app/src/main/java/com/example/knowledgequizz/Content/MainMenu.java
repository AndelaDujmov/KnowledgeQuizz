package com.example.knowledgequizz.Content;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.knowledgequizz.MainActivity;
import com.example.knowledgequizz.R;
import com.example.knowledgequizz.Results;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {

    Button start, logout, results;
    Button rank;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        FirebaseApp.initializeApp(MainMenu.this);

        auth = FirebaseAuth.getInstance();

        start = findViewById(R.id.newGame);
        results = findViewById(R.id.resume);
        logout = findViewById(R.id.results);
        rank = findViewById(R.id.rank);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Game.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Results.class);
                startActivity(intent);
            }
        });

        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, RankOfPlayers.class);
                startActivity(intent);
            }
        });
    }
}