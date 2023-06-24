package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GoActivity extends AppCompatActivity {
    //region Declare Attributes
    Button btn_go;
    ImageView iv_randomImage;
    ArrayList<String> imagesLinks;
    NetworkChangeReceiver networkChangeReceiver;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        //region Initialize Attributes
        btn_go = findViewById(R.id.btn_go);
        iv_randomImage = findViewById(R.id.iv_randomImage);
        imagesLinks = new ArrayList<>(Arrays.asList("https://iili.io/H4a4t7S.png",
                "https://iili.io/H4a4Dk7.png", "https://iili.io/H4a4Z22.png",
                "https://iili.io/H4a4bp9.png", "https://iili.io/H4a4pIe.png"));
        networkChangeReceiver = new NetworkChangeReceiver();
        //endregion


        if (isConnectedToInternet()) {
            loadRandomImage();
        } else {
            iv_randomImage.setImageResource(R.drawable.image_not_available);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerNetworkChangeReceiver();

        if (isConnectedToInternet()) {
            loadRandomImage();
        } else {
            iv_randomImage.setImageResource(R.drawable.image_not_available);
        }
    }

    //region Handle The Connection to the internet using BroadcastReceiver
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isConnectedToInternet()) {
                loadRandomImage();
            } else {
                iv_randomImage.setImageResource(R.drawable.image_not_available);
            }
        }
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }
    //endregion

    //region Load a random image from the imagesLinks
    private void loadRandomImage() {
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