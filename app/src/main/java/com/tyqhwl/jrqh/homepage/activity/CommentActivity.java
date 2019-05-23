package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.base.RecycleViewLoadWrapper;
import com.tyqhwl.jrqh.homepage.adapter.CommitAdapter;
import com.tyqhwl.jrqh.homepage.presenter.CommitPresenter;
import com.tyqhwl.jrqh.homepage.view.CommentActEntry;
import com.tyqhwl.jrqh.homepage.view.CommitEntry;
import com.tyqhwl.jrqh.homepage.view.CommitView;
import com.tyqhwl.jrqh.homepage.view.WriteCommentaryEntry;
import com.tyqhwl.jrqh.login.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书籍评论页面
 */

public class CommentActivity extends BaseActivity implements CommitView {
    @BindView(R.id.book_detail_act_back)
    ImageView bookDetailActBack;
    @BindView(R.id.book_detail_image)
    ImageView bookDetailImage;
    @BindView(R.id.book_detail_tital)
    TextView bookDetailTital;
    @BindView(R.id.read_comment)
    LinearLayout readComment;
    @BindView(R.id.comment_act_recycler_view)
    RecyclerView commentActRecyclerView;
    @BindView(R.id.add_bookrack)
    TextView addBookrack;
    @BindView(R.id.reading_immediately)
    TextView readingImmediately;
    @BindView(R.id.comment_act_introduce)
    TextView commentActIntroduce;
    @BindView(R.id.comment_act_section)
    TextView commentActSection;
    @BindView(R.id.comment_act_catagory)
    LinearLayout commentActCatagory;
    private CommitPresenter commitPresenter = new CommitPresenter(this);
    private ArrayList<CommitEntry> arrayList = new ArrayList<>();
    private CommitAdapter commitAdapter;
    private RecycleViewLoadWrapper recycleViewLoadWrapper;
    private CommentActEntry bookEntry;
    private AwaitDialog awaitDialog;
    @Override
    public int getXMLLayout() {
        return R.layout.comment_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o instanceof WriteCommentaryEntry){
            AVUser avUser = AVUser.getCurrentUser();
            arrayList.add(0 , new CommitEntry(ApplicationStatic.getUserMessage().getObjectId() , ApplicationStatic.getUserMessage().getUsername()
            ,((WriteCommentaryEntry) o).getCommit() , avUser.get("icon") != null ? (String) avUser.get("icon") : null));
            commitAdapter.notifyDataSetChanged();
            recycleViewLoadWrapper.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getIntentBuild();
        commitPresenter.getCommitData();
        initRecyclerView();
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commitAdapter = new CommitAdapter(arrayList, this);
        recycleViewLoadWrapper = new RecycleViewLoadWrapper(commitAdapter, new RecycleViewLoadWrapper.EntryClickListener() {
            @Override
            public void Click() {
                Toast.makeText(CommentActivity.this, "重新获取数据...", Toast.LENGTH_SHORT).show();
                commitPresenter.getCommitData();
            }
        });
        commentActRecyclerView.setLayoutManager(linearLayoutManager);
        commentActRecyclerView.setAdapter(recycleViewLoadWrapper);
    }

    private void getIntentBuild() {
        if (getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD) != null) {
            bookEntry = (CommentActEntry) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            Glide.with(this)
                    .load(bookEntry.getImages())
                    .apply(new RequestOptions().placeholder(R.drawable.tu_jiazai).error(R.drawable.tu_wuzhuangtai))
                    .into(bookDetailImage);
            commentActIntroduce.setText(bookEntry.getDescription() + "");
            commentActSection.setText("共 " + bookEntry.getSectionCount() + " 章");
        }
    }

    @OnClick({R.id.book_detail_act_back, R.id.book_detail_image, R.id.book_detail_tital,
            R.id.read_comment, R.id.comment_act_recycler_view, R.id.add_bookrack, R.id.reading_immediately})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_detail_act_back:
                finish();
                break;
            case R.id.book_detail_image:
                break;
            case R.id.book_detail_tital:
                break;
            case R.id.read_comment:
                //写评论
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(this , new WriteCommentaryActivity() , null);
                }else {
                    IntentSkip.startIntent(this , new LoginActivity() , null);
                }
                break;
            case R.id.comment_act_recycler_view:
                break;
            case R.id.add_bookrack:
                //添加到书架
                if (ApplicationStatic.getUserLoginState()){
                    commitPresenter.getBookRackData(bookEntry);
                }else {
                    IntentSkip.startIntent(this , new LoginActivity() , null);
                }
                break;
            case R.id.reading_immediately:
                //继续阅读
                finish();
                break;
        }
    }

    @Override
    public void getCommitDataSuccess(ArrayList<CommitEntry> data) {
        arrayList.clear();
        arrayList.addAll(data);
        commitAdapter.notifyDataSetChanged();
        recycleViewLoadWrapper.notifyDataSetChanged();
    }

    @Override
    public void getCommitDataFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPushUseCommitSuccess() {
        //发表评论成功
    }

    @Override
    public void getPushUseCommitFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAddRackSuccess() {
        Toast.makeText(this, "添加书架成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAddRackFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(this, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }

    @OnClick(R.id.comment_act_catagory)
    public void onViewClicked() {
        //显示目录
        EventBus.getDefault().post(EventBusTag.SHOW_BOOK_SECTION);
        finish();
    }
}
