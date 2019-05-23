package com.tyqhwl.jrqh.user.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.user.view.NoticeEntrys;
import com.tyqhwl.jrqh.user.view.NoticeView;

import java.util.ArrayList;
import java.util.List;

public class NoticePresenter {


    private NoticeView noticeView;


    public NoticePresenter(NoticeView noticeView) {
        this.noticeView = noticeView;
    }

    public void getNoticeDate() {
        noticeView.showAwait();
        AVQuery<AVObject> avQuery = new AVQuery<>("Notice");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<NoticeEntrys> arrayList = new ArrayList<>();
                    for (AVObject avObject : list) {
                        String title = avObject.getString("title");
                        String dateTime = avObject.getString("dateTime");
                        String context = avObject.getString("context");
                        arrayList.add(new NoticeEntrys(title , dateTime , context));
                    }

                    noticeView.getNoticeSuccess(arrayList);
                    noticeView.finishAwait();
                } else {
                    noticeView.getNoticeFail(e.getMessage());
                    noticeView.finishAwait();
                }
            }
        });
    }

}
