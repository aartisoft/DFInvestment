package com.tyqhwl.jrqh.seek.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EndlessRecyclerOnScrollListener;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.base.NewMessage;
import com.tyqhwl.jrqh.base.RecycleViewLoadWrapper;
import com.tyqhwl.jrqh.seek.activity.InversorDetailActivity;
import com.tyqhwl.jrqh.seek.activity.ShowNewMessage;
import com.tyqhwl.jrqh.seek.adapter.SeekGuessYouLikeAdapter;
import com.tyqhwl.jrqh.seek.presenter.InversorPresenter;
import com.tyqhwl.jrqh.seek.view.InversorDetailSer;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import com.tyqhwl.jrqh.seek.view.InversorView;
import com.tyqhwl.jrqh.seek.view.NewMessageSerilize;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 发现首页
 * wmy
 * 2019-04-11
 */
public class SeekFragment extends BaseFragment implements InversorView {

    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.sign_in)
    LinearLayout signIn;

    @BindView(R.id.guess_what_you_like)
    RecyclerView guessWhatYouLike;
    Unbinder unbinder;
    @BindView(R.id.seek_frag_first_text)
    TextView seekFragFirstText;
    @BindView(R.id.seek_frag_first_image)
    ImageView seekFragFirstImage;
    @BindView(R.id.seek_frag_second_text)
    TextView seekFragSecondText;
    @BindView(R.id.seek_frag_second_image)
    ImageView seekFragSecondImage;
    @BindView(R.id.seek_frag_thread_text)
    TextView seekFragThreadText;
    @BindView(R.id.seek_frag_thread_image)
    ImageView seekFragThreadImage;
    @BindView(R.id.seek_frag_four_text)
    TextView seekFragFourText;
    @BindView(R.id.seek_frag_four_image)
    ImageView seekFragFourImage;

    private int page = 1;//分页加载
    private String tags = "ag";//显示标签分组
    private InversorPresenter inversorPresenter = new InversorPresenter(this);

    private SeekGuessYouLikeAdapter seekGuessYouLikeAdapter;
    private RecycleViewLoadWrapper recycleViewLoadWrapper;
    private ArrayList<InversorEntry> arrayList = new ArrayList<>();

    public static SeekFragment newInstance() {

        Bundle args = new Bundle();

        SeekFragment fragment = new SeekFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.seek_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        inversorPresenter.getInversorData(tags, page);
        initRecy();
        return rootView;
    }

    private void initRecy() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        seekGuessYouLikeAdapter = new SeekGuessYouLikeAdapter(getActivity(), arrayList, new SeekGuessYouLikeAdapter.SeekGuessYouLikeListener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity() , new InversorDetailActivity() , new InversorDetailSer(arrayList.get(index).post_id));
            }
        } , getActivity());
        recycleViewLoadWrapper = new RecycleViewLoadWrapper(seekGuessYouLikeAdapter, new RecycleViewLoadWrapper.EntryClickListener() {
            @Override
            public void Click() {
                //网络连接失败，点击重新获取
                page = 0;
                Toast.makeText(getActivity(), "重新获取数据。。。", Toast.LENGTH_SHORT).show();
                inversorPresenter.getInversorData(tags, page);

            }
        });
        guessWhatYouLike.setLayoutManager(linearLayoutManager);
        guessWhatYouLike.setAdapter(recycleViewLoadWrapper);
        guessWhatYouLike.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING);
                inversorPresenter.getInversorData(tags, page);
            }

            @Override
            public void onPullDown(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.homepage_frag_sign_in)
    public void onViewClicked() {
    }

    @Override
    public void getInversorSuccess(ArrayList<InversorEntry> data) {
        if (data.size() > 0) {
            recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING_COMPLETE);
            arrayList.addAll(data);
            seekGuessYouLikeAdapter.notifyDataSetChanged();
            recycleViewLoadWrapper.notifyDataSetChanged();
        } else {
            recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING_END);
        }
    }

    @Override
    public void getInversorFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }

    @OnClick({R.id.seek_frag_first_text, R.id.seek_frag_first_image, R.id.seek_frag_second_text, R.id.seek_frag_second_image, R.id.seek_frag_thread_text, R.id.seek_frag_thread_image, R.id.seek_frag_four_text, R.id.seek_frag_four_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seek_frag_first_text:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("揭秘期货的奥秘" , NewMessage.RUDIMENT_SEX));
                break;
            case R.id.seek_frag_first_image:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("揭秘期货的奥秘" , NewMessage.RUDIMENT_SEX));
                break;
            case R.id.seek_frag_second_text:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("期货大赢家关注的" , NewMessage.RUDIMENT_SEVER));
                break;
            case R.id.seek_frag_second_image:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("期货大赢家关注的" , NewMessage.RUDIMENT_SEVER));
                break;
            case R.id.seek_frag_thread_text:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("期货购入原则" , NewMessage.RUDIMENT_SECOND));
                break;
            case R.id.seek_frag_thread_image:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("期货购入原则" , NewMessage.RUDIMENT_SECOND));
                break;
            case R.id.seek_frag_four_text:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("股指期货与原油期" , NewMessage.RUDIMENT_FIRST));
                break;
            case R.id.seek_frag_four_image:
                IntentSkip.startIntent(getActivity(), new ShowNewMessage() , new NewMessageSerilize("股指期货与原油期" , NewMessage.RUDIMENT_FIRST));
                break;
        }
    }
}
