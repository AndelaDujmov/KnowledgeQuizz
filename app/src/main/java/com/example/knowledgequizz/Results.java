package com.example.knowledgequizz;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.knowledgequizz.Entities.Game;
import com.example.knowledgequizz.RecyclerView.MyAdapter;
import com.example.knowledgequizz.databinding.ActivityResultsBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityResultsBinding binding;
    public RecyclerView MyRecyclerView;
    public List<Game> gameList = new ArrayList<>();
    public MyAdapter Adapter;
    public FirebaseAuth auth;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(Results.this);


        binding = ActivityResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarResults.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_results);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        MyRecyclerView = findViewById(R.id.gamesCycler);

        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_results);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loadData(){
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        db.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    ArrayList<Game> gameList = new ArrayList<>();

                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Game game = doc.toObject(Game.class);
                        if (game.getCurrentUser().equals(user.getEmail())){
                            gameList.add(game);
                        }

                    }

                    Adapter = new MyAdapter(gameList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Results.this, LinearLayoutManager.VERTICAL, false);
                    MyRecyclerView.setLayoutManager(layoutManager);
                    MyRecyclerView.setAdapter(Adapter);
                    MyRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), layoutManager.getOrientation()));
                }
            }
        });

          }
}