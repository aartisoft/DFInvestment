package com.tyqhwl.jrqh.homepage.presenter;

import android.util.Log;

import com.tyqhwl.jrqh.base.BaseCalculate;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.homepage.view.BookItemView;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookItemPresenter {


    private BookItemView bookItemView;


    public BookItemPresenter(BookItemView bookItemView) {
        this.bookItemView = bookItemView;
    }

    public void getBookData(int page , int num , int type){
        bookItemView.showAwait();
        ViseHttp.GET("http://data.fk7h.com/yapi/book/book_list?page=1&num=10&type=1")
                .addParam("page" , page + "")
                .addParam("num", num + "")
                .addParam("type", type + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("show"  , data + "");
                            JSONObject jsonObject = new JSONObject(data).getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            ArrayList<BookEntry> arrayList = new ArrayList<BookEntry>();
                            int[] bc = BaseCalculate.testC(jsonArray.length());
                            for (int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject jsb = (JSONObject) jsonArray.get(bc[i]);
                                String id = jsb.getString("id");
                                String title = jsb.getString("title");
                                String description = jsb.getString("description");
                                String author = jsb.getString("author");
                                String images = jsb.getString("images");
                                String bid = jsb.getString("bid");
                                String type = jsb.getString("type");
                                String click_number = jsb.getString("click_number");
                                String create_time = jsb.getString("create_time");
                                arrayList.add(new BookEntry(id , title , description , author , images , bid , type , click_number , create_time));
                            }
                            Log.e("show"  , arrayList.size() + "");
                            bookItemView.getBookDataSuccess(arrayList);
                            bookItemView.finishAwait();
                        } catch (JSONException e) {
                            Log.e("show" , e.getMessage());
                            e.printStackTrace();
                            bookItemView.getBookDataFail(e.getMessage());
                            bookItemView.finishAwait();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("show" , errMsg);
                        bookItemView.getBookDataFail(errMsg);
                        bookItemView.finishAwait();
                    }
                });
    }
    public void getBookDataSecond(int page , int num , int type){
        ViseHttp.GET("http://data.fk7h.com/yapi/book/book_list?page=1&num=10&type=1")
                .addParam("page" , page + "")
                .addParam("num", num + "")
                .addParam("type", type + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data).getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            ArrayList<BookEntry> arrayList = new ArrayList<BookEntry>();
                            int[] bc = BaseCalculate.testC(jsonArray.length());
                            for (int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject jsb = (JSONObject) jsonArray.get(bc[i]);
                                String id = jsb.getString("id");
                                String title = jsb.getString("title");
                                String description = jsb.getString("description");
                                String author = jsb.getString("author");
                                String images = jsb.getString("images");
                                String bid = jsb.getString("bid");
                                String type = jsb.getString("type");
                                String click_number = jsb.getString("click_number");
                                String create_time = jsb.getString("create_time");
                                arrayList.add(new BookEntry(id , title , description , author , images , bid , type , click_number , create_time));
                            }
                            bookItemView.getBookDataSuccessFu(arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            bookItemView.getBookDataFailFu(e.getMessage());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("show" , errMsg);
                        bookItemView.getBookDataFailFu(errMsg);
                    }
                });
    }
}
