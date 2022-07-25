package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_dex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);
    }

    public void goToHome(View view) {
        Intent home = new Intent(getApplicationContext(), Home.class);
        startActivity(home);
        finish();
    }
}