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
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.BookDetailActivity;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import java.util.ArrayList;

public class TopRecyclerAdapter extends RecyclerView.Adapter<TopRecyclerAdapter.TopRecyclerHolder> {

    private ArrayList<BookEntry> arrayList;
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;

    public TopRecyclerAdapter(ArrayList<BookEntry> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TopRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.top_recycler_adapter , null);
        return new TopRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerHolder topRecyclerHolder, int i) {
        if (topRecyclerHolder != null){
            Glide.with(context)
                    .load(arrayList.get(i).getImages())
                    .into(topRecyclerHolder.bookImage);
            switch (i){
                case 0:
                    topRecyclerHolder.topImage.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.drawable.top_first)
                            .into(topRecyclerHolder.topImage);
                    topRecyclerHolder.topCount.setText(1 + "");
                    break;
                case 1:
                    topRecyclerHolder.topImage.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.drawable.top_second)
                            .into(topRecyclerHolder.topImage);
                    topRecyclerHolder.topCount.setText(2 + "");
                    break;
                case 2:
                    topRecyclerHolder.topCount.setText(3 + "");
                    topRecyclerHolder.topImage.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(R.drawable.top_thread)
                            .into(topRecyclerHolder.topImage);
                    break;
                    default:
                        topRecyclerHolder.topImage.setVisibility(View.INVISIBLE);
                        topRecyclerHolder.topCount.setVisibility(View.INVISIBLE);
                        break;
            }
            topRecyclerHolder.bookTital.setText(arrayList.get(i).getTitle() + "");
            topRecyclerHolder.topTital.setText("综合人气榜 第" + (i+1) + "名");

            topRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationStatic.getUserLoginState()){
                        //跳转到书籍详情页面
                        IntentSkip.startIntent(activity , new BookDetailActivity() , arrayList.get(i));
                    }else {
                        IntentSkip.startIntent(activity , new LoginActivity() , null);
                    }
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TopRecyclerHolder extends RecyclerView.ViewHolder{

        private ImageView bookImage;
        private ImageView topImage;
        private TextView bookTital;
        private TextView topTital;
        private TextView topCount;
        public TopRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.top_recycler_image);
            topImage = itemView.findViewById(R.id.top_recycler_top_image);
            bookTital = itemView.findViewById(R.id.top_recycler_tital);
            topTital = itemView.findViewById(R.id.top_recycler_top_text);
            topCount = itemView.findViewById(R.id.top_count);
        }
    }
}
