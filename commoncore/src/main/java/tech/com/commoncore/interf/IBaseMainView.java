package tech.com.commoncore.interf;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;


import java.util.List;

import tech.com.commoncore.entity.BaseTabEntity;

/**
 * @Author: AriesHoo on 2018/7/20 17:07
 * @E-Mail: AriesHoo@126.com
 * Function: 包含CommonTabLayout的主页面Activity/Fragment
 * Description:
 */
public interface IBaseMainView extends OnTabSelectListener {

    /**
     * 控制主界面Fragment是否可滑动切换
     *
     * @return true 可滑动切换(配合ViewPager)
     */
    boolean isSwipeEnable();

    /**
     * 用于添加Tab属性(文字-图标)
     *
     * @return 主页tab数组
     */
    @Nullable
    List<BaseTabEntity> getTabList();

    /**
     * 返回 CommonTabLayout  对象用于自定义设置
     *
     * @param tabLayout CommonTabLayout 对象用于单独属性调节
     */
    void setTabLayout(CommonTabLayout tabLayout);

    /**
     * 设置ViewPager属性
     *
     * @param mViewPager ViewPager属性控制
     */
    void setViewPager(ViewPager mViewPager);
}
