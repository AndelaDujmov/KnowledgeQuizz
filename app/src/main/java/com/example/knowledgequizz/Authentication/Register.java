package com.example.knowledgequizz.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.knowledgequizz.Content.MainMenu;
import com.example.knowledgequizz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText username,email, password, repeatPassword;
    FirebaseAuth auth;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(Register.this);

        auth = FirebaseAuth.getInstance();

        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        repeatPassword = findViewById(R.id.repeatedPassword);


        register = findViewById(R.id.button4);


       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (password.getText().toString().equals(repeatPassword.getText().toString())) {
                   registerUser();
               }else{
                   Toast.makeText(Register.this, "Passwords dont match",Toast.LENGTH_LONG).show();
               }

           }
       });

    }

    private void registerUser(){
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(Register.this, MainMenu.class);
                            startActivity(intent);
                            Toast.makeText(Register.this, "Successfully registered " + user.getEmail().toString(),Toast.LENGTH_LONG).show();}
                        else{
                            Toast.makeText(Register.this, "Unable to create account",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
