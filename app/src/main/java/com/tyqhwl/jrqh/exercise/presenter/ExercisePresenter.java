package com.tyqhwl.jrqh.exercise.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.tyqhwl.jrqh.exercise.view.ExerciseEntry;
import com.tyqhwl.jrqh.seek.view.ExerciseView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ExercisePresenter {

    private ExerciseView exerciseView;


    public ExercisePresenter(ExerciseView exerciseView) {
        this.exerciseView = exerciseView;
    }

    public void getExerciseData(){
        exerciseView.showAwait();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("exercise");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    ArrayList<ExerciseEntry> data = new ArrayList<>();
                    for (AVObject avObject : list){
                        String title = avObject.getString("topicName");
                        int answerIndex = avObject.getInt("anserIndex");
                        JSONArray jsonArray = avObject.getJSONArray("optionList");
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0 ; i < jsonArray.length() ; i++){
                            try {
                                arrayList.add((String) jsonArray.get(i));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        data.add(new ExerciseEntry(title , arrayList , answerIndex));
                    }
                    exerciseView.getExerciseSuccess(data);
                    exerciseView.finishAwait();
                }else {
                    exerciseView.finishAwait();
                    exerciseView.getExerciseFail(e.getMessage());
                    Log.e("show" , e.getMessage());
                }
            }
        });
    }

}
