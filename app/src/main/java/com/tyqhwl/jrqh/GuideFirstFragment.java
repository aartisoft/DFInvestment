package com.tyqhwl.jrqh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tech.com.commoncore.base.BaseFragment;

public class GuideFirstFragment extends BaseFragment {
    @BindView(R.id.item_first)
    ImageView itemThread;
    Unbinder unbinder;

    @Override
    public int getContentLayout() {
        return R.layout.item01;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Glide.with(this)
                .load(R.drawable.start_second)
                .into(itemThread);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
