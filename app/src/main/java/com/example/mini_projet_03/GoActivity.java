package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}