package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAttentionPresenter {

    public MyAttentionView myAttentionView;


    public MyAttentionPresenter(MyAttentionView myAttentionView) {
        this.myAttentionView = myAttentionView;
    }

    //获取所有评价，筛选用户保存数据
    public void getMyAttentionData(){
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyAttention");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e ==null){
                    ArrayList<MyAttentionEntry> attentionEntries = new ArrayList<MyAttentionEntry>();
                    Collections.reverse(list);
                    for (AVObject avObject :list){
                        String userId = avObject.getString("userId");
//                        if (userId.equals(ApplicationStatic.getUserMessage().getObjectId())){
                            String thumb = avObject.getString("thumb");
                            String read = avObject.getString("read");
                            String time = avObject.getString("time");
                            String tital = avObject.getString("tital");
                            String summary = avObject.getString("summary");
                            int post_id = avObject.getInt("post_id");
                            String userImage = avObject.getString("userImage");
                            String userName = avObject.getString("userName");
                            String image = avObject.getString("countImage");
                            ArrayList<AttentionListEntry> attentionListEntries = new ArrayList<AttentionListEntry>();
                            JSONArray jsonArray = avObject.getJSONArray("attentionList");
                            if (jsonArray != null){
                                for (int i = 0 ; i< jsonArray.length() ; i++){
                                    try {
                                        String userNames = jsonArray.getJSONObject(i).getString("userNames");
                                        String userImages = jsonArray.getJSONObject(i).getString("userImages");
                                        String comments = jsonArray.getJSONObject(i).getString("comments");
                                        attentionListEntries.add(new AttentionListEntry(userImages , userNames , comments));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            attentionEntries.add(new MyAttentionEntry(userId , thumb , read , time , tital , summary , post_id , userImage , userName
                             ,image,attentionListEntries));
//                            Log.e("show" ,thumb + "##" + read + "%%" + time + "%%" + tital + "%%" + tital +
//                                    "%%" + summary + "%%" + post_id + "%%" + attentionListEntries.get(0).toString() + "%%" );
//                        }

                        myAttentionView.getMyAttentionSuccess(attentionEntries);

                    }
                }else {
                    Log.e("show" ,e.getMessage() + "ssss");
                    myAttentionView.getMyAttentionFail(e.getMessage());
                }
            }
        });
    }

}
