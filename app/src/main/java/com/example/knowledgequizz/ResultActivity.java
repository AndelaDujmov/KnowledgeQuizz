package com.example.knowledgequizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledgequizz.Content.MainMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    TextView correct, wrong, total;
    FloatingActionButton yes, no;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        total = findViewById(R.id.totalScoreCount);
        correct = findViewById(R.id.totalScoreCorrect);
        wrong = findViewById(R.id.totalScoreWrong);
        yes = findViewById(R.id.accept);
        no = findViewById(R.id.decline);

        int totalC = getIntent().getIntExtra("total", 0);
        int correctC = getIntent().getIntExtra("correct", 0);
        int wrongC = totalC - correctC;

        total.setText(String.valueOf(totalC));
        correct.setText(String.valueOf(correctC));
        wrong.setText(String.valueOf(wrongC));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(totalC, correctC);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainMenu();
            }
        });
    }

    public void save(int total, int correct){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("totalPoints", total*5);
        questionMap.put("achievedPoints", correct*5);
        questionMap.put("playedAt", currentDate.format(formatter));
        FirebaseUser user = auth.getCurrentUser();
        questionMap.put("currentUser", user.getEmail());
        db.collection("games").add(questionMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        returnToMainMenu();
                        Toast.makeText(ResultActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        returnToMainMenu();
                        Toast.makeText(ResultActivity.this, "Unsuccessfully saved because " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void returnToMainMenu(){
        Intent intent = new Intent(ResultActivity.this, MainMenu.class);
        startActivity(intent);
    }
}