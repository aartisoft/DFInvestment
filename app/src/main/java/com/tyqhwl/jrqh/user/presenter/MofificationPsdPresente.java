package com.tyqhwl.jrqh.user.presenter;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.tyqhwl.jrqh.user.view.ModificationPsdView;

public class MofificationPsdPresente {

    private ModificationPsdView modificationPsdView;


    public MofificationPsdPresente(ModificationPsdView modificationPsdView) {
        this.modificationPsdView = modificationPsdView;
    }

    //修改密码
    public void updateUserPsd(String oldPsd , final String newPsd){
        modificationPsdView.showAwait();
        AVUser.getCurrentUser().updatePasswordInBackground(oldPsd, newPsd, new UpdatePasswordCallback() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void done(AVException e) {
                if (e == null){
                    modificationPsdView.getModificationPsdSuccess();
//                    SharedPreferences sharedPreferences = context.getSharedPreferences("userLogin" , Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                    editor.putString("name" , userName);
//                    editor.putString("password" , newPsd);
//                    editor.commit();
                }else {
                    modificationPsdView.getModificationPsdFail(e.getMessage());
                }
                modificationPsdView.finishAwait();
            }
        });
    }


}
