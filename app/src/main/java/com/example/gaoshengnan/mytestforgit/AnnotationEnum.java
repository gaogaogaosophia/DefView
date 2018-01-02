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

    /**
     * @IntDef "包住" 常量；
     * @Retention 保留定义策略:
     *   SOURCE —— 只在源代码级别保留,编译时就会被忽略
     *   CLASS —— 编译时被保留,在class文件中存在,但JVM将会忽略
     *   RUNTIME —— 被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用
     * @interface 声明构造器
     */
    @IntDef(flag = true,value = {SPRING, SUMMER, WINTER, FALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Season{}

    @Season public int curSeason = WINTER;

    @Season public int getCurSeason(){
        return this.curSeason;
    }

    public void setCurSeason(@Season int season) {
        this.curSeason = season;
    }

    /**
     * 测试@IntDef flag = true的使用
     */
    public void testIntDefFlag() {
        setCurSeason(AnnotationEnum.SPRING & AnnotationEnum.FALL);
        System.out.println("I feel better");
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

    /**
     *测试用反射获得注解
     */
    public void testReflect(){
        Class<AnnotationEnum> clasz =AnnotationEnum.class;

    }

}
