package com.tyqhwl.jrqh.user.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.user.view.UserView;

public class ModificationUserNamePresenter {


    private UserView userView;


    public ModificationUserNamePresenter(UserView userView) {
        this.userView = userView;
    }

    //保存用户信息
    public void saveUserMessage(final String userName ){
        userView.showAwait();
        //更新现有数据（用户列表）
        AVObject todoFolder = AVObject.createWithoutData("_User" , ApplicationStatic.getUserMessage().getObjectId());// 构建对象
        todoFolder.put("username" , userName);
        todoFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    getUserMessage();
                }else {
                    userView.saveUserMessageFail(e.getMessage());
                }
                userView.finishAwait();
            }
        });// 保存到服务端
    }


    //获取用户信息
    public void getUserMessage(){
        AVQuery<AVUser> avObjectAVQuery = new AVQuery<>("_User");
        avObjectAVQuery.getInBackground(ApplicationStatic.getUserMessage().getObjectId(), new GetCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null){
                    ApplicationStatic.setUserMessage(avUser);
                    userView.saveUserMessageSuccess();
                }else {
//                    Log.e("show" , e.getMessage());
                    userView.saveUserMessageFail(e.getMessage());
                }
            }
        });
    }

}
