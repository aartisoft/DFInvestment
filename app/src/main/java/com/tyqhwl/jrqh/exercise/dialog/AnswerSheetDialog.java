package com.tyqhwl.jrqh.exercise.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tyqhwl.jrqh.R;
import java.util.ArrayList;

public class AnswerSheetDialog extends AlertDialog implements View.OnClickListener {

    TextView answerSheetDialogFirst;
    TextView answerSheetDialogSecond;
    TextView answerSheetDialogThread;
    TextView answerSheetDialogFour;
    TextView answerSheetDialogFive;
    TextView answerSheetDialogSex;
    TextView answerSheetDialogSeven;
    TextView answerSheetDialogEight;
    TextView answerSheetDialogNine;
    TextView answerSheetDialogThen;
    ImageView answerSheetDialogFinansh;
    private ArrayList<String> answerSheet = new ArrayList<>();//答题卡
    private Activity activity;
    private Context context;
    private AnswerSheetListener answerSheetListener;

    public interface AnswerSheetListener{
        void onClick(int index);
    }

    public AnswerSheetDialog(Context context, ArrayList<String> answerSheet , AnswerSheetListener answerSheetListener) {
        super(context);
        this.answerSheet = answerSheet;
        this.context = context;
        this.answerSheetListener = answerSheetListener;
    }

    public AnswerSheetDialog(Context context, int themeResId, ArrayList<String> answerSheet) {
        super(context, themeResId);
        this.answerSheet = answerSheet;
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_sheet_dialog);
        answerSheetDialogFirst = findViewById(R.id.answer_sheet_dialog_first);
        answerSheetDialogSecond = findViewById(R.id.answer_sheet_dialog_second);
        answerSheetDialogThread = findViewById(R.id.answer_sheet_dialog_thread);
        answerSheetDialogFour = findViewById(R.id.answer_sheet_dialog_four);
        answerSheetDialogFive = findViewById(R.id.answer_sheet_dialog_five);
        answerSheetDialogSex = findViewById(R.id.answer_sheet_dialog_sex);
        answerSheetDialogSeven = findViewById(R.id.answer_sheet_dialog_seven);
        answerSheetDialogEight = findViewById(R.id.answer_sheet_dialog_eight);
        answerSheetDialogNine = findViewById(R.id.answer_sheet_dialog_nine);
        answerSheetDialogThen = findViewById(R.id.answer_sheet_dialog_then);
        answerSheetDialogFinansh = findViewById(R.id.answer_sheet_dialog_finansh);

        answerSheetDialogFirst.setOnClickListener(this);
        answerSheetDialogSecond.setOnClickListener(this);
        answerSheetDialogThread.setOnClickListener(this);
        answerSheetDialogFour.setOnClickListener(this);
        answerSheetDialogFive.setOnClickListener(this);
        answerSheetDialogSex.setOnClickListener(this);
        answerSheetDialogSeven.setOnClickListener(this);
        answerSheetDialogEight.setOnClickListener(this);
        answerSheetDialogNine.setOnClickListener(this);
        answerSheetDialogThen.setOnClickListener(this);
        answerSheetDialogFinansh.setOnClickListener(this);
        init();
    }

    private void init() {
        for (int i = 0; i < answerSheet.size(); i++) {
            if (!answerSheet.get(i).equals("5")) {
                setCheck(i , true);
            } else {
                setCheck(i , false);
            }
        }
    }


    private void setCheck(int index , boolean ischeck) {
        switch (index) {
            case 0:
                if (ischeck){
                    answerSheetDialogFirst.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogFirst.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogFirst.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogFirst.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 1:
                if (ischeck){
                    answerSheetDialogSecond.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogSecond.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogSecond.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogSecond.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 2:
                if (ischeck){
                    answerSheetDialogThread.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogThread.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogThread.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogThread.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 3:
                if (ischeck){
                    answerSheetDialogFour.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogFour.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogFour.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogFour.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 4:
                if (ischeck){
                    answerSheetDialogFive.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogFive.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogFive.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogFive.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 5:
                if (ischeck){
                    answerSheetDialogSex.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogSex.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogSex.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogSex.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 6:
                if (ischeck){
                    answerSheetDialogSeven.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogSeven.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogSeven.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogSeven.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 7:
                if (ischeck){
                    answerSheetDialogEight.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogEight.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogEight.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogEight.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 8:
                if (ischeck){
                    answerSheetDialogNine.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogNine.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogNine.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogNine.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
            case 9:
                if (ischeck){
                    answerSheetDialogThen.setBackgroundResource(R.drawable.answer_sheet_text_shape);
                    answerSheetDialogThen.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    answerSheetDialogThen.setBackgroundResource(R.drawable.answer_sheet_text_shape_second);
                    answerSheetDialogThen.setTextColor(Color.parseColor("#c2c2c2"));
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.answer_sheet_dialog_finansh:
                dismiss();
                break;
            case R.id.answer_sheet_dialog_first:
                answerSheetListener.onClick(0);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_second:
                answerSheetListener.onClick(1);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_thread:
                answerSheetListener.onClick(2);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_four:
                answerSheetListener.onClick(3);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_five:
                answerSheetListener.onClick(4);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_sex:
                answerSheetListener.onClick(5);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_seven:
                answerSheetListener.onClick(6);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_eight:
                answerSheetListener.onClick(7);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_nine:
                answerSheetListener.onClick(8);
                dismiss();
                break;
            case R.id.answer_sheet_dialog_then:
                answerSheetListener.onClick(9);
                dismiss();
                break;
        }
    }
}
