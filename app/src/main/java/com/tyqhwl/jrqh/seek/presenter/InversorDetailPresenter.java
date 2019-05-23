package com.tyqhwl.jrqh.seek.presenter;

import com.tyqhwl.jrqh.seek.view.InversorDetailEntry;
import com.tyqhwl.jrqh.seek.view.InversorDetailView;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

public class InversorDetailPresenter {


    private InversorDetailView inversorDetailView;

    public InversorDetailPresenter(InversorDetailView inversorDetailView) {
        this.inversorDetailView = inversorDetailView;
    }

    public void getInversor(int postId){
        inversorDetailView.showAwait();
        ViseHttp.GET("http://data.fk7h.com/yapi/news_letter/gelonghui_article?post_id=237492")
                .addParam("post_id" , postId + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data).getJSONObject("data");
                            if (jsonObject != null && jsonObject.getString("title")!= null){
                                inversorDetailView.getInversorDetailSuccess(
                                        new InversorDetailEntry(
                                                jsonObject.getString("title")
                                                , jsonObject.getString("content")));
                            }else {
                                inversorDetailView.getInversorDetailSuccess(
                                        new InversorDetailEntry(
                                                jsonObject.getString("")
                                                , jsonObject.getString("")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        inversorDetailView.finishAwait();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        inversorDetailView.finishAwait();
                        inversorDetailView.getInversorDetailFail(errMsg);
                    }
                });
    }

}
