package com.tyqhwl.jrqh.information.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.base.BaseCalculate;
import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionEntry;
import com.tyqhwl.jrqh.information.view.AddInversorMyCollectView;
import com.tyqhwl.jrqh.information.view.AddInversorMyEntry;
import java.util.ArrayList;
import java.util.List;

public class AddInversorMyCollectPresenter{

    private AddInversorMyCollectView addInversorMyCollectView;

    public AddInversorMyCollectPresenter(AddInversorMyCollectView addInversorMyCollectView) {
        this.addInversorMyCollectView = addInversorMyCollectView;
    }

    //收藏
    public void getAddInversorMyCollect(AddInversorMyEntry addInversorMyEntry){
        addInversorMyCollectView.showAwait();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyAttention");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e ==null){
                    ArrayList<MyAttentionEntry> attentionEntries = new ArrayList<MyAttentionEntry>();
                    boolean isEntry = false;
                    for (AVObject avObject :list) {
                        String userId = avObject.getString("userId");
                        int post_id = avObject.getInt("post_id");
                        if (userId.equals(ApplicationStatic.getUserMessage().getObjectId()) && (post_id == addInversorMyEntry.postId)) {
                            isEntry = true;
                        }
                    }
                        if (!isEntry){
                            //随机获取4条评论信息
                            AVQuery<AVObject> avQuery = new AVQuery<>("comment");
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    ArrayList<AttentionListEntry> arrayList = new ArrayList<AttentionListEntry>();
                                    if(e == null){

                                        int[] bc = BaseCalculate.testC(list.size());
                                        for (int i = 0 ; i < 4 ; i++){
                                            AVObject avObject = list.get(bc[i]);
                                            String userId = avObject.getString("userId");
                                            String userName = avObject.getString("userName");
                                            String userComment = avObject.getString("userComment");
                                            String userImage = avObject.getString("userImage");
                                            arrayList.add(new AttentionListEntry(userImage , userName , userComment));
                                        }
                                    }else {
                                        Log.e("show"  , e.getMessage());
                                        addInversorMyEntry.setAttentionList(arrayList);
                                    }
                                }
                            });
                            //提交数据
                            getAddData(addInversorMyEntry);
                        }else {
                            Log.e("show" , "已收藏，请勿重复提交");
                            addInversorMyCollectView.finishAwait();
                            addInversorMyCollectView.getAddInversorMyFail("已收藏，请勿重复提交");

                        }
                }else {
                    Log.e("show" , "提交失败ssss" + e.getMessage());
                    addInversorMyCollectView.finishAwait();
                }
            }
        });
    }


    //提交到服务器
    private void getAddData(AddInversorMyEntry addInversorMyEntry){
        AVObject avObject = new AVObject("MyAttention");
        avObject.put("thumb" , addInversorMyEntry.thumb);
        avObject.put("read" , addInversorMyEntry.read);
        avObject.put("time" , addInversorMyEntry.time);
        avObject.put("tital" , addInversorMyEntry.tital);
        avObject.put("summary" , addInversorMyEntry.summary);
        avObject.put("post_id" , addInversorMyEntry.postId);
        avObject.put("userId" , ApplicationStatic.getUserMessage().getObjectId());
        avObject.put("attentionList" , addInversorMyEntry.attentionList);
        avObject.put("userImage" , addInversorMyEntry.userImage);
        avObject.put("countImage" , addInversorMyEntry.contentImage);
        avObject.put("userName" , addInversorMyEntry.userName);
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    Log.e("show" , "提交成功");
                    addInversorMyCollectView.getAddInversorMySuccess();
                    addInversorMyCollectView.finishAwait();
                }else {
                    addInversorMyCollectView.finishAwait();
                    Log.e("show" , "提交失败" + e.getMessage());
                    addInversorMyCollectView.getAddInversorMyFail(e.getMessage());
                }
            }
        });
    }

}
