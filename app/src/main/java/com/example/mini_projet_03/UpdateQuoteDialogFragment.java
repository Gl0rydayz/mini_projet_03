package com.example.mini_projet_03;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mini_projet_03.db.QuotesDbOpenHelper;
import com.example.mini_projet_03.models.Quote;

public class UpdateQuoteDialogFragment extends DialogFragment {
    QuotesDbOpenHelper db;
    Quote quote;

    public UpdateQuoteDialogFragment(Quote quote) {
        this.quote = quote;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_quote_dialog_fragment_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText et_addQuote = view.findViewById(R.id.et_addQuote);
        EditText et_addAuthor = view.findViewById(R.id.et_addAuthor);
        Button btn_add = view.findViewById(R.id.btn_add);
        db = new QuotesDbOpenHelper(getContext());

        btn_add.setText("Update");

        et_addQuote.setText(quote.getQuote());
        et_addAuthor.setText(quote.getAuthor());

        btn_add.setOnClickListener(v -> {
            String updatedQuoteContent = et_addQuote.getText().toString();
            String updatedAuthor = et_addAuthor.getText().toString();

            Quote updatedQuote = new Quote(updatedQuoteContent, updatedAuthor);

            db.update(quote, updatedQuote);

            dismiss();
        });
    }
}
