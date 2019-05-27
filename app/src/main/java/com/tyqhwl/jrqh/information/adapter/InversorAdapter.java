package com.tyqhwl.jrqh.information.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.CommentActivity;
import com.tyqhwl.jrqh.information.activity.InversorCommentActivity;
import com.tyqhwl.jrqh.information.presenter.AddInversorMyCollectPresenter;
import com.tyqhwl.jrqh.information.view.AddInversorMyCollectView;
import com.tyqhwl.jrqh.information.view.AddInversorMyEntry;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import java.util.ArrayList;

import tech.com.commoncore.manager.GlideManager;

public class InversorAdapter extends RecyclerView.Adapter<InversorAdapter.InversorHolder> implements AddInversorMyCollectView {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<InversorEntry> arrayList;
    private InversorClickListener inversorClickListener;
    private Activity activity;
    private AddInversorMyCollectPresenter addInversorMyCollectPresenter = new AddInversorMyCollectPresenter(this);
    private AwaitDialog awaitDialog;
    @Override
    public void getAddInversorMySuccess() {
        Toast.makeText(context , "收藏成功" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAddInversorMyFail(String msg) {
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(context, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }

    public interface InversorClickListener{
        void onClickListerer(int index);
    }

    public InversorAdapter(Context context, ArrayList<InversorEntry> arrayList, InversorClickListener inversorClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.inversorClickListener = inversorClickListener;
    }

    public InversorAdapter(Context context, ArrayList<InversorEntry> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public InversorAdapter(Context context, ArrayList<InversorEntry> arrayList, InversorClickListener inversorClickListener, Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        this.inversorClickListener = inversorClickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public InversorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inversor_adapter , viewGroup , false);
        return new InversorHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InversorHolder inversorHolder, @SuppressLint("RecyclerView") final int i) {
        if (inversorHolder != null){
            inversorHolder.count.setText(arrayList.get(i).summary);
            inversorHolder.data.setText(arrayList.get(i).getTime());
            inversorHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inversorClickListener != null){
                        inversorClickListener.onClickListerer(i);
                    }else {
                        Log.e("InversorAdapter" , "构造方法错误");
                    }
                }
            });
            Glide.with(context)
                    .load(arrayList.get(i).image)
                    .into(inversorHolder.image);
            String userImage = "http://img3.imgtn.bdimg.com/it/u=1451961535,3314663922&fm=26&gp=0.jpg";
            String userName = "期货小能手";
            switch (i % 10){
                case 0:
                    GlideManager.loadCircleImg("http://img3.imgtn.bdimg.com/it/u=1451961535,3314663922&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("褪了色旳回憶");
                    userImage = "http://img3.imgtn.bdimg.com/it/u=1451961535,3314663922&fm=26&gp=0.jpg";
                    userName = "褪了色旳回憶";
                    break;
                case 1:
                    GlideManager.loadCircleImg("http://img5.imgtn.bdimg.com/it/u=3198151682,3105226015&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("时光あ瘦了~");
                    userImage = "http://img5.imgtn.bdimg.com/it/u=3198151682,3105226015&fm=26&gp=0.jpg";
                    userName = "时光あ瘦了~";
                    break;
                case 2:
                    GlideManager.loadCircleImg("http://img0.imgtn.bdimg.com/it/u=2088214962,2895726712&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("曾有↘几人");
                    userImage = "http://img0.imgtn.bdimg.com/it/u=2088214962,2895726712&fm=26&gp=0.jpg";
                    userName = "曾有↘几人";
                    break;
                case 3:
                    GlideManager.loadCircleImg("http://img4.imgtn.bdimg.com/it/u=2181297549,2875630229&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("亲，我没跑");
                    userImage = "http://img4.imgtn.bdimg.com/it/u=2181297549,2875630229&fm=26&gp=0.jpg" ;
                    userName = "亲，我没跑";
                    break;
                case 4:
                    GlideManager.loadCircleImg("http://img5.imgtn.bdimg.com/it/u=2575280810,3016252651&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("多啦呮諟個夢");
                    userImage = "http://img5.imgtn.bdimg.com/it/u=2575280810,3016252651&fm=26&gp=0.jpg";
                    userName = "多啦呮諟個夢";
                    break;
                case 5:
                    GlideManager.loadCircleImg("http://img4.imgtn.bdimg.com/it/u=1627933634,1160629598&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("时间是记忆的橡皮擦");
                    userImage = "http://img4.imgtn.bdimg.com/it/u=1627933634,1160629598&fm=26&gp=0.jpg";
                    userName = "时间是记忆的橡皮擦";
                    break;
                case 6:
                    GlideManager.loadCircleImg("http://img3.imgtn.bdimg.com/it/u=2461781229,1858231552&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("大哥大@");
                    userImage = "http://img3.imgtn.bdimg.com/it/u=2461781229,1858231552&fm=26&gp=0.jpg";
                    userName = "大哥大@";
                    break;
                case 7:
                    GlideManager.loadCircleImg("http://img4.imgtn.bdimg.com/it/u=2365173552,1600985734&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("漃寞煙錇謌");
                    userImage = "http://img4.imgtn.bdimg.com/it/u=2365173552,1600985734&fm=26&gp=0.jpg";
                    userName = "漃寞煙錇謌";
                    break;
                case 8:
                    GlideManager.loadCircleImg("http://img0.imgtn.bdimg.com/it/u=1901600690,2735789840&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("回忆刺穿心脏╮");
                    userImage = "http://img0.imgtn.bdimg.com/it/u=1901600690,2735789840&fm=26&gp=0.jpg";
                    userName = "回忆刺穿心脏╮";
                    break;
                case 9:
                    GlideManager.loadCircleImg("http://img3.imgtn.bdimg.com/it/u=2852730361,1563734226&fm=26&gp=0.jpg" , inversorHolder.userImage);
                    inversorHolder.userName.setText("古城小巷萌街少年");
                    userImage = "http://img3.imgtn.bdimg.com/it/u=2852730361,1563734226&fm=26&gp=0.jpg";
                    userName = "古城小巷萌街少年";
                    break;
            }


            String finalUserImage = userImage;
            String finalUserName = userName;
            inversorHolder.collectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationStatic.getUserLoginState()){
                        //添加到我的关注
                        addInversorMyCollectPresenter.getAddInversorMyCollect(new AddInversorMyEntry(arrayList.get(i).thumb , arrayList.get(i).read , arrayList.get(i).time,
                                arrayList.get(i).title , arrayList.get(i).summary , arrayList.get(i).post_id , ApplicationStatic.getUserMessage().getObjectId() ,
                                finalUserImage,arrayList.get(i).image , finalUserName));
                    }else {
                        //登录
                        IntentSkip.startIntent(activity , new LoginActivity() , null);
                    }
                }
            });

            inversorHolder.commentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationStatic.getUserLoginState()){
                        //添加评论
//                        addInversorMyCollectPresenter.getAddInversorMyCollect(new AddInversorMyEntry(arrayList.get(i).thumb , arrayList.get(i).read , arrayList.get(i).time,
//                                arrayList.get(i).title , arrayList.get(i).summary , arrayList.get(i).post_id , ApplicationStatic.getUserMessage().getObjectId() ,
//                                finalUserImage,arrayList.get(i).image , finalUserName));

                        IntentSkip.startIntent(activity , new InversorCommentActivity() , new AddInversorMyEntry(arrayList.get(i).thumb , arrayList.get(i).read , arrayList.get(i).time,
                                arrayList.get(i).title , arrayList.get(i).summary , arrayList.get(i).post_id , ApplicationStatic.getUserMessage().getObjectId() ,
                                finalUserImage,arrayList.get(i).image , finalUserName));

                    }else {
                        //登录
                        IntentSkip.startIntent(activity , new LoginActivity() , null);
                    }
                }
            });


        }
    }

    public class InversorHolder extends RecyclerView.ViewHolder{
        private TextView count;
        private TextView data;
        private ImageView userImage;
        private TextView userName;
        private RelativeLayout collectImage;
        private RelativeLayout commentImage;
        private ImageView image;
        public InversorHolder(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.inversor_adt_count);
            data = itemView.findViewById(R.id.inversor_adt_data);
            userName = itemView.findViewById(R.id.inversor_adt_name);
            userImage = itemView.findViewById(R.id.inversor_adt_userimage);
            collectImage = itemView.findViewById(R.id.inversor_adt_collect);
            commentImage = itemView.findViewById(R.id.inversor_adt_comment);
            image = itemView.findViewById(R.id.inversor_adt_image);

        }
    }

}
