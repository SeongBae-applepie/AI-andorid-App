package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AndroidCameraExample";
    private static final int REQUEST_CAMERA_PERMISSION = 1; // Request code for camera permission.

    private ImageButton Select_pepper_btn, Select_green_onion_btn, Select_lettuce_btn ,Select_tomato_btn ,Select_kimchi_lettuce_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check and request necessary permissions.
        if (!hasRequiredPermissions()) {
            requestNecessaryPermissions();
        }

        Select_pepper_btn = findViewById(R.id.pepper_btn);
        Select_green_onion_btn = findViewById(R.id.green_onion_btn);
        Select_tomato_btn = findViewById(R.id.tomato_btn);
        Select_kimchi_lettuce_btn = findViewById(R.id.kimchi_lettuce_btn);
        Select_lettuce_btn = findViewById(R.id.lettuce_btn);

        Select_pepper_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectPlent.class);
                intent.putExtra("name","고추");
                startActivity(intent);
            }
        });

        Select_kimchi_lettuce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectPlent.class);
                intent.putExtra("name","배추");
                startActivity(intent);
            }
        });

        Select_lettuce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectPlent.class);
                intent.putExtra("name","양배추");
                startActivity(intent);
            }
        });

        Select_tomato_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectPlent.class);
                intent.putExtra("name","토마토");
                startActivity(intent);
            }
        });

        Select_green_onion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectPlent.class);
                intent.putExtra("name","파");
                startActivity(intent);
            }
        });

    }




    // Check if the app has required permissions.
    private boolean hasRequiredPermissions() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    // Request necessary permissions for the app.
    private void requestNecessaryPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, REQUEST_CAMERA_PERMISSION);
    }

    @Override
    // Handle the result of the permission request.
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            // Check if the permissions were granted.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mCamera.openCamera(); // Open the camera if permission is granted.
                Log.d(TAG, "Camera permission granted");
            } else {
                Toast.makeText(this, "Camera permission is required to use this app.", Toast.LENGTH_LONG).show();
                finish(); // Close the app if permission is denied.
            }
        }
    }
}