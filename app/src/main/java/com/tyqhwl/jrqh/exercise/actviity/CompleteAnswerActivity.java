package com.tyqhwl.jrqh.exercise.actviity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.exercise.view.CompleteAnswerEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完成答题页面
 */
public class CompleteAnswerActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.complete_answer_act_title)
    TextView completeAnswerActTitle;
    @BindView(R.id.complete_answer_act_content)
    TextView completeAnswerActContent;
    @BindView(R.id.complete_answer_act_count)
    TextView completeAnswerActCount;
    @BindView(R.id.complete_answer_act_layout)
    LinearLayout completeAnswerActLayout;
    @BindView(R.id.complete_answer_act_percentage)
    TextView completeAnswerActPercentage;
    @BindView(R.id.complete_answer_act_my_answer)
    TextView completeAnswerActMyAnswer;
    @BindView(R.id.complete_answer_act_list)
    TextView completeAnswerActList;
    @BindView(R.id.complete_answer_act_anew_image)
    ImageView completeAnswerActAnewImage;
    @BindView(R.id.complete_answer_act_anew_text)
    TextView completeAnswerActAnewText;
    @BindView(R.id.complete_answer_act_anew_layout)
    LinearLayout completeAnswerActAnewLayout;


    private ArrayList<Integer> arrayList = new ArrayList<>();

    private int count;


    @Override
    public int getXMLLayout() {
        return R.layout.complete_answer_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initIntent();
    }

    private void initIntent() {

        StringBuffer sb = new StringBuffer();

        if (getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD) != null) {
            CompleteAnswerEntry completeAnswerEntry = (CompleteAnswerEntry) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            for (int i = 0; i < completeAnswerEntry.answerList.size(); i++) {
                if (completeAnswerEntry.answerList.get(i).equals(completeAnswerEntry.answerSheet.get(i))) {
                    count++;
                    int s = i;
                    s++;
                    sb.append(s + "." + getStringBuffer(completeAnswerEntry.answerSheet.get(i)));
                } else {
                    int s = i;
                    s++;
                    arrayList.add(sb.length());
                    sb.append(s + "." + getStringBuffer(completeAnswerEntry.answerSheet.get(i)));
                }
            }

            SpannableStringBuilder ssb = new SpannableStringBuilder(sb);
            for (int i = 0 ; i < arrayList.size() ; i++){
                ssb.setSpan(new ForegroundColorSpan(Color.RED), arrayList.get(i) + 2, arrayList.get(i) + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            completeAnswerActList.setText(ssb);
            completeAnswerActCount.setText(count + "");
            completeAnswerActPercentage.setText("击败全国" + count + "0%的人");
        }
    }


    private String getStringBuffer(String index) {
        String s = "";
        switch (index) {
            case "0":
                s = "A  ";
                break;
            case "1":
                s = "B  ";
                break;
            case "2":
                s = "C  ";
                break;
            case "3":
                s = "D  ";
                break;
                default:
                    s = "未选 ";
                    break;
        }
        return s;
    }

    @OnClick({R.id.back, R.id.complete_answer_act_title, R.id.complete_answer_act_content,
            R.id.complete_answer_act_count, R.id.complete_answer_act_layout,
            R.id.complete_answer_act_percentage, R.id.complete_answer_act_my_answer,
            R.id.complete_answer_act_list, R.id.complete_answer_act_anew_image,
            R.id.complete_answer_act_anew_text, R.id.complete_answer_act_anew_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete_answer_act_anew_image:
                //重新进行答题
                IntentSkip.startIntent(this, new ExerciseActivity(), null);
                CompleteAnswerActivity.this.finish();
                break;
            case R.id.complete_answer_act_anew_text:
                //重新进行答题
                IntentSkip.startIntent(this, new ExerciseActivity(), null);
                CompleteAnswerActivity.this.finish();
                break;
            case R.id.complete_answer_act_anew_layout:
                //重新进行答题
                IntentSkip.startIntent(this, new ExerciseActivity(), null);
                CompleteAnswerActivity.this.finish();
                break;
        }
    }
}
