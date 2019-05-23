package com.tyqhwl.jrqh.homepage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.base.MyViewPager;
import com.tyqhwl.jrqh.homepage.activity.CatagoryActivity;
import com.tyqhwl.jrqh.homepage.activity.TopActivity;
import com.tyqhwl.jrqh.homepage.presenter.BookItemPresenter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.homepage.view.BookItemView;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.user.activity.LookCatagoryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 书城首页
 * wmy
 * 2019-05-20
 */
public class BookMallFragment extends BaseFragment implements BookItemView {
    @BindView(R.id.optional_textview)
    TextView optionalTextview;
    @BindView(R.id.optional_view)
    View optionalView;
    @BindView(R.id.international_textview)
    TextView internationalTextview;
    @BindView(R.id.international_view)
    View internationalView;
    @BindView(R.id.stock_index_textview)
    TextView stockIndexTextview;
    @BindView(R.id.stock_index_view)
    View stockIndexView;
    @BindView(R.id.inland_textview)
    TextView inlandTextview;
    @BindView(R.id.inland_view)
    View inlandView;
    @BindView(R.id.book_mall_frag_seek)
    LinearLayout bookMallFragSeek;
    @BindView(R.id.study_fragment_tital)
    LinearLayout studyFragmentTital;
    //    @BindView(R.id.book_mall_frag_catogory)
//    LinearLayout bookMallFragCatogory;
//    @BindView(R.id.book_mall_frag_top)
//    LinearLayout bookMallFragTop;
//    @BindView(R.id.book_mall_frag_look_top)
//    LinearLayout bookMallFragLookTop;
    @BindView(R.id.book_mall_viewpager)
    MyViewPager bookMallViewpager;
    Unbinder unbinder;
    //    @BindView(R.id.background)
//    ImageView background;
    @BindView(R.id.optional_textview_layout)
    LinearLayout optionalTextviewLayout;
    @BindView(R.id.international_layout)
    LinearLayout internationalLayout;
    @BindView(R.id.stock_index_textview_layout)
    LinearLayout stockIndexTextviewLayout;
    @BindView(R.id.inland_textview_layout)
    LinearLayout inlandTextviewLayout;
    private AwaitDialog awaitDialog;

    public static BookMallFragment newInstance() {

        Bundle args = new Bundle();

        BookMallFragment fragment = new BookMallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private BookItemPresenter bookItemPresenter = new BookItemPresenter(this);
    private BookItemFragment bookItemFragmentFirst;
    private BookItemFragment bookItemFragmentSecond;
    private BookItemFragment bookItemFragmentThread;
    private BookItemSecondFragment bookItemFragmentFour;
    private ArrayList<Fragment> arrayListFragment = new ArrayList<>();

    @Override
    public int getXMLLayout() {
        return R.layout.book_mall_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        bookItemPresenter.getBookData(1, 30, 1);
//        Glide.with(getContext())
//                .load(R.drawable.homepage_image)
//                .into(background);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.book_mall_frag_seek,
            R.id.study_fragment_tital, R.id.book_mall_viewpager})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.book_mall_frag_seek:
                break;
            case R.id.study_fragment_tital:
                break;
//            case R.id.book_mall_frag_catogory:
//                //分类
//                IntentSkip.startIntent(getActivity() , new CatagoryActivity() , null);
//                break;
//            case R.id.book_mall_frag_top:
//                //榜单
//                IntentSkip.startIntent(getActivity() , new TopActivity() , null);
//                break;
//            case R.id.book_mall_frag_look_top:
//                //看单
//                if (ApplicationStatic.getUserLoginState()){
//                    IntentSkip.startIntent(getActivity() , new LookCatagoryActivity() , null);
//                }else {
//                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
//                }
//                break;
        }
    }

    @Override
    public void getBookDataSuccess(ArrayList<BookEntry> arrayList) {
        initViewPager(arrayList);
    }

    private void initViewPager(ArrayList<BookEntry> arrayList) {
        Toast.makeText(getActivity(), arrayList.size() + "###", Toast.LENGTH_LONG).show();
        ApplicationStatic.saveBookAllData(arrayList);
        ArrayList<BookEntry> firstData = new ArrayList<>();
        ArrayList<BookEntry> secondData = new ArrayList<>();
        ArrayList<BookEntry> threadData = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            switch (i % 3) {
                case 0:
                    firstData.add(arrayList.get(i));
                    break;
                case 1:
                    secondData.add(arrayList.get(i));
                    break;
                case 2:
                    threadData.add(arrayList.get(i));
                    break;
            }
        }
        arrayListFragment.clear();
        arrayListFragment.add(bookItemFragmentFirst = new BookItemFragment(firstData));
        arrayListFragment.add(bookItemFragmentSecond = new BookItemFragment(secondData));
        arrayListFragment.add(bookItemFragmentThread = new BookItemFragment(threadData));
        arrayListFragment.add(bookItemFragmentFour = new BookItemSecondFragment());
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), arrayListFragment);
        bookMallViewpager.setAdapter(baseFragmentAdapter);
        bookMallViewpager.setCurrentItem(0);
        showIndex(0);
        bookMallViewpager.setOffscreenPageLimit(arrayListFragment.size());
        bookMallViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                showIndex(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void showIndex(int index) {
        optionalView.setVisibility(View.INVISIBLE);
        internationalView.setVisibility(View.INVISIBLE);
        stockIndexView.setVisibility(View.INVISIBLE);
        inlandView.setVisibility(View.INVISIBLE);
        switch (index) {
            case 0:
                optionalView.setVisibility(View.VISIBLE);
                break;
            case 1:
                internationalView.setVisibility(View.VISIBLE);
                break;
            case 2:
                stockIndexView.setVisibility(View.VISIBLE);
                break;
            case 3:
                inlandView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void getBookDataFail(String msg) {

    }

    @Override
    public void getBookDataSuccessFu(ArrayList<BookEntry> arrayList) {

    }

    @Override
    public void getBookDataFailFu(String msg) {

    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(getActivity(), R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }

    @OnClick({R.id.optional_textview_layout, R.id.international_layout, R.id.stock_index_textview_layout, R.id.inland_textview_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.optional_textview_layout:
                bookMallViewpager.setCurrentItem(0);
                showIndex(0);
                break;
            case R.id.international_layout:
                bookMallViewpager.setCurrentItem(1);
                showIndex(1);
                break;
            case R.id.stock_index_textview_layout:
                bookMallViewpager.setCurrentItem(2);
                showIndex(2);
                break;
            case R.id.inland_textview_layout:
                bookMallViewpager.setCurrentItem(3);
                showIndex(3);
                break;
        }
    }
}
