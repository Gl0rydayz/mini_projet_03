package com.example.mini_projet_03.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.R;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class RVQuotes_Adapter extends RecyclerView.Adapter<RVQuotes_Adapter.MyViewHolder> {
    ArrayList<Quote> quotes;

    public RVQuotes_Adapter(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemQuote, tv_itemAuthor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_itemQuote = itemView.findViewById(R.id.tv_itemQuote);
            tv_itemAuthor = itemView.findViewById(R.id.tv_itemAuthor);
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
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }
}
