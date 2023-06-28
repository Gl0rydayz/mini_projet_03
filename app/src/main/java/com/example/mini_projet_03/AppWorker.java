package com.example.mini_projet_03;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mini_projet_03.db.QuotesDbOpenHelper;
import com.example.mini_projet_03.models.Quote;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AppWorker extends Worker {

    public AppWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        if (getPercentage() < 20) {
            Toast.makeText(getApplicationContext(), "The percentage of the battery is too low to allow download !!!", Toast.LENGTH_SHORT).show();
            return Result.failure();
        }

        ArrayList<Quote> quotes = new QuotesDbOpenHelper(getApplicationContext()).getAll();

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MP3_Quotes.txt");
        try (FileWriter writer = new FileWriter(file);){

            for (int i = 0; i < quotes.size(); i++) {
                writer.write(quotes.get(i).getQuote() + " \n---> " + quotes.get(i).getAuthor() + "\n\n");
            }

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }

    }
    private int getPercentage() {
        BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

}
