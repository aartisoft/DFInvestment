package com.tyqhwl.jrqh.seek.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import com.tyqhwl.jrqh.seek.view.InversorView;
import com.tyqhwl.jrqh.seek.view.SaveNewMessageView;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class InversorPresenter {

    private InversorView inversorView;
    private SaveNewMessageView saveNewMessageView;

    public InversorPresenter(InversorView inversorView) {
        this.inversorView = inversorView;
    }


    public InversorPresenter(SaveNewMessageView saveNewMessageView) {
        this.saveNewMessageView = saveNewMessageView;
    }


    public void getInversorData(String tags, int page) {
        ViseHttp.GET("http://data.fk7h.com/yapi/news_letter/gelonghui_list?tags=sd&page=1&size=10")
                .addParam("tags", tags)
                .addParam("page", page + "")
                .addParam("size", "10")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        if (data != null) {
                            JSONArray jsonArray = null;
                            ArrayList<InversorEntry> arrayList = new ArrayList<>();
                            try {
                                jsonArray = new JSONObject(data).getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String title = jsonObject.getString("title");
                                    String summary = jsonObject.getString("summary");
                                    String thumb = jsonObject.getString("thumb");
                                    String read = jsonObject.getString("read");
                                    String time = jsonObject.getString("time");
                                    String like = jsonObject.getString("like");
                                    int post_id = jsonObject.getInt("post_id");
                                    String image = jsonObject.getString("thumb");
                                    arrayList.add(new InversorEntry(title, summary, thumb, post_id, time,"", false ,image));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            inversorView.getInversorSuccess(arrayList);
                        } else {
                            ArrayList<InversorEntry> arrayList = new ArrayList<>();
                            inversorView.getInversorSuccess(arrayList);
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        inversorView.getInversorFail(errMsg);
                    }
                });
    }
    public void getInversorDataSecond(String tags, int page) {
        if (page == 1){
            inversorView.showAwait();
        }
        ViseHttp.GET("http://data.fk7h.com/yapi/news_letter/gelonghui_list?tags=sd&page=1&size=10")
                .addParam("tags", tags)
                .addParam("page", page + "")
                .addParam("size", "10")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        if (data != null) {
                            JSONArray jsonArray = null;
                            ArrayList<InversorEntry> arrayList = new ArrayList<>();
                            try {
                                jsonArray = new JSONObject(data).getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String title = jsonObject.getString("title");
                                    String summary = jsonObject.getString("summary");
                                    String thumb = jsonObject.getString("thumb");
                                    String read = jsonObject.getString("read");
                                    String time = jsonObject.getString("time");
                                    String like = jsonObject.getString("like");
                                    int post_id = jsonObject.getInt("post_id");
                                    String image = jsonObject.getString("thumb");
                                    arrayList.add(new InversorEntry(title, summary, thumb, post_id, time,"", false  , image));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (page == 1){
                                inversorView.finishAwait();
                            }
                            inversorView.getInversorSuccess(arrayList);
                        } else {
                            ArrayList<InversorEntry> arrayList = new ArrayList<>();
                            inversorView.getInversorSuccess(arrayList);
                            if (page == 1){
                                inversorView.finishAwait();
                            }
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        inversorView.getInversorFail(errMsg);
                        if (page == 1){
                            inversorView.finishAwait();
                        }
                    }
                });
    }

    //保存用户收藏数据
    public void saveUserNewMesage(String read, String content, String time, String title, String imageUrl, int postId, String userId) {
//        saveNewMessageView.showAwait();
        AVObject todoFolder = new AVObject("CollectNewMessage");// 构建对象
        todoFolder.put("read", read);// 设置名称
        todoFolder.put("content", content);// 设置名称
        todoFolder.put("time", time);// 设置名称
        todoFolder.put("title", title);// 设置名称
        todoFolder.put("imageUrl", imageUrl);// 设置名称
        todoFolder.put("post_id", postId);// 设置名称
        todoFolder.put("userId", userId);// 设置名称
        todoFolder.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    saveNewMessageView.getSaveNewMessageSuccess();
//                    saveNewMessageView.finishAwait();
                } else {
                    saveNewMessageView.getSaveNewMessageFail(e.getMessage());
//                    saveNewMessageView.finishAwait();
                }
            }
        });// 保存到服务端

    }


    //删除用户收藏数据
    public void deleteuserNewMessage(int postId) {
//        saveNewMessageView.showAwait();
        //获取所有数据
        AVQuery<AVObject> query = new AVQuery<>("CollectNewMessage");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    boolean isData = false;
                    String objectId = null;
                    //便利数组，获取数据相同postid,进行删除
                    for (AVObject avObject : list) {
                        int postIdAB = avObject.getInt("post_id");
                        if (postIdAB == postId) {
                            isData = true;
                            objectId = avObject.getObjectId();
                            if (isData && objectId != null) {
                                //删除数据
                                AVObject avObjects = AVObject.createWithoutData("CollectNewMessage", objectId);
                                avObjects.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        // 如果 e 为空，说明操作成功
                                        if (e == null) {
//                                    Log.e("show" , "删除成功");
                                            saveNewMessageView.cancelSaveSuccess();
                                        } else {
                                            saveNewMessageView.cancelSaveFail(e.getMessage());
                                        }
                                    }
                                });
                            }

                        }
                    }
//                    saveNewMessageView.finishAwait();
                } else {
//                    Log.e("show" , e.getMessage());

                    saveNewMessageView.cancelSaveFail(e.getMessage());
//                    saveNewMessageView.finishAwait();
                }
            }
        });
    }

}
