package com.example.knowledgequizz.Content;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.knowledgequizz.Entities.Question;
import com.example.knowledgequizz.R;
import com.example.knowledgequizz.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    Button item1, item2, item3, item4, next;
    String choosen;
    TextView question;
    List<Question> questions = new ArrayList<>();
    int currentQuestion, correct, wrong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        question = findViewById(R.id.questionText);
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        item4 = findViewById(R.id.item4);
        next = findViewById(R.id.next);


        loadQuestions();
        Collections.shuffle(questions);
        setCurrentQuestion(currentQuestion);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosen = item1.getText().toString();
                item1.setBackgroundResource(R.drawable.button_picked);
                item2.setBackgroundResource(R.drawable.button_option);
                item3.setBackgroundResource(R.drawable.button_option);
                item4.setBackgroundResource(R.drawable.button_option);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosen = item2.getText().toString();
                item1.setBackgroundResource(R.drawable.button_option);
                item2.setBackgroundResource(R.drawable.button_picked);
                item3.setBackgroundResource(R.drawable.button_option);
                item4.setBackgroundResource(R.drawable.button_option);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosen = item3.getText().toString();
                item1.setBackgroundResource(R.drawable.button_option);
                item2.setBackgroundResource(R.drawable.button_option);
                item3.setBackgroundResource(R.drawable.button_picked);
                item4.setBackgroundResource(R.drawable.button_option);
            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosen = item4.getText().toString();
                item1.setBackgroundResource(R.drawable.button_option);
                item2.setBackgroundResource(R.drawable.button_option);
                item3.setBackgroundResource(R.drawable.button_option);
                item4.setBackgroundResource(R.drawable.button_picked);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        setCurrentQuestion(currentQuestion);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCurrentQuestion(currentQuestion);
    }

    private void setCurrentQuestion(int current){
        question.setText(questions.get(current).getQuestion());
        item1.setText(questions.get(current).getItem1());
        item2.setText(questions.get(current).getItem2());
        item3.setText(questions.get(current).getItem3());
        item4.setText(questions.get(current).getItem4());

    }

    private void loadQuestions(){
        try {
            InputStream inputStream = getAssets().open("easy.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonArray = jsonObj.getJSONArray("easy");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Question questionFromJson = new Question(jsonObject.getString("question"), jsonObject.getString("item1"), jsonObject.getString("item2"), jsonObject.getString("item3"), jsonObject.getString("item4"), jsonObject.getString("rightAnswer"));
                questions.add(questionFromJson);
                Log.e("Rez", questionFromJson.getQuestion());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public  void nextQuestion(){
        if (currentQuestion < questions.size() - 1){
            item1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentQuestion++;
                    setCurrentQuestion(currentQuestion);
                    resetColors();
                }
            }, 800);
        }else {
            Intent intent = new Intent(Game.this, ResultActivity.class);
            intent.putExtra("total", questions.size());
            intent.putExtra("correct", correct);
            startActivity(intent);
        }
    }

    private void checkAnswers() {
        if (choosen.equals(questions.get(currentQuestion).getRightAnswer()) && choosen.equals(questions.get(currentQuestion).getItem1())) {
            correct++;
            item1.setBackgroundResource(R.color.green);
            item1.setTextColor(ContextCompat.getColor(this, R.color.white));
            item2.setBackgroundResource(R.color.red);
            item2.setTextColor(ContextCompat.getColor(this, R.color.white));
            item3.setBackgroundResource(R.color.red);
            item3.setTextColor(ContextCompat.getColor(this, R.color.white));
            item4.setBackgroundResource(R.color.red);
            item4.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (choosen.equals(questions.get(currentQuestion).getRightAnswer()) && choosen.equals(questions.get(currentQuestion).getItem2())) {
            correct++;
            item2.setBackgroundResource(R.color.green);
            item2.setTextColor(ContextCompat.getColor(this, R.color.white));
            item1.setBackgroundResource(R.color.red);
            item1.setTextColor(ContextCompat.getColor(this, R.color.white));
            item3.setBackgroundResource(R.color.red);
            item3.setTextColor(ContextCompat.getColor(this, R.color.white));
            item4.setBackgroundResource(R.color.red);
            item4.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (choosen.equals(questions.get(currentQuestion).getRightAnswer()) && questions.get(currentQuestion).getItem3().equals(questions.get(currentQuestion).getRightAnswer())) {
            correct++;
            item3.setBackgroundResource(R.color.green);
            item3.setTextColor(ContextCompat.getColor(this, R.color.white));
            item1.setBackgroundResource(R.color.red);
            item1.setTextColor(ContextCompat.getColor(this, R.color.white));
            item2.setBackgroundResource(R.color.red);
            item2.setTextColor(ContextCompat.getColor(this, R.color.white));
            item4.setBackgroundResource(R.color.red);
            item4.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (choosen.equals(questions.get(currentQuestion).getRightAnswer()) && questions.get(currentQuestion).getItem2().equals(questions.get(currentQuestion).getRightAnswer())) {
            correct++;
            item4.setBackgroundResource(R.color.green);
            item4.setTextColor(ContextCompat.getColor(this, R.color.white));
            item1.setBackgroundResource(R.color.red);
            item1.setTextColor(ContextCompat.getColor(this, R.color.white));
            item3.setBackgroundResource(R.color.red);
            item3.setTextColor(ContextCompat.getColor(this, R.color.white));
            item2.setBackgroundResource(R.color.red);
            item2.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        nextQuestion();
    }

    public void resetColors(){
        item1.setBackgroundResource(R.drawable.button_option);
        item1.setTextColor(ContextCompat.getColor(this, R.color.black));
        item2.setBackgroundResource(R.drawable.button_option);
        item2.setTextColor(ContextCompat.getColor(this, R.color.black));
        item3.setBackgroundResource(R.drawable.button_option);
        item3.setTextColor(ContextCompat.getColor(this, R.color.black));
        item4.setBackgroundResource(R.drawable.button_option);
        item4.setTextColor(ContextCompat.getColor(this, R.color.black));
    }
}