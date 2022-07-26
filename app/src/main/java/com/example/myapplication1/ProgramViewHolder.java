package com.example.myapplication1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {

    ImageView itemImage;
    TextView birdName;
    ProgramViewHolder(View v)
    {
        itemImage = v.findViewById(R.id.imageliste);
        birdName = v.findViewById(R.id.title_image);
    }
}
