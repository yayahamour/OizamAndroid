package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_dex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex);
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        ListView listview = findViewById(R.id.listview);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://oizam.herokuapp.com/OiseauxDex/List/" + ((Oizam) getApplication()).getId(),null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject liste = (JSONObject) response.getJSONArray("list").get(0);
                            JSONArray birds =  liste.getJSONArray("birds");
                            for (int i =0; i < birds.length(); i++){
                                Log.i("MyActivity", String.valueOf(birds.get(i)));
                                JSONObject bird = (JSONObject) birds.get(i);
                                ids.add(bird.getInt("id"));
                                images.add(bird.getString("image"));
                                names.add(bird.getString("french_name"));
                            }
                            ProgramAdapter programAdapter = new ProgramAdapter(getApplicationContext(), ids, images, names);
                            listview.setAdapter(programAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("MyActivity", error.toString());
                Intent home = new Intent(getApplicationContext(), Home.class);
                startActivity(home);
                finish();
            }
        }
                );
        requestQueue.add(request);
    }

    public void goToHome(View view) {
        Intent home = new Intent(getApplicationContext(), Home.class);
        startActivity(home);
        finish();
    }
}