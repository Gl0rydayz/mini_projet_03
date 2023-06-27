package com.example.mini_projet_03.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.R;
import com.example.mini_projet_03.UpdateQuoteDialogFragment;
import com.example.mini_projet_03.models.Quote;

import java.util.ArrayList;

public class RVQuotes_Adapter extends RecyclerView.Adapter<RVQuotes_Adapter.MyViewHolder> {
    ArrayList<Quote> quotes;
    FragmentManager fragmentManager;
    private onItemSelectedListener listener;
    ArrayList<Integer> positions = new ArrayList<>();
    private int i = 0;

    public interface onItemSelectedListener {
        void onQuoteSelected(int value, ArrayList<Integer> positions);
    }

    public RVQuotes_Adapter(ArrayList<Quote> quotes, FragmentManager fragmentManager, onItemSelectedListener listener) {
        this.quotes = quotes;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemQuote, tv_itemAuthor;
        Button btn_itemUpdate;
        public CheckBox cb_itemSelected;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_itemQuote = itemView.findViewById(R.id.tv_itemQuote);
            tv_itemAuthor = itemView.findViewById(R.id.tv_itemAuthor);
            btn_itemUpdate = itemView.findViewById(R.id.btn_itemUpdate);
            cb_itemSelected = itemView.findViewById(R.id.cb_itemSelect);
        }
    }

    @NonNull
    @Override
    public RVQuotes_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item_layout, parent, false);
        itemView.setBackgroundColor(Color.parseColor("#FDF5E6"));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVQuotes_Adapter.MyViewHolder holder, int position) {
        holder.tv_itemQuote.setText(quotes.get(position).getQuote());
        holder.tv_itemAuthor.setText(quotes.get(position).getAuthor());

        //region Call the DialogFragment
        holder.btn_itemUpdate.setOnClickListener(v -> {
            UpdateQuoteDialogFragment updateQuoteDialogFragment = new UpdateQuoteDialogFragment(quotes.get(position));
            updateQuoteDialogFragment.show(fragmentManager, null);
        });
        //endregion

        //region Manage selecting quotes
        holder.cb_itemSelected.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                positions.add(position);
                i++;
            } else {
                positions.remove(Integer.valueOf(position));
                i--;
            }

            Toast.makeText(holder.itemView.getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
            listener.onQuoteSelected(i, positions);
        });
        //endregion
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }
}
