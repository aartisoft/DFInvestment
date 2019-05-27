package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.adapter.SeekRecyclerViewAdapter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索页面
 * wmy
 * 2019-05-27
 */
public class SeekActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.seek_act_edittext)
    EditText seekActEdittext;
    @BindView(R.id.seek_act_button)
    TextView seekActButton;
    @BindView(R.id.book_mall_seek)
    LinearLayout bookMallSeek;
    @BindView(R.id.book_mall_seek_layout)
    LinearLayout bookMallSeekLayout;
    @BindView(R.id.book_mall_tital)
    RelativeLayout bookMallTital;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.entry_layout)
    RelativeLayout entryLayout;
    private ArrayList<BookEntry> arrayList;
    private ArrayList<BookEntry> data = new ArrayList<>();
    private SeekRecyclerViewAdapter seekRecyclerViewAdapter;

    @Override
    public int getXMLLayout() {
        return R.layout.seek_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
        initRecyclerView();
//        initSeek("");
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        seekRecyclerViewAdapter = new SeekRecyclerViewAdapter(data, this, new SeekRecyclerViewAdapter.OnClickListenerAdapter() {
            @Override
            public void onClick(int index) {
                //点击事件
                //跳转到书籍详情页面
                IntentSkip.startIntent(SeekActivity.this, new BookDetailActivity(), data.get(index));
            }
        });
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(seekRecyclerViewAdapter);
    }

    private void init() {
        arrayList = ApplicationStatic.getBookAllData();

    }

    @OnClick({R.id.back, R.id.seek_act_edittext, R.id.seek_act_button,
            R.id.book_mall_seek, R.id.book_mall_seek_layout,
            R.id.book_mall_tital, R.id.recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.seek_act_edittext:
                break;
            case R.id.seek_act_button:
                //搜索
                if (seekActEdittext.getText().toString().length() > 0) {
                    initSeek(seekActEdittext.getText().toString());
                } else {
                    Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.book_mall_seek:
                break;
            case R.id.book_mall_seek_layout:
                break;
            case R.id.book_mall_tital:
                break;
            case R.id.recyclerview:
                break;
        }
    }

    private void initSeek(String edittextText) {
        data.clear();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                boolean ii = arrayList.get(i).getTitle().contains(edittextText);
                if (ii == true) {
                    data.add(arrayList.get(i));
                }
            }
            seekRecyclerViewAdapter.notifyDataSetChanged();
        }
        if (data.size() <= 0) {
            entryLayout.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.INVISIBLE);
        } else {
            entryLayout.setVisibility(View.INVISIBLE);
            recyclerview.setVisibility(View.VISIBLE);
        }

    }
}
