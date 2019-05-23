package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.BookSecTionContentEntry;

import java.util.ArrayList;

import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;

public class BookDetailRecyclerViewAdapter extends RecyclerView.Adapter<BookDetailRecyclerViewAdapter.BookDetailRecyclerViewHolder> {

    private ArrayList<BookSecTionContentEntry> dataSecond;
    private Context context;
    private LayoutInflater inflater;


    public BookDetailRecyclerViewAdapter(ArrayList<BookSecTionContentEntry> dataSecond, Context context) {
        this.dataSecond = dataSecond;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookDetailRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.book_detail_recycler_view_adapter , null);
        return new BookDetailRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDetailRecyclerViewHolder bookDetailRecyclerViewHolder, int i) {
        if (bookDetailRecyclerViewHolder != null){
            HtmlText.from(dataSecond.get(i).getContent())
                    .setImageLoader(new HtmlImageLoader() {
                        @Override
                        public void loadImage(String s, final Callback callback) {
                            // Glide sample, you can also use other image loader
                            Glide.with(context)
                                    .load(s)
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            BitmapDrawable bit = (BitmapDrawable) resource;
                                            callback.onLoadComplete(bit.getBitmap());
                                        }
                                    });
                        }

                        @Override
                        public Drawable getDefaultDrawable() {
                            return ContextCompat.getDrawable(context, R.drawable.bg);
                        }

                        @Override
                        public Drawable getErrorDrawable() {
                            return ContextCompat.getDrawable(context, R.drawable.bg);
                        }

                        @Override
                        public int getMaxWidth() {
                            return bookDetailRecyclerViewHolder.textView.getMaxWidth();
                        }

                        @Override
                        public boolean fitWidth() {
                            return false;
                        }
                    })
                    .into(bookDetailRecyclerViewHolder.textView);
            bookDetailRecyclerViewHolder.tital.setText(dataSecond.get(i).getTitle() + "");
//            bookDetailRecyclerViewHolder.scrollView.fullScroll(View.FOCUS_UP);
        }

    }


    @Override
    public int getItemCount() {
        return dataSecond.size();
    }

    public class BookDetailRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView tital;
        private ScrollView scrollView;
        public BookDetailRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.book_detail_recycler_view_adt_content);
            tital = itemView.findViewById(R.id.book_detail_recycler_view_adt_tital);
            scrollView = itemView.findViewById(R.id.book_detail_scrollview);
        }
    }
}
