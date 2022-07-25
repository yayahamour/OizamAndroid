package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BirdActivity extends AppCompatActivity {
    Context context;
    ImageView imageBird;
    TextView nameBird;
    TextView ordre;
    TextView family;
    TextView genre;
    TextView espece;
    TextView taille;
    TextView poids;
    TextView description;
    TextView envergure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);

        context = this;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://oizam.herokuapp.com/bird/" + ((Oizam) getApplication()).getBirdId(),null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        imageBird = (ImageView) findViewById(R.id.imageBird);
                        nameBird = (TextView) findViewById(R.id.nameBird);
                        ordre = (TextView) findViewById(R.id.ordre);
                        family = (TextView) findViewById(R.id.family);
                        genre = (TextView) findViewById(R.id.genre);
                        espece = (TextView) findViewById(R.id.espece);
                        taille = (TextView) findViewById(R.id.taille);
                        poids = (TextView) findViewById(R.id.poids);
                        description = (TextView) findViewById(R.id.description);
                        envergure = (TextView) findViewById(R.id.envergure);
                        try {
                            Glide.with(context).load(response.getString("image")).into(imageBird);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            nameBird.setText(response.getString("french_name"));
                        } catch (JSONException e) {
                            nameBird.setText("No Name");
                        }
                        try {
                            ordre.setText("Ordre : ".concat(response.getString("ordre")));
                        } catch (JSONException e) {
                            ordre.setText("Ordre : -");
                        }
                        try {
                            family.setText("Famille : ".concat(response.getString("famille")));
                        } catch (JSONException e) {
                            family.setText("Famille : -");
                        }
                        try {
                            genre.setText("Genre : ".concat(response.getString("genre")));
                        } catch (JSONException e) {
                            genre.setText("Genre : -");
                        }
                        try {
                            espece.setText("Espece : ".concat(response.getString("espece")));
                        } catch (JSONException e) {
                            espece.setText("Espece : -");
                        }
                        try {
                            taille.setText("Taille : ".concat(response.getString("taille")));
                        } catch (JSONException e) {
                            taille.setText("Taille : -");
                        }
                        try {
                            poids.setText("Poids : ".concat(response.getString("poids")));
                        } catch (JSONException e) {
                            poids.setText("Poids : -");
                        }try {
                            description.setText(response.getString("text"));
                        } catch (JSONException e) {
                            description.setText("-");
                        }try {
                            envergure.setText("Envergure : ".concat(response.getString("envergure")));
                        } catch (JSONException e) {
                            envergure.setText("Envergure : -");
                        }
                        Log.i("MyActivity", "response :" + response.toString());
                    }},
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("MyActivity", "response :" + error.toString());
                        Log.e("MyActivity", "url :" + "https://oizam.herokuapp.com/bird/" + ((Oizam) getApplication()).getBirdId());
                        Log.e("MyActivity", "id :" + ((Oizam) getApplication()).getBirdId());
                    }
                }
        );
        requestQueue.add(request);
    }

    public void goToDex(View view) {
        Intent dexActivity = new Intent(getApplicationContext(), activity_dex.class);
        startActivity(dexActivity);
        finish();
    }

    public void goToHome(View view) {
        Intent home = new Intent(getApplicationContext(), Home.class);
        startActivity(home);
        finish();
    }
}