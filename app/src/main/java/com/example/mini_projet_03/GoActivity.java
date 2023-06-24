package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GoActivity extends AppCompatActivity {
    Button btn_go;
    ImageView iv_randomImage;
    ArrayList<String> imagesLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        btn_go = findViewById(R.id.btn_go);
        iv_randomImage = findViewById(R.id.iv_randomImage);
        imagesLinks = new ArrayList<>(Arrays.asList("https://iili.io/H4a4t7S.png",
                "https://iili.io/H4a4Dk7.png", "https://iili.io/H4a4Z22.png",
                "https://iili.io/H4a4bp9.png", "https://iili.io/H4a4pIe.png"));

        loadRandomImane();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRandomImane();
    }

    //region Load a random image from the imagesLinks
    private void loadRandomImane() {
        int random = new Random().nextInt(imagesLinks.size());

        Glide.with(this)
                .load(imagesLinks.get(random))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv_randomImage);
    }
    //endregion

    //region Move task to back to avoid going back to the tutorial activity (MainActivity)
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    //endregion
}