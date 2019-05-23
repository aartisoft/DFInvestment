package com.tyqhwl.jrqh.goover.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.goover.view.UserCollectEntry;

import java.util.ArrayList;

public class UserCollectAdapter extends RecyclerView.Adapter<UserCollectAdapter.UserCollectHolder> {

    private Context context;
    private ArrayList<UserCollectEntry> data;
    private UserCollectListener userCollectListener;
    private LayoutInflater inflater;
    private CancelCollectListener cancelCollectListener;

    public UserCollectAdapter(Context context, ArrayList<UserCollectEntry> data, UserCollectListener userCollectListener) {
        this.context = context;
        this.data = data;
        this.userCollectListener = userCollectListener;
        inflater = LayoutInflater.from(context);
    }


    public UserCollectAdapter(Context context, ArrayList<UserCollectEntry> data, UserCollectListener userCollectListener,
                              CancelCollectListener cancelCollectListener) {
        this.context = context;
        this.data = data;
        this.userCollectListener = userCollectListener;
        this.cancelCollectListener = cancelCollectListener;
        inflater = LayoutInflater.from(context);
    }

    public interface UserCollectListener{
        void onClick(int index , int postId);
    }


    public interface CancelCollectListener{
        void onCancelCollect(int postId);
    }


    @NonNull
    @Override
    public UserCollectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.user_collect_adapter , null);
        return new UserCollectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCollectHolder userCollectHolder, int i) {
        if (userCollectHolder != null){
            userCollectHolder.read.setText(data.get(i).read + " 阅读");
            userCollectHolder.title.setText(data.get(i).title);
            userCollectHolder.context.setText(data.get(i).content);
            userCollectHolder.checkBox.setChecked(true);
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(11);
            Glide.with(context)
                    .load(data.get(i).imageUrl)
                    .apply(new RequestOptions().bitmapTransform(roundedCorners).error(R.drawable.tu_wuzhuangtai).placeholder(R.drawable.tu_jiazai))
                    .into(userCollectHolder.imageView);

            userCollectHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userCollectListener.onClick(i , data.get(i).postId);
                }
            });
            userCollectHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        userCollectHolder.checkboxText.setText("取消");
                        userCollectHolder.checkboxText.setTextColor(Color.parseColor("#FA6750"));
//                        notifyDataSetChanged();
                    }else {
                        userCollectHolder.checkboxText.setText("收藏");
                        userCollectHolder.checkboxText.setTextColor(Color.parseColor("#7D8596"));
//                        notifyDataSetChanged();
                        cancelCollectListener.onCancelCollect(data.get(i).postId);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserCollectHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView context;
        private TextView read;
        private CheckBox checkBox;
        private TextView checkboxText;
        public UserCollectHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.seek_guss_you_like_image);
            title = itemView.findViewById(R.id.seek_guss_you_like_title);
            context = itemView.findViewById(R.id.seek_guss_you_like_content);
            read = itemView.findViewById(R.id.seek_guss_you_like_read);
            checkBox = itemView.findViewById(R.id.seek_guss_you_like_collect_checkbox);
            checkboxText = itemView.findViewById(R.id.seek_guss_you_like_collect_text);
        }
    }
}
