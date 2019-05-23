package com.tyqhwl.jrqh.exercise.view;

import java.io.Serializable;

public class ExerciseAnswer implements Serializable {
    public int fragmentIndex;
    public int answerIndex;

    public ExerciseAnswer(int fragmentIndex, int answerIndex) {
        this.fragmentIndex = fragmentIndex;
        this.answerIndex = answerIndex;
    }
}
