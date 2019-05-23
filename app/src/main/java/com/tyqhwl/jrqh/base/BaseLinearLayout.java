package com.tyqhwl.jrqh.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class BaseLinearLayout extends LinearLayoutManager {

    private boolean aBoolean = true;

    public BaseLinearLayout(Context context) {
        super(context);
    }

    public BaseLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnable(boolean enable){
        aBoolean = enable;
    }

    @Override
    public boolean canScrollVertically() {
        return aBoolean && super.canScrollVertically();
    }
}
