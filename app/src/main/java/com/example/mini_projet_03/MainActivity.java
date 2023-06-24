package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.mini_projet_03.adapters.CustomPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 vp_welcome;
    Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp_welcome = findViewById(R.id.vp_welcome);
        btn_skip = findViewById(R.id.btn_skip);

        SharedPreferences sharedPreferences = getSharedPreferences("tutorial", MODE_PRIVATE);
        boolean isTutorialCompleted = sharedPreferences.getBoolean("isCompleted", false);

        if (isTutorialCompleted) {
            startActivity(new Intent(this, GoActivity.class));
        } else {
            CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this);
            vp_welcome.setAdapter(customPagerAdapter);

            btn_skip.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isCompleted", true);
                editor.apply();

                startActivity(new Intent(this, GoActivity.class));
            });
        }
    }
}