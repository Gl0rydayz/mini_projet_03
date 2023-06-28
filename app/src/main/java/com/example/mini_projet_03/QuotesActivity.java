package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mini_projet_03.adapters.RVQuotes_Adapter;
import com.example.mini_projet_03.db.QuotesDbOpenHelper;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity implements RVQuotes_Adapter.onItemSelectedListener {
    //region Declare Attribues
    RecyclerView rv_quotes;
    ArrayList<Quote> quotes;
    QuotesDbOpenHelper db;

    EditText et_addQuote, et_addAuthor;
    Button btn_add, btn_delete;
    ImageButton ib_download;
    //endregion

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        //region Initialize Attributes
        rv_quotes = findViewById(R.id.rv_quotes);
        db = new QuotesDbOpenHelper(this);
        quotes = new ArrayList<>(db.getAll());
        RVQuotes_Adapter adapter = new RVQuotes_Adapter(quotes, getSupportFragmentManager(), this);

        et_addQuote = findViewById(R.id.et_addQuote);
        et_addAuthor = findViewById(R.id.et_addAuthor);
        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
        ib_download = findViewById(R.id.ib_download);
        //endregion

        rv_quotes.setAdapter(adapter);
        rv_quotes.setLayoutManager(new LinearLayoutManager(this));

        et_addQuote.requestFocus();

        btn_add.setOnClickListener(v -> {
            String quoteContent = et_addQuote.getText().toString();
            String authorName = et_addAuthor.getText().toString();

            if(!quoteContent.equals("") && !authorName.equals("")) {
                db.add(new Quote(quoteContent, authorName));

                Quote newQuote = new Quote(quoteContent, authorName);
                quotes.add(newQuote);
                adapter.notifyItemInserted(quotes.size() - 1);

                // Clear the input fields
                et_addQuote.setText("");
                et_addAuthor.setText("");

                et_addQuote.requestFocus();
            } else {
                Toast.makeText(this, "Insert the quote content and it's author's name !!!", Toast.LENGTH_SHORT).show();
            }
        });

        //region Handle Download ImageButton
        ib_download.setOnClickListener(v -> {

            //region Constraints to check if the state of the battery
            Constraints batteryConstraints = new Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiresBatteryNotLow(true)
                    .build();
            //endregion

            Data inputData = new Data.Builder().build();

            OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest.Builder(AppWorker.class)
                    .setInputData(inputData)
                    .setConstraints(batteryConstraints)
                    .build();

            WorkManager.getInstance(this).enqueue(downloadRequest);
            Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
        });
        //endregion
    }

    //region Change the number of selected quotes Dynamically and delete them
    @Override
    public void onQuoteSelected(int value, ArrayList<Integer> positions) {
        btn_delete.setText(String.format("Delete(%d)", value));
        btn_delete.setOnClickListener(v -> {
            for (int i = 0; i < positions.size(); i++) {
                db.delete(quotes.get(i));
            }
        });
    }
    //endregion
}