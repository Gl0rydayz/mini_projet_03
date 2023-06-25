package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mini_projet_03.adapters.RVQuotes_Adapter;
import com.example.mini_projet_03.db.QuotesDbOpenHelper;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {
    //region Declare Attribues
    RecyclerView rv_quotes;
    ArrayList<Quote> quotes;
    QuotesDbOpenHelper db;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        //region Initialize Attributes
        rv_quotes = findViewById(R.id.rv_quotes);
        db = new QuotesDbOpenHelper(this);
        quotes = new ArrayList<>(db.getAll());
        //endregion

        rv_quotes.setAdapter(new RVQuotes_Adapter(quotes));
        rv_quotes.setLayoutManager(new LinearLayoutManager(this));
    }
}