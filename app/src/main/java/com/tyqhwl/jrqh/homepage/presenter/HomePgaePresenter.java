package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;
import com.tyqhwl.jrqh.homepage.view.HomePageView;
import com.tyqhwl.jrqh.homepage.view.SignInEntry;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HomePgaePresenter {


    private HomePageView homePageView;


    public HomePgaePresenter(HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    public void getHomeRecyclerData(){
        homePageView.showAwait();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("Books");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    ArrayList<HomePageEntry> arrayList = new ArrayList<HomePageEntry>();
                    for (AVObject avObject : list){
                        String title = avObject.getString("title");
                        String context = avObject.getString("context");
                        String image = avObject.getString("image");
                        int read = avObject.getInt("read");
                        ArrayList<String> studObjectId = (ArrayList<String>) avObject.getList("studObjectId");
                        arrayList.add(new HomePageEntry(title , context , image , read ,studObjectId));
                    }
                    homePageView.finishAwait();
                    homePageView.getHomePageDataSuccess(arrayList);

                }else {
                    homePageView.finishAwait();
                    homePageView.getHomePageDataFail(e.getMessage());

                }
            }
        });
    }



    public void getHomeRecyclerDataAwait(){

        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("Books");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    ArrayList<HomePageEntry> arrayList = new ArrayList<HomePageEntry>();
                    for (AVObject avObject : list){
                        String title = avObject.getString("title");
                        String context = avObject.getString("context");
                        String image = avObject.getString("image");
                        int read = avObject.getInt("read");
                        ArrayList<String> studObjectId = (ArrayList<String>) avObject.getList("studObjectId");
                        arrayList.add(new HomePageEntry(title , context , image , read ,studObjectId));
                    }
                    homePageView.getHomePageDataSuccess(arrayList);

                }else {
                    homePageView.getHomePageDataFail(e.getMessage());

                }
            }
        });
    }

    public void getUserStatuBooks(){
        if (!ApplicationStatic.getUserLoginState()){
            homePageView.getUserBooksDataSuccess(new ArrayList<>());
            return;
        }
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("UserStatuBooks");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (AVObject avObject : list){
                        if (avObject.getString("userId").equals(ApplicationStatic.getUserMessage().getObjectId())){
                            for (int i = 0 ; i< avObject.getJSONArray("booksId").length() ; i++){
                                try {
                                    arrayList.add((String) avObject.getJSONArray("booksId").get(i));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                    homePageView.getUserBooksDataSuccess(arrayList);

                }else {
                    Log.e("show" , e.getMessage());
                    homePageView.getUserBooksDataFail(e.getMessage());
                }
            }
        });
    }


    public void getSignIn(){
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
                    if (arrayList.size() > 0){
                        homePageView.getSignInTodaySuccess(arrayList.get(0).signinList.size());
                    }else {
                        homePageView.getSignInTodaySuccess(0);
                    }

                } else {
                    Log.e("show", e.getMessage());
                    homePageView.getHomePageDataFail(e.getMessage());
                }
            }
        });
    }

}
