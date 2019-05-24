package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionEntry;

import java.util.ArrayList;

import tech.com.commoncore.manager.GlideManager;

public class MyAttentionAdapter extends RecyclerView.Adapter<MyAttentionAdapter.MyAttentionHolder> {


    private Context context;
    private ArrayList<MyAttentionEntry> arrayList;
    private LayoutInflater inflater;

    private ArrayList<AttentionListEntry> data = new ArrayList<>();
    private MyAttentionItemAdapte myAttentionItemAdapte;

    public MyAttentionAdapter(Context context, ArrayList<MyAttentionEntry> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyAttentionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.my_attention_adapter , null);
        return new MyAttentionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAttentionHolder myAttentionHolder, int i) {
        if (myAttentionHolder != null){
            myAttentionHolder.count.setText(arrayList.get(i).summary + "");
            myAttentionHolder.data.setText(arrayList.get(i).time + "");
            GlideManager.loadCircleImg(arrayList.get(i).userImage , myAttentionHolder.userImage);
            myAttentionHolder.userName.setText(arrayList.get(i).userName);
            Glide.with(context)
                    .load(arrayList.get(i).image)
                    .into(myAttentionHolder.image);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            data.clear();
            data.addAll(arrayList.get(i).attentionListEntries);
            myAttentionItemAdapte = new MyAttentionItemAdapte(context , data);
            myAttentionHolder.recyclerView.setLayoutManager(linearLayoutManager);
            myAttentionHolder.recyclerView.setAdapter(myAttentionItemAdapte);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyAttentionHolder extends RecyclerView.ViewHolder{
        private TextView count;
        private TextView data;
        private ImageView userImage;
        private TextView userName;
        private RelativeLayout collectImage;
        private RelativeLayout commentImage;
        private RecyclerView recyclerView;
        private ImageView image;
        public MyAttentionHolder(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.inversor_adt_count);
            data = itemView.findViewById(R.id.inversor_adt_data);
            userImage = itemView.findViewById(R.id.inversor_adt_userimage);
            userName = itemView.findViewById(R.id.inversor_adt_name);
            image = itemView.findViewById(R.id.my_attention_adt_image);
            collectImage = itemView.findViewById(R.id.inversor_adt_collect);
            commentImage = itemView.findViewById(R.id.inversor_adt_comment);
            recyclerView = itemView.findViewById(R.id.my_attention_adt_recyvlerview);
        }
    }
}
