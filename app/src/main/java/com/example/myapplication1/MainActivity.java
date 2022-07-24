package com.example.myapplication1;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connexion(View view) throws Exception {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        TextView email = (TextView) findViewById(R.id.EmailAddress);
        TextView password = (TextView) findViewById(R.id.password);
        JSONObject body = new JSONObject();
        body.put("email", email.getText());
        body.put("hashed_password", password.getText());
        Log.i("MyActivity", "body :" + body);

       // RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "https://oizam.herokuapp.com/login/login", body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ((Oizam) getApplication()).setId(response.getInt("user_connected"));

                            Intent home = new Intent(getApplicationContext(), Home.class);
                            startActivity(home);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("MyActivity", "response :" + response.toString());
                    }},
                new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error){
                            TextView connectionString = (TextView) findViewById(R.id.conectionString);
                            connectionString.setVisibility(view.VISIBLE);
                            Log.e("MyActivity", "response :" + error.toString());
                        }
                    }
                );
        requestQueue.add(request);
    }

    public void createProfile(View view) {
        Intent signup = new Intent(getApplicationContext(), SignUp.class);
        startActivity(signup);
        finish();
    }
}