package com.example.gaoshengnan.mytestforgit.json;

/**
 * Description:
 *
 * @author: gaoshengnan
 * @email: gaoshengnan@meituan.com
 * Date: 2018/3/12
 */

public class User {

    public String name;
    public int age;
    public String emailAddress;

    public User(String name,int age,String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "name:"+name+","+"age:"+age+","+"emailAddress:"+emailAddress+".";
    }
}
