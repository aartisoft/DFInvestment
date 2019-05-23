package com.tyqhwl.jrqh.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.login.view.LoginView;
import com.tyqhwl.jrqh.user.view.FeedBackView;
import com.tyqhwl.jrqh.user.view.ModificationPsd;
import com.tyqhwl.jrqh.user.view.UserView;


public class LoginHttps {

    private LoginView loginView;
    private Context context;
    private UserView userView;
    private ModificationPsd modificationPsd;
    private FeedBackView feedBackView;

    //用户登录
    public LoginHttps(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
    }

    //用户修改相关数据
    public LoginHttps(UserView userView , Context context){
        this.userView = userView;
        this.context = context;
    }

    //修改密码
    public LoginHttps(Context context, ModificationPsd modificationPsd) {
        this.context = context;
        this.modificationPsd = modificationPsd;
    }

    //意见反馈
    public LoginHttps(FeedBackView feedBackView, Context context) {
        this.feedBackView = feedBackView;
        this.context = context;
    }

    //登录
    public void getLoginData(final String userName, final String password) {
        loginView.showAwait();
//        AVOSCloud.initialize(context, HttpApiConfig.APP_ID, HttpApiConfig.APP_KEY);
        AVUser.loginByMobilePhoneNumberInBackground(userName, password,
                new LogInCallback<AVUser>() {
                    @SuppressLint("ApplySharedPref")
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            ApplicationStatic.setUserMessage(avUser);
                            SharedPreferences sharedPreferences = context.getSharedPreferences("userLogin" , Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name" , userName);
                            editor.putString("password" , password);
                            editor.commit();
                            loginView.loginSuccess(avUser);
                        } else {
                            loginView.loginFail("用户名或密码错误");
                        }
                        loginView.finishAwait();
                    }
                }
        );

    }

    //注册
    public void getRegisterData(final String userName, final String password, String verification_code) {

        AVUser.signUpOrLoginByMobilePhoneInBackground(userName, verification_code, new LogInCallback<AVUser>() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void done(AVUser avUser, AVException e) {
                // 如果 e 为空就可以表示登录成功了，并且 user 是一个全新的用户
                if (e == null) {
                    //注册成功之后更新用户密码（使用用户名作为手机号）
                    setPw(avUser, password);
                    ApplicationStatic.setUserMessage(avUser);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("userLogin" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name" , userName);
                    editor.putString("password" , password);
                    editor.commit();
                    loginView.loginSuccess(avUser);
                } else {
                    Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    /**
     * 保存密码
     *
     * @param user
     * @param password
     */
    private void setPw(AVUser user, String password) {
        loginView.showAwait();
        user.updatePasswordInBackground(user.get("password") + "", password, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    loginView.registerSuccess();
                } else {
                    loginView.registerFail(e.getMessage());
                }
                loginView.finishAwait();
            }
        });

    }

    //发送验证码
    public void sendVerificationCode(String phone) {
//        AVOSCloud.initialize(context, HttpApiConfig.APP_ID, HttpApiConfig.APP_KEY);
        AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    /* 请求成功 */
                    Toast.makeText(context, "验证码发送成功", Toast.LENGTH_LONG).show();
                } else {
                    /* 请求失败 */
                    Log.e("show", e.getMessage());
                    Toast.makeText(context, "获取验证码失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //密码找回(重置密码)
    public void findPassword(String verification_code, final String password) {
        loginView.showAwait();
        AVUser.resetPasswordBySmsCodeInBackground(verification_code, password, new UpdatePasswordCallback() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void done(AVException e) {
                if (e == null) {
                    loginView.findPasswordSuccess();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userLogin" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password" , password);
                    editor.commit();
                } else {
                    loginView.findPasswordFail(e.getMessage());
                }
                loginView.finishAwait();
            }
        });
    }

    //保存用户信息
    public void saveUserMessage(final String userName , final String signature , final int sex){
        userView.showAwait();
        //更新现有数据（用户列表）
        AVObject todoFolder = AVObject.createWithoutData("_User" , ApplicationStatic.getUserMessage().getObjectId());// 构建对象
        todoFolder.put("username" , userName);
        todoFolder.put("signature" ,signature );
        todoFolder.put("sex" , sex);
        todoFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                   userView.saveUserMessageSuccess();
                }else {
                    userView.saveUserMessageFail(e.getMessage());
                }
                userView.finishAwait();
            }
        });// 保存到服务端
    }

    //修改密码
    public void updateUserPsd(String oldPsd , final String newPsd){
        modificationPsd.showAwait();
        AVUser.getCurrentUser().updatePasswordInBackground(oldPsd, newPsd, new UpdatePasswordCallback() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void done(AVException e) {
                if (e == null){
                    modificationPsd.updateUserPsdSuccess();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userLogin" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("name" , userName);
                    editor.putString("password" , newPsd);
                    editor.commit();
                }else {
                    modificationPsd.updateUserPsdFail(e.getMessage());
                }
                modificationPsd.finishAwait();
            }
        });
    }

    //意见反馈
    public void feedBack(String feedback , String serverImageId){
        feedBackView.showAwait();
        AVObject avObject = new AVObject("FeedBack");
        avObject.put("feedback" , feedback);
        avObject.put("username" , ApplicationStatic.getUserMessage().getUsername());
        avObject.put("phonenumber" , ApplicationStatic.getUserMessage().getMobilePhoneNumber());
        avObject.put("serverimageid" ,serverImageId);
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null){
                    feedBackView.getFeedBackSuccess();
                }else {
                    feedBackView.getFeedBackFail(e.getMessage());
                }
                feedBackView.finishAwait();
            }
        });
    }


}
