package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.homepage.view.BookDetailView;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.homepage.view.BookSecTionContentEntry;
import com.tyqhwl.jrqh.homepage.view.BookSection;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDetailPresenter {


    private BookDetailView bookDetailView;

    public BookDetailPresenter(BookDetailView bookDetailView) {
        this.bookDetailView = bookDetailView;
    }

    //获取书籍列表
    public void getBookDetailSection(int bid) {
        ViseHttp.GET("http://data.fk7h.com/yapi/book/chapter?bid=713247")
                .addParam("bid", bid + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            ArrayList<BookSection> arrayList = new ArrayList<BookSection>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsb = (JSONObject) jsonArray.get(i);
                                int id = jsb.getInt("id");
                                String title = jsb.getString("title");
                                arrayList.add(new BookSection(id, title));
                            }
                            bookDetailView.getBookDetailSectionSec(arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            bookDetailView.getBookDetailSectionFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        bookDetailView.getBookDetailSectionFail(errMsg);
                    }
                });
    }

    //获取章节内容
    public void getBookSectionContent(int id) {
        ViseHttp.GET("http://data.fk7h.com/yapi/book/content?id=4")
                .addParam("id", id + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data).getJSONObject("data");
                            int id = jsonObject.getInt("id");
                            String title = jsonObject.getString("title");
                            String content = jsonObject.getString("content");
                            String create_time = jsonObject.getString("create_time");
                            int bid = jsonObject.getInt("bid");
                            int chapter = jsonObject.getInt("chapter");
                            BookSecTionContentEntry bookSecTionContentEntry = new BookSecTionContentEntry(id, title, content, create_time, bid, chapter);

                            bookDetailView.getBookSectionContentSec(bookSecTionContentEntry);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            bookDetailView.getBookSectionContentFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        bookDetailView.getBookSectionContentFail(errMsg);
                    }
                });
    }

    //上传书籍信息
    public void getLookCatagoryData(BookEntry bookEntry) {
        AVObject avObject = new AVObject("MyBookList");
        avObject.put("id", bookEntry.getId());
        avObject.put("title", bookEntry.getTitle());
        avObject.put("description", bookEntry.getDescription());
        avObject.put("author", bookEntry.getAuthor());
        avObject.put("images", bookEntry.getImages());
        avObject.put("bid", bookEntry.getBid());
        avObject.put("type", bookEntry.getType());
        avObject.put("click_number", bookEntry.getClick_number());
        avObject.put("create_time", bookEntry.getCreate_time());
        avObject.put("userId", ApplicationStatic.getUserMessage().getObjectId());
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                } else {

                }
            }
        });
    }


    public void getBookMessageToUser(BookEntry bookEntry) {
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("MyBookList");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<BookEntry> arrayList = new ArrayList<>();
                    boolean isRepetition = false;
                    for (AVObject avObject : list) {
                        String userId = avObject.getString("userId");
                        String id = avObject.getString("id");

                        if (bookEntry.getId().equals(id) && userId.equals(ApplicationStatic.getUserMessage().getObjectId())) {
                            isRepetition = true;
                        }
                    }
                    if (!isRepetition) {

                        //未存在
                        getLookCatagoryData(bookEntry);
                    } else {
                        //已存在
                    }

                } else {
                    Log.e("show", e.getMessage());
                }
            }
        });
    }
}
