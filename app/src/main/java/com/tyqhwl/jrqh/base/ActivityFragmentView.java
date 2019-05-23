package com.tyqhwl.jrqh.base;

/**
 * 有梯子固然是好的，但是会使用梯子之前要学会自己造梯子
 * wmy
 * 2019-03-12
 *
 */
public interface ActivityFragmentView {
    //监听eventbus以及黄油刀是否初始化
    boolean isEventOrBindInit();

    //activity与fragment xml视图
    int getXMLLayout();

    //组件初始化
    void initView();
}
