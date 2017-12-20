package com.example.gaoshengnan.mytestforgit;

/**
 * Created by gaoshengnan on 2017/12/20.
 * 一个如下的数据结构
 * {
     "apiInfo":{
     "name": "WuXiaolong",
     "key": "666"
     }
 }
 */

public class ApiInfo {

    private ApiInfoBean apiInfoBean;

    public ApiInfoBean getApiInfoBean() {
        return this.apiInfoBean;
    }

    public void setApiInfo(ApiInfoBean apiInfoBean) {
        this.apiInfoBean = apiInfoBean;
    }
    public class ApiInfoBean {
        private String name;
        private String key;
        private String getName() {
            return this.name;
        }
        public String getKey() {
            return this.key;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setKey(String key) {
            this.key = key;
        }
    }
}
