package tech.com.commoncore.entity;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.flyco.tablayout.listener.CustomTabEntity;

import tech.com.commoncore.app.FastManager;

/**
 * @Author: AriesHoo on 2018/7/16 9:16
 * @E-Mail: AriesHoo@126.com
 * Function: 主页Tab实体类
 * Description:
 * 1、2018-7-27 17:45:45 修改重载方式
 */
public class BaseTabEntity implements CustomTabEntity {
    public String mTitle;
    public int mSelectedIcon;
    public int mUnSelectedIcon;
    public Fragment mFragment;

    public BaseTabEntity(String title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnSelectedIcon = unSelectedIcon;
        this.mFragment = fragment;
    }

    public BaseTabEntity(int title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this(FastManager.getInstance().getApplication().getString(title), unSelectedIcon, selectedIcon, fragment);
    }

    public BaseTabEntity(int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this("", unSelectedIcon, selectedIcon, fragment);
    }

    @Override
    public String getTabTitle() {
        if (TextUtils.isEmpty(mTitle)) {
            return "";
        }
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnSelectedIcon;
    }
}
