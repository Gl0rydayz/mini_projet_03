package com.example.mini_projet_03.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.R;
import com.example.mini_projet_03.UpdateQuoteDialogFragment;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class RVQuotes_Adapter extends RecyclerView.Adapter<RVQuotes_Adapter.MyViewHolder> {
    ArrayList<Quote> quotes;
    FragmentManager fragmentManager;

    public RVQuotes_Adapter(ArrayList<Quote> quotes, FragmentManager fragmentManager) {
        this.quotes = quotes;
        this.fragmentManager = fragmentManager;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemQuote, tv_itemAuthor;
        Button btn_itemUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_itemQuote = itemView.findViewById(R.id.tv_itemQuote);
            tv_itemAuthor = itemView.findViewById(R.id.tv_itemAuthor);
            btn_itemUpdate = itemView.findViewById(R.id.btn_itemUpdate);
        }
    }

    @NonNull
    @Override
    public RVQuotes_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVQuotes_Adapter.MyViewHolder holder, int position) {
        holder.tv_itemQuote.setText(quotes.get(position).getQuote());
        holder.tv_itemAuthor.setText(quotes.get(position).getAuthor());

        holder.btn_itemUpdate.setOnClickListener(v -> {
            UpdateQuoteDialogFragment updateQuoteDialogFragment = new UpdateQuoteDialogFragment(quotes.get(position));
            updateQuoteDialogFragment.show(fragmentManager, null);
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }
}
