package com.tyqhwl.jrqh.seek.view;

import com.tyqhwl.jrqh.base.BaseView;
import com.tyqhwl.jrqh.exercise.view.ExerciseEntry;

import java.util.ArrayList;

public interface ExerciseView extends BaseView {
    void  getExerciseSuccess(ArrayList<ExerciseEntry> data);
    void  getExerciseFail(String msg);
}
