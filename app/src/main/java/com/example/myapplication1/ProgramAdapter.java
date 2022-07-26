package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProgramAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<Integer> ids = new ArrayList<Integer>();

    public ProgramAdapter(Context context , ArrayList<Integer> ids, ArrayList<String> images, ArrayList<String> names) {
        super(context , R.layout.item, R.id.title_image, names);
        this.context = context;
        this.names = names;
        this.images = images;
        this.ids = ids;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleItem = convertView;
        ProgramViewHolder holdre = null;
        if (singleItem == null ){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.item, parent, false);
            holdre = new ProgramViewHolder(singleItem);
            singleItem.setTag(holdre);
        }
        else{
            holdre = (ProgramViewHolder) singleItem.getTag();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ProgramViewHolder finalHoldre = holdre;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://oizam.herokuapp.com/bird/" + ids.get(position),null ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Glide.with(context).load(response.getString("image")).into(finalHoldre.itemImage);
                            Log.i("MyActivity", "response :" +response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("MyActivity", "response :" + error.toString());
                        Log.e("MyActivity", "url :" + "https://oizam.herokuapp.com/bird/" + ids.get(position));
                        Log.e("MyActivity", "id :" + ids.get(position));
                    }
                }
        );
        requestQueue.add(request);
        holdre.birdName.setText(names.get(position));
        Log.i("MyActivity", "lal");
        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Oizam) context).setBirdId(ids.get(position));
                Intent bird = new Intent(context, BirdActivity.class);
                bird.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(bird);
                }
            });

        return singleItem;
    }
}
