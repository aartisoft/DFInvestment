package com.tyqhwl.jrqh.bookrack.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.bookrack.activity.SigninsActivity;
import com.tyqhwl.jrqh.homepage.activity.BookDetailActivity;
import com.tyqhwl.jrqh.homepage.adapter.BookItemAdapter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.login.activity.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 书架首页
 * wmy
 * 2019-05-21
 */
public class BookRackFragment extends BaseFragment {
    @BindView(R.id.book_rack_signin_image)
    ImageView bookRackSigninImage;
    @BindView(R.id.book_rack_seek_image)
    ImageView bookRackSeekImage;
    @BindView(R.id.book_detail_image)
    ImageView bookDetailImage;
    @BindView(R.id.book_detail_tital)
    TextView bookDetailTital;
    @BindView(R.id.continue_reading)
    LinearLayout continueReading;
    @BindView(R.id.book_rack_frag_recyclerview)
    RecyclerView bookRackFragRecyclerview;
    Unbinder unbinder;
    ArrayList<BookEntry> data = new ArrayList<BookEntry>();
    @BindView(R.id.background)
    ImageView background;
    private BookItemAdapter bookItemAdapter;

    public static BookRackFragment newInstance() {

        Bundle args = new Bundle();

        BookRackFragment fragment = new BookRackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.book_rack_fragment;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {
        if (o.equals(EventBusTag.ALL_BOOK)) {
            data.addAll(ApplicationStatic.getBookAllData());
            bookItemAdapter.notifyDataSetChanged();
            Glide.with(getActivity())
                    .load(data.get(0).getImages())
                    .into(bookDetailImage);
            bookDetailTital.setText(data.get(0).getTitle());

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        Glide.with(getActivity())
                .load(R.drawable.user_background)
                .into(background);
        return rootView;
    }

    private void initRecyclerView() {
        data.clear();
        data.addAll(ApplicationStatic.getBookAllData());
        Log.e("show", data.size() + "SSSSss");
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        bookItemAdapter = new BookItemAdapter(data, getActivity(), getActivity());
        bookRackFragRecyclerview.setLayoutManager(layoutManager);
        bookRackFragRecyclerview.setAdapter(bookItemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.book_rack_signin_image, R.id.book_rack_seek_image, R.id.book_detail_image,
            R.id.book_detail_tital, R.id.continue_reading, R.id.book_rack_frag_recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_rack_signin_image:
                //签到
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SigninsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }

                break;
            case R.id.book_rack_seek_image:
                break;
            case R.id.book_detail_image:
                break;
            case R.id.book_detail_tital:
                break;
            case R.id.continue_reading:
                //跳转到书籍详情页面
                IntentSkip.startIntent(getActivity(), new BookDetailActivity(), data.get(0));
                break;
        }
    }
}
