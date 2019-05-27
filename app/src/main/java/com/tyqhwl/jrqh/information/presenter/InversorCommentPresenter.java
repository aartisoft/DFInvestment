package com.tyqhwl.jrqh.information.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.base.BaseCalculate;
import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionEntry;
import com.tyqhwl.jrqh.information.view.AddInversorMyEntry;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class InversorCommentPresenter {


    public void getInversor(AddInversorMyEntry addInversorMyEntry , String commit){
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyAttention");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e ==null){
                    ArrayList<MyAttentionEntry> attentionEntries = new ArrayList<MyAttentionEntry>();
                    boolean isEntry = false;
                    String dataId = null;
                    AVUser avUser = new AVUser();
                    ArrayList<AttentionListEntry> als = new ArrayList<AttentionListEntry>();
                    for (AVObject avObject :list) {
                        String userId = avObject.getString("userId");
                        int post_id = avObject.getInt("post_id");

                        if (userId.equals(ApplicationStatic.getUserMessage().getObjectId()) && (post_id == addInversorMyEntry.postId)) {
                            isEntry = true;
                            dataId = avObject.getObjectId();
                            JSONArray al = avObject.getJSONArray("attentionList");
                            for (int i = 0 ; i< al.length() ; i++){
                                String userName = avObject.getString("userName");
                                String userComment = avObject.getString("userComment");
                                String userImage = avObject.getString("userImage");
                                als.add(new AttentionListEntry(userImage , userName , userComment));
                            }
                            als.add(new AttentionListEntry((String) avUser.get("icon")
                                    , ApplicationStatic.getUserMessage().getUsername()
                                    , commit));
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
                                    arrayList.add(new AttentionListEntry((String) avUser.get("icon")
                                            , ApplicationStatic.getUserMessage().getUsername()
                                            , commit));
                                }else {

                                    addInversorMyEntry.setAttentionList(arrayList);
                                }
                            }
                        });
                        //提交数据
                        getAddData(addInversorMyEntry);
                    }else {
                        Log.e("show" , "已收藏，请勿重复提交");
//                        addInversorMyCollectView.finishAwait();
//                        addInversorMyCollectView.getAddInversorMyFail("已收藏，请勿重复提交");
                        if (dataId != null){
                            //更新数据
                            AVObject avObject = AVObject.createWithoutData("MyAttention" , dataId);
                            avObject.put("attentionList" , als);
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null){
                                        Log.e("show" , "留言成功" + e.getMessage());
                                    }else {
                                        Log.e("show" , "关注留言失败" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                }else {
                    Log.e("show" , "提交失败ssss" + e.getMessage());
//                    addInversorMyCollectView.finishAwait();
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
//                    addInversorMyCollectView.getAddInversorMySuccess();
//                    addInversorMyCollectView.finishAwait();
                }else {
//                    addInversorMyCollectView.finishAwait();
                    Log.e("show" , "提交失败" + e.getMessage());
//                    addInversorMyCollectView.getAddInversorMyFail(e.getMessage());
                }
            }
        });
    }

}
