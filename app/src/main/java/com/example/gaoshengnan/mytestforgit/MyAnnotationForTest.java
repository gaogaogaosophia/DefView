package com.example.gaoshengnan.mytestforgit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gaoshengnan on 2018/1/2.
 * 注解类
 */

public class MyAnnotationForTest {

    /**
     * 类注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyClassAnnotation {
        String desc();
        String uri();
    }

    /**
     * 构造函数注解
     */
    @Target(ElementType.CONSTRUCTOR)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyConstructorAnnotation {
        String desc();
        String uri();
    }

    /**
     * 方法注解
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyMethodAnnotation{
        String desc();
        String uri();
    }

    /**
     * 字段注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyFieldAnnotation{
        String desc();
        String uri();
    }

    /**
     * 参数注解
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyParameterAnnotation{
        String desc();
        String uri();
    }
}







