package com.example.knowledgequizz.Content;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.knowledgequizz.Entities.Game;
import com.example.knowledgequizz.R;
import com.example.knowledgequizz.RecyclerView.MyAdapter;
import com.example.knowledgequizz.RecyclerView.RankAdapter;
import com.example.knowledgequizz.Results;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankOfPlayers extends AppCompatActivity {

    FirebaseFirestore db;
    RankAdapter MyAdapter;
    RecyclerView recyclerView;
    HashMap<String, Integer> gamersValues = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_of_players);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recycler);

        getData();

    }

    private void getData(){
        db.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    ArrayList<String> users = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Game game = doc.toObject(Game.class);
                        if (game.getAchievedPoints() == game.getTotalPoints()){
                            if (!users.contains(game.getCurrentUser())) {
                                users.add(game.getCurrentUser());
                                gamersValues.put(game.getCurrentUser(), 1);
                            }
                            else{
                                gamersValues.put(game.getCurrentUser(), gamersValues.get(game.getCurrentUser()) + 1);
                            }
                        }

                    }

                    Collections.sort(users, (user1, user2) -> gamersValues.get(user2) - gamersValues.get(user1));


                    MyAdapter = new RankAdapter(users);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(RankOfPlayers.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(MyAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), layoutManager.getOrientation()));
                }
            }
        });
    }
}