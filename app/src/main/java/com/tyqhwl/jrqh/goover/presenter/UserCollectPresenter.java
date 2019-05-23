package com.tyqhwl.jrqh.goover.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.goover.view.UserCollectEntry;
import com.tyqhwl.jrqh.goover.view.UserCollectView;

import java.util.ArrayList;
import java.util.List;

public class UserCollectPresenter {


    private UserCollectView userCollectView;


    public UserCollectPresenter(UserCollectView userCollectView) {
        this.userCollectView = userCollectView;
    }

    public void getUserCollectData(){
        userCollectView.showAwait();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("CollectNewMessage");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    ArrayList<UserCollectEntry> arrayList = new ArrayList();
                    for (AVObject avObject : list){
                        String userObjectId = avObject.getString("userId");
                        if (userObjectId.equals(ApplicationStatic.getUserMessage().getObjectId())){
                            String read = avObject.getString("read");
                            String content = avObject.getString("content");
                            String time = avObject.getString("time");
                            String title = avObject.getString("title");
                            String imageUrl = avObject.getString("imageUrl");
                            int post_Id = avObject.getInt("post_id");
                            arrayList.add(new UserCollectEntry(read , content , time , title , imageUrl , post_Id , userObjectId));
                        }
                    }
                    userCollectView.getUserCollectSuccess(arrayList);
                    userCollectView.finishAwait();
                }else {
                    Log.e("show" , e.getMessage());
                    userCollectView.getUserCollectFail(e.getMessage());
                    userCollectView.finishAwait();
                }
            }
        });
    }



    //删除用户收藏数据
    public void deleteuserNewMessage(int postId) {
//        userCollectView.showAwait();
        //获取所有数据
        AVQuery<AVObject> query = new AVQuery<>("CollectNewMessage");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    boolean isData = false;
                    String objectId = null;
                    //便利数组，获取数据相同postid,进行删除
                    for (AVObject avObject : list) {
                        int postIdAB = avObject.getInt("post_id");
                        if (postIdAB == postId) {
                            isData = true;
                            objectId = avObject.getObjectId();
                            if (isData && objectId != null) {
                                //删除数据
                                AVObject avObjects = AVObject.createWithoutData("CollectNewMessage", objectId);
                                avObjects.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        // 如果 e 为空，说明操作成功
                                        if (e == null) {
//                                    Log.e("show" , "删除成功");
                                            userCollectView.getCancleCollectSuccess();
//                                            userCollectView.finishAwait();
                                        } else {
                                            userCollectView.getCancleCollectFail(e.getMessage());
//                                            userCollectView.finishAwait();
                                        }
                                    }
                                });
                            }

                        }
                    }
//                    userCollectView.finishAwait();
                } else {
//                    Log.e("show" , e.getMessage());
//                    userCollectView.finishAwait();
                    userCollectView.getCancleCollectFail(e.getMessage());
                }
            }
        });
    }


}
