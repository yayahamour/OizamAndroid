package com.example.myapplication1;

import android.app.Application;
import android.util.Log;

public class Oizam extends Application {

    private int id;
    private int birdId;
    public int getId() {
        Log.e("MyActivity", "id :" + id);
        return id;
    }

    public void setId(int id) {
        Log.e("MyActivity", "id :" + id);
        this.id = id;
    }

    public int getBirdId(){
        return birdId;
    }

    public void setBirdId(int birdId){
        this.birdId = birdId;
    }
}