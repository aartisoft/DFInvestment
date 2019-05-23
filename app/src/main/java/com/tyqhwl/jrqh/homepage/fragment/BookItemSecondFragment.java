package com.tyqhwl.jrqh.homepage.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.homepage.adapter.BookItemAdapter;
import com.tyqhwl.jrqh.homepage.presenter.BookItemPresenter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.homepage.view.BookItemView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookItemSecondFragment extends BaseFragment implements BookItemView {
    @BindView(R.id.book_mall_gookbook_recycler_view)
    RecyclerView bookMallGookbookRecyclerView;
    Unbinder unbinder;
    ArrayList<BookEntry> data = new ArrayList<BookEntry>();
    private BookItemAdapter bookItemAdapter;

    private BookItemPresenter bookItemPresenter = new BookItemPresenter(this);

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
        bookItemPresenter.getBookDataSecond(1 , 10 , 2);
        return rootView;
    }
    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity() , 4);
        bookItemAdapter = new BookItemAdapter(data , getActivity() , getActivity());
        bookMallGookbookRecyclerView.setLayoutManager(layoutManager);
        bookMallGookbookRecyclerView.setAdapter(bookItemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getBookDataSuccess(ArrayList<BookEntry> arrayList) {

    }

    @Override
    public void getBookDataFail(String msg) {

    }

    @Override
    public void getBookDataSuccessFu(ArrayList<BookEntry> arrayList) {
        ApplicationStatic.saveBookAllData(arrayList);
        data.clear();
        data.addAll(arrayList);
        bookItemAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(EventBusTag.ALL_BOOK);
    }

    @Override
    public void getBookDataFailFu(String msg) {

    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
