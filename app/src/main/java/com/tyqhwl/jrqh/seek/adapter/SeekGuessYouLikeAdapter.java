package com.tyqhwl.jrqh.seek.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.seek.presenter.InversorPresenter;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import com.tyqhwl.jrqh.seek.view.SaveNewMessageView;

import java.util.ArrayList;

public class SeekGuessYouLikeAdapter extends RecyclerView.Adapter<SeekGuessYouLikeAdapter.SeekGuessYouLikeHolder> implements SaveNewMessageView {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<InversorEntry> data;
    private SeekGuessYouLikeListener seekGuessYouLikeListener;
    private InversorPresenter inversorPresenter = new InversorPresenter(this);
    private AwaitDialog awaitDialog;
    private Activity baseActivity;
    public SeekGuessYouLikeAdapter(Context context, ArrayList<InversorEntry> data, SeekGuessYouLikeListener seekGuessYouLikeListener) {
        this.context = context;
        this.data = data;
        this.seekGuessYouLikeListener = seekGuessYouLikeListener;
        inflater = LayoutInflater.from(context);
    }

    public SeekGuessYouLikeAdapter(Context context, ArrayList<InversorEntry> data, SeekGuessYouLikeListener seekGuessYouLikeListener , Activity baseActivity) {
        this.context = context;
        this.data = data;
        this.seekGuessYouLikeListener = seekGuessYouLikeListener;
        inflater = LayoutInflater.from(context);
        this.baseActivity = baseActivity;
    }

    @Override
    public void getSaveNewMessageSuccess() {
        Log.e("show","收藏成功" );
    }

    @Override
    public void getSaveNewMessageFail(String msg) {
        Log.e("show",msg );
    }

    @Override
    public void cancelSaveSuccess() {
        Log.e("show","删除成功" );
    }

    @Override
    public void cancelSaveFail(String msg) {
        Log.e("show",msg );
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(baseActivity, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }

    public interface SeekGuessYouLikeListener{
        void onClick(int index);
    }

    @NonNull
    @Override
    public SeekGuessYouLikeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.seek_guess_you_like_adapter , null);
        return new SeekGuessYouLikeHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SeekGuessYouLikeHolder seekGuessYouLikeHolder, @SuppressLint("RecyclerView") int i) {
        if (seekGuessYouLikeHolder != null){
//            Log.e("show" , data.get(i).toString() + "");
            seekGuessYouLikeHolder.read.setText(data.get(i).getRead() + " 阅读");
            seekGuessYouLikeHolder.title.setText(data.get(i).getTitle());
            seekGuessYouLikeHolder.context.setText(data.get(i).getSummary());
            seekGuessYouLikeHolder.checkBox.setImageResource(data.get(i).isCheck() ? R.drawable.collect_check : R.drawable.collect_ischeck);
            seekGuessYouLikeHolder.checkboxText.setTextColor(data.get(i).isCheck() ? Color.parseColor("#FA6750") : Color.parseColor("#7D8596"));
            seekGuessYouLikeHolder.checkboxText.setText(data.get(i).isCheck() ? "取消" :"收藏");
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(11);
            Glide.with(context)
                    .load(data.get(i).getThumb())
                    .apply(new RequestOptions().bitmapTransform(roundedCorners).error(R.drawable.tu_wuzhuangtai).placeholder(R.drawable.tu_jiazai))
                    .into(seekGuessYouLikeHolder.imageView);

            seekGuessYouLikeHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekGuessYouLikeListener.onClick(i);
                }
            });

            seekGuessYouLikeHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消用户保存新闻
                    if (ApplicationStatic.getUserLoginState()){
                        inversorPresenter.deleteuserNewMessage(data.get(i).getPost_id());
                    }else {
                        Log.e("show" , "未登录");
                        IntentSkip.startIntent(baseActivity , new LoginActivity() , null);
                        return;
                    }
                    if (data.get(i).isCheck()){
                        seekGuessYouLikeHolder.checkBox.setImageResource(R.drawable.collect_ischeck);
                        data.get(i).setCheck(false);
                        seekGuessYouLikeHolder.checkboxText.setText("收藏");
                        seekGuessYouLikeHolder.checkboxText.setTextColor(Color.parseColor("#7D8596"));

                        notifyDataSetChanged();

                    }else {
                        //保存用户收藏新闻
                        if (ApplicationStatic.getUserLoginState()){
                            inversorPresenter.saveUserNewMesage(data.get(i).getRead() , data.get(i).getSummary() ,
                                    data.get(i).getTime() , data.get(i).getTitle(),data.get(i).getThumb() , data.get(i).getPost_id() , ApplicationStatic.getUserMessage().getObjectId());
                        }else {
                            Log.e("show" , "未登录");
                            IntentSkip.startIntent(baseActivity , new LoginActivity() , null);
                            return;
                        }
                        seekGuessYouLikeHolder.checkBox.setImageResource(R.drawable.collect_check);
                        data.get(i).setCheck(true);
                        seekGuessYouLikeHolder.checkboxText.setText("取消");
                        seekGuessYouLikeHolder.checkboxText.setTextColor(Color.parseColor("#FA6750"));


                        notifyDataSetChanged();
                    }
                }
            });


//            seekGuessYouLikeHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//                        data.get(i).setCheck(true);
//                        seekGuessYouLikeHolder.checkboxText.setText("取消");
//                        seekGuessYouLikeHolder.checkboxText.setTextColor(Color.parseColor("#FA6750"));
//                        //保存用户收藏新闻
//                        if (ApplicationStatic.getUserLoginState()){
//                            inversorPresenter.saveUserNewMesage(data.get(i).getRead() , data.get(i).getSummary() ,
//                                    data.get(i).getTime() , data.get(i).getTitle(),data.get(i).getThumb() , data.get(i).getPost_id() , ApplicationStatic.getUserMessage().getObjectId());
//                        }else {
//                            Log.e("show" , "未登录");
//                        }
//
//                        notifyDataSetChanged();
//
//                    }else {
//                        data.get(i).setCheck(false);
//                        seekGuessYouLikeHolder.checkboxText.setText("收藏");
//                        seekGuessYouLikeHolder.checkboxText.setTextColor(Color.parseColor("#7D8596"));
//                        //取消用户保存新闻
//                        if (ApplicationStatic.getUserLoginState()){
//                            inversorPresenter.deleteuserNewMessage(data.get(i).getPost_id());
//                        }else {
//                            Log.e("show" , "未登录");
//                        }
//                        notifyDataSetChanged();
//                    }
//                }
//            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SeekGuessYouLikeHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView context;
        private TextView read;
        private ImageView checkBox;
        private TextView checkboxText;

        public SeekGuessYouLikeHolder(@NonNull View itemView) {
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
