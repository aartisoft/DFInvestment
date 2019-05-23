package com.tyqhwl.jrqh.exercise.view;

import java.io.Serializable;
import java.util.ArrayList;

public class CompleteAnswerEntry implements Serializable {

    public ArrayList<String> answerList;//答案
    public ArrayList<String> answerSheet ;//答题卡

    public CompleteAnswerEntry(ArrayList<String> answerList, ArrayList<String> answerSheet) {
        this.answerList = answerList;
        this.answerSheet = answerSheet;
    }
}
