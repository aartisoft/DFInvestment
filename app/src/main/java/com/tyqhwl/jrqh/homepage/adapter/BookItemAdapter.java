package com.tyqhwl.jrqh.homepage.adapter;

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
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.BookDetailActivity;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.login.activity.LoginActivity;

import java.util.ArrayList;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.BookItemHolder> {

    private ArrayList<BookEntry> data;
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;


    public BookItemAdapter(ArrayList<BookEntry> data, Context context, Activity activity) {
        this.data = data;
        this.context = context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.book_item_adapter , null);
        return new BookItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemHolder bookItemHolder, int i) {
        if (bookItemHolder != null){
            Glide.with(context)
                    .load(data.get(i).getImages())
                    .apply(new RequestOptions().error(R.drawable.tu_wuzhuangtai).placeholder(R.drawable.tu_jiazai))
                    .into(bookItemHolder.image);
            bookItemHolder.tital.setText(data.get(i).getTitle() + "");

            bookItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationStatic.getUserLoginState()){
                        //跳转到书籍详情页面
                        IntentSkip.startIntent(activity , new BookDetailActivity() , data.get(i));
                    }else {
                        IntentSkip.startIntent(activity , new LoginActivity() , null);
                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookItemHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView tital;
        public BookItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.book_item_adt_image);
            tital = itemView.findViewById(R.id.book_item_adt_tital);
        }
    }
}
