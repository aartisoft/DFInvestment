package com.tyqhwl.jrqh.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.user.view.NoticeEntrys;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    private Context context;
    private ArrayList<NoticeEntrys> data;
    private LayoutInflater inflater;
    private NoticeListener noticeListener;

    public interface NoticeListener{
        void onClick(int index);
    }

    public NoticeAdapter(Context context, ArrayList<NoticeEntrys> data, NoticeListener noticeListener) {
        this.context = context;
        this.data = data;
        this.noticeListener = noticeListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.notice_adt , null);
        return new NoticeHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoticeHolder noticeHolder, @SuppressLint("RecyclerView") final int i) {
         if (noticeHolder != null){
             noticeHolder.title.setText(data.get(i).title + "");
             noticeHolder.context.setText(data.get(i).context + "");
             noticeHolder.dataTime.setText(data.get(i).dataTime + "");
             noticeHolder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     noticeListener.onClick(i);
                 }
             });
         }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NoticeHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView context;
        private TextView dataTime;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notice_adt_title);
            context = itemView.findViewById(R.id.notice_adt_context);
            dataTime = itemView.findViewById(R.id.notice_adt_datetime);
        }
    }
}
