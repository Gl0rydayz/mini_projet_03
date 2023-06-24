package com.example.mini_projet_03.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.R;

public class CustomPagerAdapter extends RecyclerView.Adapter<CustomPagerAdapter.SlideViewHolder> {

    private Context mContext;
    private String[] texts = {"Hi",
            "Our simple app allows you to manage your Quotes",
            "Enjoy...."};

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_pager_layout, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.tv_viewPagerText.setText(texts[position]);
    }

    @Override
    public int getItemCount() {
        return texts.length;
    }

    public static class SlideViewHolder extends RecyclerView.ViewHolder {
        TextView tv_viewPagerText;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_viewPagerText = itemView.findViewById(R.id.tv_viewPagerText);
        }
    }
}
