package com.tyqhwl.jrqh;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;
import java.util.HashMap;

//保存全局变量
public class ApplicationStatic {
    public static final String USER_LOGIN_STATE = "user_login_state";
    public static final String USER_MESSAGE = "user_message";
    public static final String VIEW_POINT = "view_point";
    public static final String LEANRING_TIME_TODAY = "learnint_time_today";
    public static final String TOTAL_STUDY_TIME = "total_study_time";
    public static final String SIGN_IN_NUMBER_OF_DAYS = "sign_in_number_of_days";
    public static final String BOOK_ALL_DATA = "book_all_data";
    public static final String READING_MODEL = "reading_model";
    public static HashMap<String , Object> hashMap = new HashMap();


    //保存阅读模式
    public static void saveReadingModel(boolean isread){
        hashMap.put(READING_MODEL , isread);
    }

    //获取阅读模式
    public static boolean getRadingModel(){
        if (hashMap.get(READING_MODEL) != null){
            return (boolean) hashMap.get(READING_MODEL);
        }else {
            return false;
        }
    }

    //保存书籍信息
    public static void saveBookAllData(ArrayList<BookEntry> arrayList){
        ArrayList<BookEntry> ar = getBookAllData();
        ar.addAll(arrayList);
        hashMap.put(BOOK_ALL_DATA , ar);
    }

    //获取所有书籍信息
    public static ArrayList<BookEntry> getBookAllData(){
        ArrayList<BookEntry> arrayList = new ArrayList<>();
        if (hashMap.get(BOOK_ALL_DATA) != null){
            arrayList.addAll((ArrayList<BookEntry>) hashMap.get(BOOK_ALL_DATA));
            return arrayList;
        }else {
            return arrayList;
        }
    }

    //保存用户今日学习时间
    public static void setLearningTimeToday(long timeToday){
        hashMap.put(LEANRING_TIME_TODAY , timeToday);
    }
    //获取用户今日学习时间
    public static long getLearningTimeToday(){
        if (hashMap.get(LEANRING_TIME_TODAY) != null){
            return (long) hashMap.get(LEANRING_TIME_TODAY);
        }else {
            return 0;
        }
    }

    //保存总共学习时间
    public static void setTotalStudyTime(long totalStudyTime){
        hashMap.put(TOTAL_STUDY_TIME , totalStudyTime);
    }

    //获取总共学习时间
    public static long getTotalStudyTime(){
        if (hashMap.get(TOTAL_STUDY_TIME) != null){
            return (long) hashMap.get(TOTAL_STUDY_TIME);
        }else {
            return 0;
        }
    }

    //保存总共签到天数
    public static void setSignInNumberOfDays(int signInNumberOfDays){
        hashMap.put(SIGN_IN_NUMBER_OF_DAYS , signInNumberOfDays);
    }

    //获取总共签到天数
    public static int getSignInNumberOfDays(){
        if (hashMap.get(SIGN_IN_NUMBER_OF_DAYS) != null){
            return (int) hashMap.get(SIGN_IN_NUMBER_OF_DAYS);
        }else {
            return 0;
        }
    }



    public static  ArrayList<ViewPointCountEntry> data = new ArrayList<>();
    //保存用户登录状态
    public static void setUserLoginState(boolean userLoginState){
        hashMap.put(USER_LOGIN_STATE , userLoginState);
    }

    //获取用户保存状态
    public static boolean getUserLoginState(){
        if (hashMap.get(USER_LOGIN_STATE) != null){
            return (boolean) hashMap.get(USER_LOGIN_STATE);
        }else {
            return false;
        }
    }

    //保存用户数据
    public static void setUserMessage(AVUser avUser){
        hashMap.put(USER_MESSAGE , avUser);
    }

    //获取用户保存状态
    public static AVUser getUserMessage(){
        if (hashMap.get(USER_MESSAGE) != null){
            return (AVUser) hashMap.get(USER_MESSAGE);
        }else {
            return new AVUser();
        }
    }

    //保存观点数据
    public static void setViewPoint(ArrayList<ViewPointCountEntry> data ){
        hashMap.put(VIEW_POINT , data);
    }

    //获取观点数据
    public static ArrayList<ViewPointCountEntry>  getViewPoint(){
        if (hashMap.get(VIEW_POINT) != null){
            return (ArrayList<ViewPointCountEntry>) hashMap.get(USER_MESSAGE);
        }else {
            return data;
        }
    }

    //修改观点数据
    public static void saveViewPoint(int count ,String approve, String oppose){
        getViewPoint().get(count).setApprove(approve);
        getViewPoint().get(count).setOppose(approve);
    }

}
