package com.tyqhwl.jrqh.exercise.view;

import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseEntry implements Serializable {

    public String title;
    public ArrayList<String> optionList;
    public int anserIndex;

    public ExerciseEntry(String title, ArrayList<String> optionList, int anserIndex) {
        this.title = title;
        this.optionList = optionList;
        this.anserIndex = anserIndex;
    }
}
