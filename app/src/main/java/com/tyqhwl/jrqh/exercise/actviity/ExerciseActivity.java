package com.tyqhwl.jrqh.exercise.actviity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.exercise.dialog.AnswerSheetDialog;
import com.tyqhwl.jrqh.exercise.fragment.ExerciseFragment;
import com.tyqhwl.jrqh.exercise.presenter.ExercisePresenter;
import com.tyqhwl.jrqh.exercise.view.CompleteAnswerEntry;
import com.tyqhwl.jrqh.exercise.view.ExerciseAnswer;
import com.tyqhwl.jrqh.exercise.view.ExerciseEntry;
import com.tyqhwl.jrqh.seek.view.ExerciseView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 答题首页
 * wmy
 * 2019-04-17
 */
public class ExerciseActivity extends BaseActivity implements ExerciseView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.exercise_act_answer_image)
    ImageView exerciseActAnswerImage;
    @BindView(R.id.exercise_act_answer_layout)
    LinearLayout exerciseActAnswerLayout;
    @BindView(R.id.exercise_activity_framelayout)
    FrameLayout exerciseActivityFramelayout;
    @BindView(R.id.exercise_act_button_first)
    TextView exerciseActButtonFirst;
    @BindView(R.id.exercise_act_button_second)
    TextView exerciseActButtonSecond;


    private ExerciseFragment exerciseFragmentFirst;
    private ExerciseFragment exerciseFragmentSecond;
    private ExerciseFragment exerciseFragmentThread;
    private ExerciseFragment exerciseFragmentFour;
    private ExerciseFragment exerciseFragmentFive;
    private ExerciseFragment exerciseFragmentSex;
    private ExerciseFragment exerciseFragmentSeven;
    private ExerciseFragment exerciseFragmentEight;
    private ExerciseFragment exerciseFragmentNine;
    private ExerciseFragment exerciseFragmentTen;
    private final String TAG0 = "tag0";
    private final String TAG1 = "tag1";
    private final String TAG2 = "tag2";
    private final String TAG3 = "tag3";
    private final String TAG4 = "tag4";
    private final String TAG5 = "tag5";
    private final String TAG6 = "tag6";
    private final String TAG7 = "tag7";
    private final String TAG8 = "tag8";
    private final String TAG9 = "tag9";
    private int fragmentIndex = 0;
    private AwaitDialog awaitDialog;
    private ExercisePresenter exercisePresenter = new ExercisePresenter(this);
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ArrayList<String> answerList = new ArrayList<>();//答案
    private ArrayList<String> answerSheet = new ArrayList<>();//答题卡
    private AnswerSheetDialog answerSheetDialog;

    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o instanceof ExerciseAnswer){
            int s = ((ExerciseAnswer) o).fragmentIndex;
            s--;
            answerSheet.set(s , ((ExerciseAnswer) o).answerIndex + "");
        }

        if (o.equals(EventBusTag.TO_ANSWER_QAESTIONS)){
            answerSheet.clear();
            for (int i = 0 ; i < 10 ; i++){
                answerSheet.add(i , "5");
            }
            fragmentIndex = 0;
            isIndex(fragmentIndex);
            showFragment(TAG0);
        }

    }

    @Override
    public int getXMLLayout() {
        return R.layout.exercise_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0 ; i < 10 ; i++){
            answerSheet.add(i , "5");
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        exercisePresenter.getExerciseData();
        answerSheetDialog = new AnswerSheetDialog(ExerciseActivity.this, answerSheet, new AnswerSheetDialog.AnswerSheetListener() {
            @Override
            public void onClick(int index) {
                showFragment("tag" + index);
                isIndex(index);
            }
        });
    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.exercise_act_answer_image, R.id.exercise_act_answer_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.exercise_act_answer_image:
                //答题卡
                answerSheetDialog.show();
                break;
            case R.id.exercise_act_answer_layout:
                //答题卡
                answerSheetDialog.show();
                break;
        }
    }

    @OnClick({R.id.exercise_act_button_first, R.id.exercise_act_button_second})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.exercise_act_button_first:
                if (fragmentIndex == 0){
                    break;
                }else {
                    fragmentIndex--;
                    showFragment("tag" + fragmentIndex);
                    isIndex(fragmentIndex);

                }
                break;
            case R.id.exercise_act_button_second:
                if (fragmentIndex == 9){
                    //点击跳转到完成做题页面
                    IntentSkip.startIntent(this , new CompleteAnswerActivity() ,new CompleteAnswerEntry(answerList , answerSheet));
                    ExerciseActivity.this.finish();
                }else {
                    fragmentIndex++;
                    showFragment("tag" + fragmentIndex);
                    isIndex(fragmentIndex);
                }
                break;
        }
    }


    public void isIndex(int fragmentIndex){
        if (fragmentIndex == 0){
            exerciseActButtonFirst.setBackgroundResource(R.drawable.exercise_act_button_shape_second);
            exerciseActButtonSecond.setText("下一题");
        }else if (fragmentIndex == 9){
            exerciseActButtonFirst.setBackgroundResource(R.drawable.exercise_act_button_shape);
            exerciseActButtonSecond.setText("提交做题");
        }else {
            exerciseActButtonFirst.setBackgroundResource(R.drawable.exercise_act_button_shape);
            exerciseActButtonSecond.setText("下一题");
        }
    }

    @Override
    public void getExerciseSuccess(ArrayList<ExerciseEntry> data) {
        for (int i = 0 ; i < data.size() ; i++){
            answerList.add(i , data.get(i).anserIndex + "");

        }
        initFragment(data);
    }

    private void initFragment(ArrayList<ExerciseEntry> data) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentFirst = new ExerciseFragment(data.get(0) , 1) , TAG0);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentSecond = new ExerciseFragment(data.get(1) , 2) , TAG1);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentThread = new ExerciseFragment(data.get(2) , 3) , TAG2);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentFour = new ExerciseFragment(data.get(3) , 4) , TAG3);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentFive = new ExerciseFragment(data.get(4) , 5) , TAG4);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentSex = new ExerciseFragment(data.get(5) , 6) , TAG5);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentSeven = new ExerciseFragment(data.get(6) , 7) , TAG6);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentEight = new ExerciseFragment(data.get(7) , 8) , TAG7);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentNine = new ExerciseFragment(data.get(8) , 9) , TAG8);
        fragmentTransaction.add(R.id.exercise_activity_framelayout , exerciseFragmentTen = new ExerciseFragment(data.get(9) , 10) , TAG9);
        fragmentTransaction.commit();
        showFragment(TAG0);
        fragmentIndex = 0;
        exerciseActButtonFirst.setBackgroundResource(R.drawable.exercise_act_button_shape_second);
    }

    private void showFragment(String tag0) {
        if (fragmentManager == null){
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(exerciseFragmentFirst);
        fragmentTransaction.hide(exerciseFragmentSecond);
        fragmentTransaction.hide(exerciseFragmentThread);
        fragmentTransaction.hide(exerciseFragmentFour);
        fragmentTransaction.hide(exerciseFragmentFive);
        fragmentTransaction.hide(exerciseFragmentSex);
        fragmentTransaction.hide(exerciseFragmentSeven);
        fragmentTransaction.hide(exerciseFragmentEight);
        fragmentTransaction.hide(exerciseFragmentNine);
        fragmentTransaction.hide(exerciseFragmentTen);
        switch (tag0){
            case TAG0:
                fragmentTransaction.show(exerciseFragmentFirst);
                break;
            case TAG1:
                fragmentTransaction.show(exerciseFragmentSecond);
                break;
            case TAG2:
                fragmentTransaction.show(exerciseFragmentThread);
                break;
            case TAG3:
                fragmentTransaction.show(exerciseFragmentFour);
                break;
            case TAG4:
                fragmentTransaction.show(exerciseFragmentFive);
                break;
            case TAG5:
                fragmentTransaction.show(exerciseFragmentSex);
                break;
            case TAG6:
                fragmentTransaction.show(exerciseFragmentSeven);
                break;
            case TAG7:
                fragmentTransaction.show(exerciseFragmentEight);
                break;
            case TAG8:
                fragmentTransaction.show(exerciseFragmentNine);
                break;
            case TAG9:
                fragmentTransaction.show(exerciseFragmentTen);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void getExerciseFail(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(this, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }
}
