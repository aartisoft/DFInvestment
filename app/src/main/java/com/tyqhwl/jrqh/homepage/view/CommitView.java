package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface CommitView extends BaseView {
    void getCommitDataSuccess(ArrayList<CommitEntry> data);
    void getCommitDataFail(String msg);

    void getPushUseCommitSuccess();
    void getPushUseCommitFail(String msg);

    void getAddRackSuccess();
    void getAddRackFail(String msg);
}
