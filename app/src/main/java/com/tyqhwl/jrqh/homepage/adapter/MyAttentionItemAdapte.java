package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;

import java.util.ArrayList;

public class MyAttentionItemAdapte extends RecyclerView.Adapter<MyAttentionItemAdapte.MyAttentionItemHolder> {

    private Context context;
    private ArrayList<AttentionListEntry> data;
    private LayoutInflater inflater;

    public MyAttentionItemAdapte(Context context, ArrayList<AttentionListEntry> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyAttentionItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.my_attention_item_adapter , null);
        return new MyAttentionItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAttentionItemHolder myAttentionItemHolder, int i) {

        if (myAttentionItemHolder != null){
            myAttentionItemHolder.userName.setText(data.get(i).userName + " : ");
            myAttentionItemHolder.comment.setText(data.get(i).userComment + "");

            if (i == 0){
                myAttentionItemHolder.view.setVisibility(View.VISIBLE);
            }else {
                myAttentionItemHolder.view.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyAttentionItemHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView comment;
        private View view;
        public MyAttentionItemHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.my_atten_item_adt_username);
            comment = itemView.findViewById(R.id.my_atten_item_adt_comment);
            view = itemView.findViewById(R.id.my_attention_item_head);
        }
    }
}
