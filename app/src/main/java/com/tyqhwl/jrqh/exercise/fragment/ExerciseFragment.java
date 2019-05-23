package com.tyqhwl.jrqh.exercise.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.exercise.view.ExerciseAnswer;
import com.tyqhwl.jrqh.exercise.view.ExerciseEntry;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ExerciseFragment extends BaseFragment {
    @BindView(R.id.exercise_frag_title)
    TextView exerciseFragTitle;
    @BindView(R.id.exercise_frag_checkbox_first)
    CheckBox exerciseFragCheckboxFirst;
    @BindView(R.id.exercise_frag_checkbox_first_text)
    TextView exerciseFragCheckboxFirstText;
    @BindView(R.id.exercise_frag_checkbox_first_layout)
    LinearLayout exerciseFragCheckboxFirstLayout;
    @BindView(R.id.exercise_frag_checkbox_second)
    CheckBox exerciseFragCheckboxSecond;
    @BindView(R.id.exercise_frag_checkbox_second_text)
    TextView exerciseFragCheckboxSecondText;
    @BindView(R.id.exercise_frag_checkbox_second_layout)
    LinearLayout exerciseFragCheckboxSecondLayout;
    @BindView(R.id.exercise_frag_checkbox_thread)
    CheckBox exerciseFragCheckboxThread;
    @BindView(R.id.exercise_frag_checkbox_thread_text)
    TextView exerciseFragCheckboxThreadText;
    @BindView(R.id.exercise_frag_checkbox_thread_layout)
    LinearLayout exerciseFragCheckboxThreadLayout;
    @BindView(R.id.exercise_frag_checkbox_four)
    CheckBox exerciseFragCheckboxFour;
    @BindView(R.id.exercise_frag_checkbox_four_text)
    TextView exerciseFragCheckboxFourText;
    @BindView(R.id.exercise_frag_checkbox_four_layout)
    LinearLayout exerciseFragCheckboxFourLayout;
    Unbinder unbinder;
    @BindView(R.id.exercise_frag_index)
    TextView exerciseFragIndex;


    private ExerciseEntry exerciseEntry;
    private int index;

    private int showCheckbox = 5;

    public ExerciseFragment() {
    }

    @SuppressLint("ValidFragment")
    public ExerciseFragment(ExerciseEntry exerciseEntry) {
        this.exerciseEntry = exerciseEntry;
    }

    @SuppressLint("ValidFragment")
    public ExerciseFragment(ExerciseEntry exerciseEntry, int index) {
        this.exerciseEntry = exerciseEntry;
        this.index = index;
    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o.equals(EventBusTag.TO_ANSWER_QAESTIONS)){
            exerciseFragCheckboxFirst.setChecked(false);
            exerciseFragCheckboxSecond.setChecked(false);
            exerciseFragCheckboxThread.setChecked(false);
            exerciseFragCheckboxFour.setChecked(false);
            showCheckbox = 5;
            exerciseFragIndex.setText(index + "/10");
        }
    }


    @Override
    public int getXMLLayout() {
        return R.layout.exercise_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        if (exerciseEntry != null && index != 0) {
            exerciseFragIndex.setText(index + "/10");
            exerciseFragTitle.setText(exerciseEntry.title + "");
            exerciseFragCheckboxFirstText.setText(exerciseEntry.optionList.get(0) + "");
            exerciseFragCheckboxSecondText.setText(exerciseEntry.optionList.get(1) + "");
            exerciseFragCheckboxThreadText.setText(exerciseEntry.optionList.get(2) + "");
            exerciseFragCheckboxFourText.setText(exerciseEntry.optionList.get(3) + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.exercise_frag_title, R.id.exercise_frag_checkbox_first, R.id.exercise_frag_checkbox_first_text, R.id.exercise_frag_checkbox_first_layout, R.id.exercise_frag_checkbox_second, R.id.exercise_frag_checkbox_second_text, R.id.exercise_frag_checkbox_second_layout, R.id.exercise_frag_checkbox_thread, R.id.exercise_frag_checkbox_thread_text, R.id.exercise_frag_checkbox_thread_layout, R.id.exercise_frag_checkbox_four, R.id.exercise_frag_checkbox_four_text, R.id.exercise_frag_checkbox_four_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exercise_frag_checkbox_first:
                if (showCheckbox != 0){
                    showCheckbox = 0;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_first_text:
                if (showCheckbox != 0){
                    showCheckbox = 0;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_first_layout:
                if (showCheckbox != 0){
                    showCheckbox = 0;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_second:
                if (showCheckbox != 1){
                    showCheckbox = 1;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_second_text:
                if (showCheckbox != 1){
                    showCheckbox = 1;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_second_layout:
                if (showCheckbox != 1){
                    showCheckbox = 1;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_thread:
                if (showCheckbox != 2){
                    showCheckbox = 2;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_thread_text:
                if (showCheckbox != 2){
                    showCheckbox = 2;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_thread_layout:
                if (showCheckbox != 2){
                    showCheckbox = 2;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_four:
                if (showCheckbox != 3){
                    showCheckbox = 3;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_four_text:
                if (showCheckbox != 3){
                    showCheckbox = 3;
                    getShowCheckBox(showCheckbox);
                }
                break;
            case R.id.exercise_frag_checkbox_four_layout:
                if (showCheckbox != 3){
                    showCheckbox = 3;
                    getShowCheckBox(showCheckbox);
                }
                break;
        }
    }


    public void getShowCheckBox(int showCheckbox){
        //发送消息事件给activity，通知其选择的答案下标
        EventBus.getDefault().post(new ExerciseAnswer(index , showCheckbox));
        exerciseFragCheckboxFirst.setChecked(false);
        exerciseFragCheckboxSecond.setChecked(false);
        exerciseFragCheckboxThread.setChecked(false);
        exerciseFragCheckboxFour.setChecked(false);
        switch (showCheckbox){
            case 0:
                exerciseFragCheckboxFirst.setChecked(true);
                break;
            case 1:
                exerciseFragCheckboxSecond.setChecked(true);
                break;
            case 2:
                exerciseFragCheckboxThread.setChecked(true);
                break;
            case 3:
                exerciseFragCheckboxFour.setChecked(true);
                break;
        }
    }

}
