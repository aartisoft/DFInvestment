package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EndlessRecyclerOnScrollListener;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.FullyLinearLayoutManager;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.base.RecycleViewLoadWrapper;
import com.tyqhwl.jrqh.homepage.adapter.BookDetailRecyclerViewAdapter;
import com.tyqhwl.jrqh.homepage.adapter.DrawerRecyclerViewAdapter;
import com.tyqhwl.jrqh.homepage.presenter.BookDetailPresenter;
import com.tyqhwl.jrqh.homepage.view.BookDetailView;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.homepage.view.BookSecTionContentEntry;
import com.tyqhwl.jrqh.homepage.view.BookSection;
import com.tyqhwl.jrqh.homepage.view.CommentActEntry;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书籍详情页面
 * wmy
 * 2019-05-20
 */
public class BookDetailActivity extends BaseActivity implements BookDetailView {
    @BindView(R.id.book_detail_act_back)
    ImageView bookDetailActBack;
    @BindView(R.id.book_detail_act_more)
    ImageView bookDetailActMore;
    @BindView(R.id.book_detail_act_recyclerview)
    RecyclerView bookDetailActRecyclerview;
    @BindView(R.id.book_detail_act_catelogy)
    LinearLayout bookDetailActCatelogy;
    @BindView(R.id.book_detail_act_setting)
    LinearLayout bookDetailActSetting;
    @BindView(R.id.book_detail_act_comment)
    LinearLayout bookDetailActComment;
    @BindView(R.id.drawer_tital)
    TextView drawerTital;
    @BindView(R.id.drawer_count)
    TextView drawerCount;
    @BindView(R.id.drawer_recycler_view)
    RecyclerView drawerRecyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.book_detail_act_catelogy_layout)
    LinearLayout bookDetailActCatelogyLayout;
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.book_detail_act_model)
    View bookDetailActModel;
    @BindView(R.id.book_detail_act_checkbox)
    CheckBox bookDetailActCheckbox;
    private int indexSection = 0;
    private boolean showCatalogryType = false;
    private boolean isClickType = false;
    private int isClickTypeCount = 0;
    private BookDetailPresenter bookDetailPresenter = new BookDetailPresenter(this);
    private ArrayList<BookSection> data = new ArrayList<>();//章节列表数据源
    private ArrayList<BookSecTionContentEntry> dataSecond = new ArrayList();//章节详情数据源
    private DrawerRecyclerViewAdapter drawerRecyclerViewAdapter;
    private BookDetailRecyclerViewAdapter bookDetailRecyclerView;
    private RecycleViewLoadWrapper recycleViewLoadWrapper;
    private BookEntry bookEntry;
    private CommentActEntry commentActEntry;

    @Override
    public int getXMLLayout() {
        return R.layout.book_detail_activity;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventThreadMain(Object o) {
        if (o.equals(EventBusTag.SHOW_BOOK_SECTION)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }

        if (o.equals(EventBusTag.READING_MODEL)) {
            if (ApplicationStatic.getRadingModel()) {
                bookDetailActModel.setVisibility(View.VISIBLE);
                bookDetailActCheckbox.setChecked(ApplicationStatic.getRadingModel());
            } else {
                bookDetailActModel.setVisibility(View.INVISIBLE);
                bookDetailActCheckbox.setChecked(ApplicationStatic.getRadingModel());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getIntentBuild();
        initDrawerRecyclerView();
        initRecyclerView();
        drawerLayout.closeDrawer(Gravity.LEFT);
        Glide.with(this)
                .load(R.drawable.books_detail_background)
                .into(background);
        if (ApplicationStatic.getRadingModel()) {
            bookDetailActModel.setVisibility(View.VISIBLE);
            bookDetailActCheckbox.setChecked(ApplicationStatic.getRadingModel());
        } else {
            bookDetailActCheckbox.setChecked(ApplicationStatic.getRadingModel());
            bookDetailActModel.setVisibility(View.INVISIBLE);
        }

        bookDetailActCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookDetailActCheckbox.isChecked()){
                    ApplicationStatic.saveReadingModel(bookDetailActCheckbox.isChecked());
                    bookDetailActModel.setVisibility(View.VISIBLE);
                }else {
                    bookDetailActModel.setVisibility(View.INVISIBLE);
                    ApplicationStatic.saveReadingModel(bookDetailActCheckbox.isChecked());
                }

            }
        });
    }

    //章节详情
    private void initRecyclerView() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        bookDetailRecyclerView = new BookDetailRecyclerViewAdapter(dataSecond, this);
        recycleViewLoadWrapper = new RecycleViewLoadWrapper(bookDetailRecyclerView);
        bookDetailActRecyclerview.setLayoutManager(linearLayoutManager);
        bookDetailActRecyclerview.setAdapter(recycleViewLoadWrapper);
        bookDetailActRecyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (indexSection >= data.size()) {
                    Toast.makeText(BookDetailActivity.this, "没有更多章节了", Toast.LENGTH_SHORT).show();
                } else {
                    //加載更多
                    recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING);
                    bookDetailPresenter.getBookSectionContent(data.get(indexSection).getId());
                    indexSection++;
                }
            }

            @Override
            public void onPullDown(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    //章节列表
    private void initDrawerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerViewAdapter = new DrawerRecyclerViewAdapter(data, this, new DrawerRecyclerViewAdapter.DrawerRecyclerListener() {
            @Override
            public void onClickListener(int index) {
                isClickType = true;
                isClickTypeCount = index;
                //章节列表点击事件
                drawerLayout.closeDrawer(Gravity.LEFT);
                if (indexSection == index) {
                    bookDetailActRecyclerview.scrollToPosition(index);
                } else if (indexSection > index) {
                    bookDetailActRecyclerview.scrollToPosition(index);
                } else if (indexSection < index) {
                    recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING);
//                    bookDetailActRecyclerview.scrollToPosition(drawerRecyclerViewAdapter.getItemCount()-1);
                    bookDetailPresenter.getBookSectionContent(data.get(index).getId());
                    indexSection = index;
                }

            }
        });
        drawerRecyclerView.setLayoutManager(linearLayoutManager);
        drawerRecyclerView.setAdapter(drawerRecyclerViewAdapter);
    }

    private void getIntentBuild() {
        if (getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD) != null) {
            bookEntry = (BookEntry) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            bookDetailPresenter.getBookMessageToUser(bookEntry);
            bookDetailPresenter.getBookDetailSection(new Integer(bookEntry.getBid()));
            drawerTital.setText(bookEntry.getTitle() + "");
        }
    }

    @OnClick({R.id.book_detail_act_back, R.id.book_detail_act_more, R.id.book_detail_act_recyclerview,
            R.id.book_detail_act_catelogy, R.id.book_detail_act_setting, R.id.book_detail_act_comment,
            R.id.drawer_tital, R.id.drawer_count, R.id.drawer_recycler_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_detail_act_back:
                finish();
                break;
            case R.id.book_detail_act_more:
                //显示菜单
                if (showCatalogryType) {
                    showCatalogryType = false;
                    bookDetailActCatelogyLayout.setVisibility(View.INVISIBLE);
                    bookDetailActCheckbox.setVisibility(View.INVISIBLE);
                } else {
                    showCatalogryType = true;
                    bookDetailActCatelogyLayout.setVisibility(View.VISIBLE);
                    bookDetailActCheckbox.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.book_detail_act_catelogy:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.book_detail_act_setting:
                break;
            case R.id.book_detail_act_comment:
                //查看评论
                IntentSkip.startIntent(this, new CommentActivity(), commentActEntry);
                break;
            case R.id.drawer_tital:
                break;
            case R.id.drawer_count:
                break;
            case R.id.drawer_recycler_view:
                break;
        }
    }

    @Override
    public void getBookDetailSectionSec(ArrayList<BookSection> arrayList) {
        if (arrayList.size() > 0) {
//            Log.e("show" , arrayList.size() + "" + "");
            commentActEntry = new CommentActEntry(bookEntry.getId(), bookEntry.getTitle(), bookEntry.getDescription(), bookEntry.getAuthor(), bookEntry.getImages()
                    , bookEntry.getBid(), bookEntry.getType(), bookEntry.getClick_number(), bookEntry.getCreate_time(), arrayList.size());
            data.clear();
            data.addAll(arrayList);
            drawerCount.setText("共 " + arrayList.size() + " 章");
            drawerRecyclerViewAdapter.notifyDataSetChanged();
            bookDetailPresenter.getBookSectionContent(data.get(indexSection).getId());
            indexSection++;
        }
    }

    @Override
    public void getBookDetailSectionFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getBookSectionContentSec(BookSecTionContentEntry bookSecTionContentEntry) {
        recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING_COMPLETE);
        dataSecond.add(bookSecTionContentEntry);
        bookDetailRecyclerView.notifyDataSetChanged();
        recycleViewLoadWrapper.notifyDataSetChanged();
        if (isClickType) {
            isClickType = false;
            bookDetailActRecyclerview.scrollToPosition(bookDetailRecyclerView.getItemCount() - 1);
            ScrollView scrollView = bookDetailActRecyclerview.findViewById(R.id.book_detail_scrollview);
            scrollView.scrollTo(0, 160);
            indexSection++;
        }
    }


    @Override
    public void getBookSectionContentFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
