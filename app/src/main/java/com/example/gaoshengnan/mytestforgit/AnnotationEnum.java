package com.example.gaoshengnan.mytestforgit;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by gaoshengnan on 2017/12/22.
 * 用注解替代枚举，在编译时期像enum那样选择变量：
 * 使用注解IntDef替代之前使用的Enum；
 */
public class AnnotationEnum {

    //声明常量
    public static final int SPRING = 0;
    public static final int SUMMER = 1;
    public static final int FALL = 2;
    public static final int WINTER = 3;

    public AnnotationEnum(@Season int season){
        System.out.println("season:" + season);
    }

    //声明IntDef
    @IntDef({SPRING, SUMMER, WINTER, FALL})
    @Retention(RetentionPolicy.SOURCE)
    //声明新的枚举注解类型
    public @interface Season{}

    @Season public int curSeason = WINTER;

    @Season public int getCurSeason(){
        return this.curSeason;
    }

    public void setCurSeason(@Season int season) {
        this.curSeason = season;
    }

    public void whichSeason(){
        @Season int season = getCurSeason();
        switch (season) {
            case SPRING:
                Log.i("AnnotationEnum", "The Season is : SPRING");
                break;
            case SUMMER:
                Log.i("AnnotationEnum", "The Season is : SUMMER");
                break;
            case FALL:
                Log.i("AnnotationEnum", "The Season is : FALL");
                break;
            case WINTER:
                Log.i("AnnotationEnum", "The Season is : WINTER");
                break;
        }
    }
}
