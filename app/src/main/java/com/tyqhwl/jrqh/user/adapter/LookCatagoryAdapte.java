package com.tyqhwl.jrqh.user.adapter;

import android.app.Activity;
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
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.BookDetailActivity;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;


public class LookCatagoryAdapte extends RecyclerView.Adapter<LookCatagoryAdapte.LookCatagoryHolder> {

    private ArrayList<BookEntry> data;
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;

    public LookCatagoryAdapte(ArrayList<BookEntry> data, Context context, Activity activity) {
        this.data = data;
        this.context = context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LookCatagoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.look_catagory_adapter  , null);
        return new LookCatagoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LookCatagoryHolder lookCatagoryHolder, int i) {
        if (lookCatagoryHolder != null){
            Glide.with(context)
                    .load(data.get(i).getImages())
                    .into(lookCatagoryHolder.image);
            lookCatagoryHolder.text.setText(data.get(i).getTitle() + "");

            lookCatagoryHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到书籍详情页面
                    IntentSkip.startIntent(activity , new BookDetailActivity() , data.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LookCatagoryHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text;
        public LookCatagoryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.look_catagory_adt_image);
            text = itemView.findViewById(R.id.look_catagory_adt_tital);
        }
    }
}
