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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;

import java.util.ArrayList;

public class HomePageSecondAdapter extends RecyclerView.Adapter<HomePageSecondAdapter.HomePageSecondHolder> {


    private ArrayList<HomePageEntry> data;
    private Context context;
    private LayoutInflater inflater;
    private HomePageAdapter.HomePageLinsener homePageLinsener;

    public interface HomePageLinsener{
        void onClick(int index);
    }

    public HomePageSecondAdapter(ArrayList<HomePageEntry> data, Context context, HomePageAdapter.HomePageLinsener homePageLinsener) {
        this.data = data;
        this.context = context;
        this.homePageLinsener = homePageLinsener;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public HomePageSecondHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.homepage_second_adapter , null);
        return new HomePageSecondHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageSecondHolder homePageSecondHolder, int i) {
        if (homePageSecondHolder != null){
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(11);
            Glide.with(context)
                    .load(data.get(i).image)
                    .apply(new RequestOptions().bitmapTransform(roundedCorners).error(R.drawable.tu_wuzhuangtai).placeholder(R.drawable.tu_jiazai))
                    .into(homePageSecondHolder.imageView);
            homePageSecondHolder.read.setText(data.get(i).read + " 阅读");
            homePageSecondHolder.title.setText(data.get(i).title);
            homePageSecondHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homePageLinsener != null){
                        homePageLinsener.onClick(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HomePageSecondHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView read;
        public HomePageSecondHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.homepage_adt_image);
            title = itemView.findViewById(R.id.homepage_adt_title);
            read = itemView.findViewById(R.id.homepage_adt_read);
        }
    }

}
