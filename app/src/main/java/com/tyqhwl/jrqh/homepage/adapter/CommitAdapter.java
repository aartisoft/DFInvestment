package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.CommitEntry;

import java.util.ArrayList;

import tech.com.commoncore.manager.GlideManager;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitHolder> {


    private ArrayList<CommitEntry> arrayList;
    private Context context;
    private LayoutInflater inflater;


    public CommitAdapter(ArrayList<CommitEntry> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CommitHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.commit_adapter , null);
        return new CommitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitHolder commitHolder, int i) {
        if (commitHolder != null){
            if (arrayList.get(i).getUserId().equals(ApplicationStatic.getUserMessage().getObjectId())){
                commitHolder.userName.setText("我");
            }else {
                commitHolder.userName.setText(arrayList.get(i).getUserName());
            }
            commitHolder.commit.setText(arrayList.get(i).getUserComment());
            GlideManager.loadCircleImg(arrayList.get(i).getUserImage() , commitHolder.userImage);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CommitHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView commit;
        private ImageView userImage;
        public CommitHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.commit_adt_username);
            commit = itemView.findViewById(R.id.commit_adt_count);
            userImage = itemView.findViewById(R.id.commit_adt_userimage);
        }
    }
}
