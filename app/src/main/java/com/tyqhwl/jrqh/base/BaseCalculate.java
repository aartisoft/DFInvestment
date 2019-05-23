package com.tyqhwl.jrqh.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 计算
 * */
public class BaseCalculate {

    //生成不重复的随机数
    public static int[] testC(int sz){
        Random rd = new Random();
        int[] rds = new int[sz];//随机数数组
        List<Integer> lst = new ArrayList<Integer>();//存放有序数字集合
        int index = 0;//随机索引
        for(int i=0;i<sz;i++){
            lst.add(i);
        }
        for(int i=0;i<sz;i++){
            index=rd.nextInt(sz-i);
            rds[i]=lst.get(index);
            lst.remove(index);
        }

        return rds;
    }
}
