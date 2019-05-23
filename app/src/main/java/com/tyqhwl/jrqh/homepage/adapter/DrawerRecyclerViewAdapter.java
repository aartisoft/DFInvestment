package com.tyqhwl.jrqh.homepage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.homepage.view.BookSection;

import java.util.ArrayList;

public class DrawerRecyclerViewAdapter extends RecyclerView.Adapter<DrawerRecyclerViewAdapter.DrawerRecyclerViewHolder> {

    private ArrayList<BookSection> arrayList;
    private Context context;
    private LayoutInflater inflater;
    private DrawerRecyclerListener drawerRecyclerListener;
    public interface DrawerRecyclerListener{
        void onClickListener(int index);
    }

    public DrawerRecyclerViewAdapter(ArrayList<BookSection> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public DrawerRecyclerViewAdapter(ArrayList<BookSection> arrayList, Context context, DrawerRecyclerListener drawerRecyclerListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.drawerRecyclerListener = drawerRecyclerListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.drawer_recycler_view_adapter , null);
        return new DrawerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerRecyclerViewHolder drawerRecyclerViewHolder, int i) {
        if (drawerRecyclerViewHolder != null){
            drawerRecyclerViewHolder.title.setText(arrayList.get(i).getTitle());
            drawerRecyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerRecyclerListener.onClickListener(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public DrawerRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.drawer_recicler_view_adt_tital);
        }
    }
}
