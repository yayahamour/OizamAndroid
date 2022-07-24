package com.example.myapplication1;

import android.app.Application;
import android.util.Log;

public class Oizam extends Application {

    private int id;

    public int getId() {
        Log.e("MyActivity", "id :" + id);
        return id;
    }

    public void setId(int id) {
        Log.e("MyActivity", "id :" + id);
        this.id = id;
    }
}