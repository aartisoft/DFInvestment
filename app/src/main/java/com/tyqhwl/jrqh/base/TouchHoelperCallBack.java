package com.tyqhwl.jrqh.base;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;

//import com.example.dell.futuresbinding.optional.view.OptionalRecyclerViewEntry;
//import com.example.dell.futuresbinding.school.adapter.OptionalRecyclerViewAdapter;

/**
 * RecyclerView 拖动item
 * wmy
 * 2019-03-06
 * */
public class TouchHoelperCallBack extends ItemTouchHelper.Callback {

    private ArrayList<Object> arrayList;
    private RecyclerView.Adapter optionalRecyclerViewAdapter;

    public TouchHoelperCallBack(ArrayList<Object> arrayList, RecyclerView.Adapter optionalRecyclerViewAdapter) {
        this.arrayList = arrayList;
        this.optionalRecyclerViewAdapter = optionalRecyclerViewAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){//表格布局是为上下滑动，左右滑动都可以
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags , swipeFlags);
        }else {
            //线性布局只能上下滑动
            final int dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlag , swipeFlags);
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        //得到当前拖拽的index
        int fromIndex = viewHolder.getAdapterPosition();
        //得到目前需要覆盖的index
        int toPostion = viewHolder1.getAdapterPosition();

        if (fromIndex < toPostion){
            for (int i = fromIndex ; i < toPostion ; i++){
                Collections.swap(arrayList , i , i + 1);
            }
        }else {
            for (int i = fromIndex; i > toPostion; i--) {
                Collections.swap(arrayList, i, i - 1);
                Collections.swap(arrayList, i, i - 1);
            }
        }

        optionalRecyclerViewAdapter.notifyItemMoved(fromIndex , toPostion);


        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        //监听目前手势松开后
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        super.clearView(recyclerView, viewHolder);
    }



}
