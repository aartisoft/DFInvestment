package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.base.BaseCalculate;
import com.tyqhwl.jrqh.homepage.view.CommentActEntry;
import com.tyqhwl.jrqh.homepage.view.CommitEntry;
import com.tyqhwl.jrqh.homepage.view.CommitView;
import java.util.ArrayList;
import java.util.List;

public class CommitPresenter {

    private CommitView commitView;

    public CommitPresenter(CommitView commitView) {
        this.commitView = commitView;
    }

    //获取所有评论
    public void getCommitData(){
        AVQuery<AVObject> avQuery = new AVQuery<>("comment");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
//                    Log.e("show"  ,list.toString());
                    ArrayList<CommitEntry> arrayList = new ArrayList<CommitEntry>();
                    int[] bc = BaseCalculate.testC(list.size());
                    for (int i = 0 ; i < list.size() ; i++){
                        AVObject avObject = list.get(bc[i]);
                        String userId = avObject.getString("userId");
                        String userName = avObject.getString("userName");
                        String userComment = avObject.getString("userComment");
                        String userImage = avObject.getString("userImage");
                        arrayList.add(new CommitEntry(userId , userName , userComment , userImage));
                    }

                    commitView.getCommitDataSuccess(arrayList);

                }else {
                    Log.e("show"  , e.getMessage());
                    commitView.getCommitDataFail(e.getMessage());
                }
            }
        });
    }

    //提交当前用户评论
    public void pushUserCommit(String commit){
        commitView.showAwait();
        //更新现有数据（用户列表）
        AVUser avUser = AVUser.getCurrentUser();
        AVObject todoFolder = new AVObject("comment");// 构建对象
        todoFolder.put("userId" , ApplicationStatic.getUserMessage().getObjectId());
        todoFolder.put("userName" ,ApplicationStatic.getUserMessage().getUsername() );
        todoFolder.put("userComment" , commit);
        todoFolder.put("userImage" , avUser.get("icon") != null ? avUser.get("icon"): null);
        todoFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e== null){
                    commitView.getPushUseCommitSuccess();
                    commitView.finishAwait();
                }else {
                    Log.e("show" , e.getMessage());
                    commitView.getPushUseCommitFail(e.getMessage());
                    commitView.finishAwait();
                }
            }
        });// 保存到服务端
    }

    //将书籍加入到自己书架
    public void getAddBookRack(CommentActEntry commentActEntry){
        AVObject avObject = new AVObject("MyBookRack");
        avObject.put("id" , commentActEntry.getId());
        avObject.put("title" , commentActEntry.getTitle());
        avObject.put("description" , commentActEntry.getDescription());
        avObject.put("author" , commentActEntry.getAuthor());
        avObject.put("images" , commentActEntry.getImages());
        avObject.put("bid" , commentActEntry.getBid());
        avObject.put("type" , commentActEntry.getType());
        avObject.put("click_number" , commentActEntry.getClick_number());
        avObject.put("create_time" , commentActEntry.getCreate_time());
        avObject.put("sectionCount" , commentActEntry.getSectionCount());
        avObject.put("userId" , ApplicationStatic.getUserMessage().getObjectId());
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    commitView.getAddRackSuccess();
                    commitView.finishAwait();
                }else {
                    commitView.getAddRackFail(e.getMessage());
                    commitView.finishAwait();
                }
            }
        });
    }

    //获取当前书籍中的所有图书
    public void getBookRackData(CommentActEntry commentActEntry){
        commitView.showAwait();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyBookRack");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    boolean isEntry = false;
                    for (AVObject avObject : list){
                        String id = avObject.getString("id");
                        String userId = avObject.getString("userId");
                        if (id.equals(commentActEntry.getId()) && userId.equals(ApplicationStatic.getUserMessage().getObjectId())){
                            isEntry = true;
                        }
                    }

                    if (!isEntry){
                        getAddBookRack(commentActEntry);
                    }else {
                        commitView.finishAwait();
                        commitView.getAddRackFail("已添加到书架，请勿重复添加");
                    }
                }else {
                    Log.e("show" , e.getMessage());
                    commitView.finishAwait();
                    commitView.getAddRackFail(e.getMessage());
                }
            }
        });
    }
}
