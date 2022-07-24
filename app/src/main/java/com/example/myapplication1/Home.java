package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class Home extends AppCompatActivity {


    public void prediction(View view){

    }

    public void takePicture(View view) {
        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA) !=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Home.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null){
                Bitmap image_capture = (Bitmap) data.getExtras().get("data");
            }
        }
        if (requestCode == 3){
            if (data != null) {
                Uri imageSelect = data.getData();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void importPicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    public void disconnected(View view) {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
        finish();
    }
}