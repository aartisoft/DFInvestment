package com.tyqhwl.jrqh.homepage.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.homepage.adapter.BookItemAdapter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 精选fragment
 * wmy
 * 2019-05-20
 */
public class BookItemFragment extends BaseFragment {


    @BindView(R.id.book_mall_gookbook_recycler_view)
    RecyclerView bookMallGookbookRecyclerView;
    Unbinder unbinder;
    private int indexType;

    ArrayList<BookEntry> data = new ArrayList<BookEntry>();
    private BookItemAdapter bookItemAdapter;
    @SuppressLint("ValidFragment")
    public BookItemFragment(ArrayList<BookEntry> data) {
        this.data = data;
    }

    public BookItemFragment() {
    }

    @Override
    public int getXMLLayout() {
        return R.layout.book_item_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity() , 3);
        bookItemAdapter = new BookItemAdapter(data , getActivity() , getActivity());
        bookMallGookbookRecyclerView.setLayoutManager(layoutManager);
        bookMallGookbookRecyclerView.setNestedScrollingEnabled(false);
        bookMallGookbookRecyclerView.setAdapter(bookItemAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
