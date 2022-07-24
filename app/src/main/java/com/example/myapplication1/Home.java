package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import com.example.myapplication1.ml.ModelFr;
import com.example.myapplication1.ml.ModelUs;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Home extends AppCompatActivity {
    int imageSize = 299;


    public void prediction(Bitmap image_capture){
        RadioButton rb;
        int maxPos = 0;
        try {
            rb = (RadioButton) findViewById(R.id.french_model);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new  int[imageSize * imageSize];
            image_capture.getPixels(intValues, 0, image_capture.getWidth(), 0,0, image_capture.getWidth(), image_capture.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val& 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            if (rb.isChecked()) {
                ModelFr model = ModelFr.newInstance(getApplicationContext());
                ModelFr.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                float [] confidences = outputFeature0.getFloatArray();
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence){
                        maxConfidence = confidences[i];
                        maxPos = i + 201;
                    }
                }
                model.close();
            }
            else{
                ModelUs model = ModelUs.newInstance(getApplicationContext());
                ModelUs.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                float [] confidences = outputFeature0.getFloatArray();
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence){
                        maxConfidence = confidences[i];
                        maxPos = i + 1;
                    }
                }
                model.close();
            }
            Log.i("MyActivity", "prediction :" + maxPos);

        } catch (IOException e) {
            // TODO Handle the exception
        }
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
                int dimension = Math.min(image_capture.getWidth(), image_capture.getHeight());
                image_capture = ThumbnailUtils.extractThumbnail(image_capture, dimension, dimension);
                image_capture = Bitmap.createScaledBitmap(image_capture, imageSize, imageSize, false);
                prediction(image_capture);
            }
        }
        if (requestCode == 3){
            if (data != null) {
                Uri imageSelect = data.getData();
                try {
                    Bitmap image_capture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageSelect);
                    int dimension = Math.min(image_capture.getWidth(), image_capture.getHeight());
                    image_capture = ThumbnailUtils.extractThumbnail(image_capture, dimension, dimension);
                    image_capture = Bitmap.createScaledBitmap(image_capture, imageSize, imageSize, false);
                    prediction(image_capture);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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