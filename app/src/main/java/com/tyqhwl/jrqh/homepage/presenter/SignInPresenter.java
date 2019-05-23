package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.homepage.view.SignInEntry;
import com.tyqhwl.jrqh.homepage.view.SignInView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SignInPresenter {

    private SignInView signInView;

    public SignInPresenter(SignInView signInView) {
        this.signInView = signInView;
    }

    public void getSignInData() {
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("SignIn");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<SignInEntry> arrayList = new ArrayList<SignInEntry>();
                    String userId = ApplicationStatic.getUserMessage().getObjectId();
                    for (AVObject avObject : list) {
                        if (avObject.getString("userId").equals(userId)) {
//                            avObject.getJSONArray("signInList");
                            ArrayList<String> data = new ArrayList<>();
                            for (int i = 0; i < avObject.getJSONArray("signInList").length(); i++) {
                                try {
                                    data.add((String) avObject.getJSONArray("signInList").get(i));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            arrayList.add(new SignInEntry(userId, data));
                        }
                    }
                    signInView.getSignInViewSuccess(arrayList);
                } else {
                    Log.e("show", e.getMessage());
                    signInView.getSignInViewFail(e.getMessage());
                }
            }
        });
    }


    public void saveSignin(ArrayList<String> date) {

        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("SignIn");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<SignInEntry> arrayList = new ArrayList<SignInEntry>();
                    String userId = ApplicationStatic.getUserMessage().getObjectId();
                    boolean isEntry = false;
                    for (AVObject avObject : list) {
                        if (avObject.getString("userId").equals(userId)) {

//                            avObject.getJSONArray("signInList");
                            if (isEntry){
                                continue;
                            }
                            //更新现有数据
                            AVObject todoFolder = AVObject.createWithoutData("SignIn", avObject.getObjectId());// 构建对象
                            todoFolder.put("signInList", date);
                            todoFolder.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        signInView.signInSuccess();
                                    } else {
                                        signInView.signInFail(e.getMessage());
                                    }
                                }
                            });
                            isEntry = true;
                        }
                    }


                    if (!isEntry){
                        //重新写入数据
                        AVObject avObject = new AVObject("SignIn");
                        avObject.put("userId" , ApplicationStatic.getUserMessage().getObjectId());
                        avObject.put("signInList" , date);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    signInView.signInSuccess();
                                } else {
                                    signInView.signInFail(e.getMessage());
                                }
                            }
                        });
                    }


                } else {
                    Log.e("show", e.getMessage());

                }
            }
        });


    }





}
