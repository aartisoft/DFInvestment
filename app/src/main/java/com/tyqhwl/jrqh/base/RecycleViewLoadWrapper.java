package com.tyqhwl.jrqh.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;


/**
 * 上拉加载更多适配器
 * wmy
 * 2019-03-08
 */
public class RecycleViewLoadWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter adapter;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    //空布局
    private final int ENTRY_ITEM = 3;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    private EntryClickListener entryClickListener;

    public RecycleViewLoadWrapper(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public RecycleViewLoadWrapper(RecyclerView.Adapter adapter, EntryClickListener entryClickListener) {
        this.adapter = adapter;
        this.entryClickListener = entryClickListener;
    }

    public interface EntryClickListener {
        void Click();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_FOOTER) {
//            Log.e("show" , "我是加载更多");
            FootViewHolder footViewHolder = new FootViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.load_more, viewGroup, false));
            return footViewHolder;
        } else if (i == ENTRY_ITEM) {
//            Log.e("show" , "显示数据执行");
            EntryHolder entryHolder = new EntryHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_enter_holder, viewGroup, false));
            return entryHolder;
        } else {
//            Log.e("show" , "我是实体数据");
            return adapter.onCreateViewHolder(viewGroup, i);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.noMoreData.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.noMoreData.setVisibility(View.GONE);
                    break;
                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.noMoreData.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        } else if (viewHolder instanceof EntryHolder) {
            EntryHolder entryHolder = (EntryHolder) viewHolder;
            entryHolder.retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entryClickListener != null) {
                        entryClickListener.Click();
                    } else {
                        Log.e("RecycleViewLoadWrapper" , "构造方式使用错误");
                    }
                }
            });
        } else {
            adapter.onBindViewHolder(viewHolder, i);
        }
    }

    @Override
    public int getItemCount() {
//        Log.e("show" , adapter.getItemCount() + "******");
//        if (adapter.getItemCount() == 0){
//            return  0;
//        }else {
            return adapter.getItemCount() + 1;
//        }

    }

    @Override
    public int getItemViewType(int position) {
//        Log.e("show" , position+ "******");
        // 最后一个item设置为FooterView

        if (adapter.getItemCount() == 0){
            return ENTRY_ITEM;
        }

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
//            if (getItemCount() == 0) {
//                return ENTRY_ITEM;
//            } else {
                return TYPE_ITEM;
//            }

        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        TextView noMoreData;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            pbLoading = itemView.findViewById(R.id.progress_bar);
            tvLoading = itemView.findViewById(R.id.tv_load_more_message);
            noMoreData = itemView.findViewById(R.id.load_more_no_more_data);
        }

    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    /**
     * 设置空白显示页面Holder
     */
    public class EntryHolder extends RecyclerView.ViewHolder {
        private TextView retry;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            retry = itemView.findViewById(R.id.adapter_enter_holder_retry);
        }
    }


}
