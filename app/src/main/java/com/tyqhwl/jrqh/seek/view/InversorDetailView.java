package com.tyqhwl.jrqh.seek.view;

import com.tyqhwl.jrqh.base.BaseView;

public interface InversorDetailView extends BaseView {
    void getInversorDetailSuccess(InversorDetailEntry inversorDetailEntry);
    void getInversorDetailFail(String msg);
}
