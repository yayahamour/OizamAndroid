package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void createProfile(View view) {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
        finish();
    }

    public void backToLogin(View view){
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
        finish();

    }
}