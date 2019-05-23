package com.tyqhwl.jrqh.user.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.user.view.LookCatagoryView;

import java.util.ArrayList;
import java.util.List;

public class LookCatagoryPresneter {


    private LookCatagoryView lookCatagoryView;

    public LookCatagoryPresneter(LookCatagoryView lookCatagoryView) {
        this.lookCatagoryView = lookCatagoryView;
    }

    public void getLookCatagoryData() {
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyBookList");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<BookEntry> arrayList = new ArrayList<>();
                    for (AVObject avObject : list) {
                        String userId = avObject.getString("userId");
                        if (userId.equals(ApplicationStatic.getUserMessage().getObjectId())) {
                            String id = avObject.getString("id");
                            String title = avObject.getString("title");
                            String description = avObject.getString("description");
                            String author = avObject.getString("author");
                            String images = avObject.getString("images");
                            String bid = avObject.getString("bid");
                            String type = avObject.getString("type");
                            String click_number = avObject.getString("click_number");
                            String create_time = avObject.getString("create_time");
                            boolean isRepetition = false;
                            for (int s = 0 ; s < arrayList.size() ; s++){
                                if (arrayList.get(s).getId().equals(id)){
                                    isRepetition = true;
                                }
                            }

                            if (!isRepetition){
                                arrayList.add(new BookEntry(id , title , description , author , images , bid , type , click_number , create_time));
                            }
                        }
                    }
                    lookCatagoryView.getLookCatagorySuccess(arrayList);
                } else {
                    lookCatagoryView.getLookCatagoryFail(e.getMessage());
                    Log.e("show", e.getMessage());
                }
            }
        });
    }

}
