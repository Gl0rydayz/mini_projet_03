package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mini_projet_03.adapters.RVQuotes_Adapter;
import com.example.mini_projet_03.db.QuotesDbOpenHelper;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {
    //region Declare Attribues
    RecyclerView rv_quotes;
    ArrayList<Quote> quotes;
    QuotesDbOpenHelper db;

    EditText et_addQuote, et_addAuthor;
    Button btn_add;
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
        RVQuotes_Adapter adapter = new RVQuotes_Adapter(quotes);

        et_addQuote = findViewById(R.id.et_addQuote);
        et_addAuthor = findViewById(R.id.et_addAuthor);
        btn_add = findViewById(R.id.btn_add);
        //endregion

        rv_quotes.setAdapter(adapter);
        rv_quotes.setLayoutManager(new LinearLayoutManager(this));

        et_addQuote.requestFocus();

        btn_add.setOnClickListener(v -> {
            String quoteContent = et_addQuote.getText().toString();
            String authorName = et_addAuthor.getText().toString();

            if(!quoteContent.equals("") && !authorName.equals("")) {
                db.add(new Quote(quoteContent, authorName));

                // Create a new Quote object with the added quote
                Quote newQuote = new Quote(quoteContent, authorName);

                // Add the new quote to the ArrayList
                quotes.add(newQuote);

                // Notify the adapter that a new item has been inserted
                adapter.notifyItemInserted(quotes.size() - 1);

                // Clear the input fields
                et_addQuote.setText("");
                et_addAuthor.setText("");

                et_addQuote.requestFocus();
            } else {
                Toast.makeText(this, "Insert the quote content and it's author's name !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}