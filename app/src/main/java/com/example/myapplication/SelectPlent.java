package com.example.myapplication;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SelectPlent extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView imageView;
    private Uri cameraImageUri, check_uri; // 카메라로 촬영한 이미지의 URI
    private ImageButton btnCapture ,btnSelectFromGallery;

    private Button btnStarAI;
    private TextView nameTextview;

    private LinearLayout select_view;

    private ArrayList result_intent;


    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_plent_view);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        nameTextview = findViewById(R.id.serch_textview);
        imageView = findViewById(R.id.photo_view);


        nameTextview.setText(name+" 사진을 \n업로드 해 주세요");
        btnCapture = findViewById(R.id.serch_camera);
        btnSelectFromGallery = findViewById(R.id.serch_gallery);

        btnStarAI = findViewById(R.id.start_ai_btn);
        select_view = findViewById(R.id.select_view);

        check_uri = null;

        result_intent = new ArrayList();


        btnStarAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_uri != null){
                    Intent intent = new Intent(getApplicationContext(), ResultPage.class);
                    result_intent.add(name);
                    result_intent.add(check_uri);
                    intent.putExtra("name",result_intent);
                    startActivity(intent);
                }else {
                    Toast.makeText(view.getContext(), "사진을 넣어주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCapture.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    // 사진을 저장할 파일 생성
                    File imageFile = createImageFile();
                    cameraImageUri = FileProvider.getUriForFile(this,
                            "com.example.myapplication.fileprovider", imageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (IOException e) {
                    Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelectFromGallery.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    if (cameraImageUri != null) {
                        // 카메라로 촬영한 이미지 URI를 사용하여 로드 및 표시
                        check_uri = cameraImageUri;
                        loadAndDisplayImage(cameraImageUri);
                    }
                    break;

                case REQUEST_IMAGE_PICK:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            check_uri = selectedImage;
                            // 갤러리에서 선택한 이미지 URI를 사용하여 로드 및 표시
                            loadAndDisplayImage(selectedImage);
                        }
                    }
                    break;
            }
        }
    }

    private File createImageFile() throws IOException {
        // 고유한 파일 이름을 생성하여 이미지를 저장
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private void loadAndDisplayImage(Uri imageUri) {


        // Glide를 사용하여 이미지 로드 및 크기 조절
        Glide.with(this)
                .load(imageUri)
                .apply(new RequestOptions()
                        .override(600, 600) // 원하는 크기로 조절
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()) // 가운데를 기준으로 잘라내기
                .into(imageView);
    }
 }