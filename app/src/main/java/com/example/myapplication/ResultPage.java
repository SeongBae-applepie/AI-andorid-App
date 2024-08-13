package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.net.URI;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResultPage extends AppCompatActivity {

    private ImageView imageview_1,imageview_2,imageview_3;
    private ImageView imageView;
    private ProgressBar progressBar;
    private String name;
    private CompositeDisposable disposables = new CompositeDisposable();

    private Uri cameraImageUri;

    private LinearLayout result_view ,result_logding_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page_view);

        result_view = findViewById(R.id.result_view);
        result_logding_view = findViewById(R.id.result_logding_view);

        imageView = findViewById(R.id.imageview);


        imageview_1 = findViewById(R.id.imageview_1);
        imageview_2 = findViewById(R.id.imageview_2);
        imageview_3 = findViewById(R.id.imageview_3);

        progressBar = findViewById(R.id.progressBar);

        result_view.setVisibility(View.GONE);
        result_logding_view.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        ArrayList data = (ArrayList) intent.getSerializableExtra("name");
        name = data.get(0).toString();
        cameraImageUri = Uri.parse(data.get(1).toString());
        loadAndDisplayImage(cameraImageUri);



        switch (name) {
            case "고추":
                imageview_1.setImageResource(R.drawable.pepper_color);
                imageview_2.setImageResource(R.drawable.pepper_color);
                imageview_3.setImageResource(R.drawable.pepper_color);
                break;

            case "배추":
                imageview_1.setImageResource(R.drawable.kimchi_cabbage_color);
                imageview_2.setImageResource(R.drawable.kimchi_cabbage_color);
                imageview_3.setImageResource(R.drawable.kimchi_cabbage_color);

                break;

            case "양배추":
                imageview_1.setImageResource(R.drawable.cabbage_color);
                imageview_2.setImageResource(R.drawable.cabbage_color);
                imageview_3.setImageResource(R.drawable.cabbage_color);
                break;

            case "토마토":
                imageview_1.setImageResource(R.drawable.tomato_color);
                imageview_2.setImageResource(R.drawable.tomato_color);
                imageview_3.setImageResource(R.drawable.tomato_color);

                break;

            case "파":
                imageview_1.setImageResource(R.drawable.green_onion_color);
                imageview_2.setImageResource(R.drawable.green_onion_color);
                imageview_3.setImageResource(R.drawable.green_onion_color);

                break;

            default:
                break;
        }

        loadData();
    }

    private void loadData() {
        // ProgressBar를 보이도록 설정
        // 비동기 작업 수행
        disposables.add(
                Observable.fromCallable(this::fetchData)
                        .subscribeOn(Schedulers.io()) // 백그라운드 스레드에서 작업 실행
                        .observeOn(AndroidSchedulers.mainThread()) // UI 업데이트를 메인 스레드에서 수행
                        .subscribe(
                                result -> {
                                    result_view.setVisibility(View.VISIBLE);
                                    result_logding_view.setVisibility(View.GONE);
                                    // ProgressBar 숨김
                                    // 작업 성공시 UI 업데이트


                                },
                                throwable -> {
                                    // 오류 발생 시 UI 업데이트
                                    Log.d("appapp","Eeeeee");
                                    result_view.setVisibility(View.GONE);
                                    result_logding_view.setVisibility(View.VISIBLE);

                                }
                        )
        );




    }

    private String fetchData() throws InterruptedException {
        // 네트워크 요청이나 긴 작업 시뮬레이션
        Thread.sleep(3000); // 3초 지연

        return "Data loaded successfully";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // 모든 구독 해제
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