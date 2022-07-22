package com.example.myapplication1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
        TextView email = (TextView) findViewById(R.id.login);
        TextView password = (TextView) findViewById(R.id.password);
        JSONObject body = new JSONObject();
        body.put("email", email.getText());
        body.put("password", password.getText());

       // RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "https://oizam.herokuapp.com/login/login", body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }},
                new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error){
                            System.out.println("Problem on connection");
                        }
                    }
                );



        Intent home = new Intent(getApplicationContext(), Home.class);
        startActivity(home);
        finish();
    }

    public void createProfile(View view) {
        Intent signup = new Intent(getApplicationContext(), SignUp.class);
        startActivity(signup);
        finish();
    }
}