package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

public class SeekRecyclerViewAdapter extends RecyclerView.Adapter<SeekRecyclerViewAdapter.SeekRecyclerViewHolder> {

    private ArrayList<BookEntry> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnClickListenerAdapter onClickListenerAdapter;


    public interface OnClickListenerAdapter{
        void onClick(int index);
    }


    public SeekRecyclerViewAdapter(ArrayList<BookEntry> data, Context context, OnClickListenerAdapter onClickListenerAdapter) {
        this.data = data;
        this.context = context;
        this.onClickListenerAdapter = onClickListenerAdapter;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SeekRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.seek_recycler_view_adapter , null);
        return new SeekRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeekRecyclerViewHolder seekRecyclerViewHolder, int i) {
        if (seekRecyclerViewHolder != null){
            seekRecyclerViewHolder.tital.setText(data.get(i).getTitle());
            Glide.with(context)
                    .load(data.get(i).getImages())
                    .into(seekRecyclerViewHolder.image);

            seekRecyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListenerAdapter.onClick(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SeekRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView tital;
        private ImageView image;

        public SeekRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tital = itemView.findViewById(R.id.seek_recycler_view_tital);
            image = itemView.findViewById(R.id.seek_recycler_view_image);
        }
    }
}
