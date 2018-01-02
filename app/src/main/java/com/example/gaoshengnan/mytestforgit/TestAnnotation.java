package com.example.gaoshengnan.mytestforgit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by gaoshengnan on 2018/1/2.
 * 注解反射测试类
 */
@MyAnnotationForTest.MyClassAnnotation(desc = "the class",uri = "")
public class TestAnnotation {

    @MyAnnotationForTest.MyConstructorAnnotation(desc = "the constructor",uri = "")
    public TestAnnotation(){
    }

    @MyAnnotationForTest.MyFieldAnnotation(desc = "the field",uri = "")
    private int id;

    /**
     * 必须得为public，若为private则无效 为啥
     * @param id
     */
    @MyAnnotationForTest.MyMethodAnnotation(desc = "the method",uri = "")
    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<TestAnnotation> clasz = TestAnnotation.class;
        //获得类注解
        MyAnnotationForTest.MyClassAnnotation myClassAnnotation = clasz.getAnnotation(MyAnnotationForTest.MyClassAnnotation.class
        );
        System.out.println(myClassAnnotation.desc() + " " + myClassAnnotation.uri());
        //得到构造函数注解
        Constructor<TestAnnotation> constructor = clasz.getConstructor(new Class[]{});
        MyAnnotationForTest.MyConstructorAnnotation myConstructorAnnotation = constructor.getAnnotation(MyAnnotationForTest.MyConstructorAnnotation.class);
        System.out.println(myConstructorAnnotation.desc() + " " + myClassAnnotation.uri());
        //获取方法注解
        Method method = clasz.getMethod("setId", new Class[]{int.class});
        MyAnnotationForTest.MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyAnnotationForTest.MyMethodAnnotation.class);
        System.out.println(myMethodAnnotation.desc() + " " + myMethodAnnotation.uri());
        //获取字段注解
        Field field = clasz.getDeclaredField("id");
        MyAnnotationForTest.MyFieldAnnotation myFieldAnnotation = field.getAnnotation(MyAnnotationForTest.MyFieldAnnotation.class);
        System.out.println(myFieldAnnotation.desc()+" "+myFieldAnnotation.uri());
        //反射的方式解析类和方法
        //parseMethod(TestAnnotation.class);
        parseClass(TestAnnotation.class);
    }

    //通过反射解析注解
    /**
     * 解析方法注解
     * @param clazz
     * @param <T>
     */
    public static <T> void parseMethod(Class<T> clazz){
        try {
            T obj = clazz.newInstance();
            for (Method method : clazz.getDeclaredMethods()) {
                MyAnnotationForTest.MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyAnnotationForTest.MyMethodAnnotation.class);
                if (myMethodAnnotation != null) {
                    try {
                        //通过反射调用带有此注解的方法
                        method.invoke(obj, myMethodAnnotation.uri());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> void parseClass(Class<T> clazz) {
        MyAnnotationForTest.MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyAnnotationForTest.MyClassAnnotation.class);
        if (myClassAnnotation != null) {
            System.out.println("class info : " + myClassAnnotation.desc());
        }
    }
}
