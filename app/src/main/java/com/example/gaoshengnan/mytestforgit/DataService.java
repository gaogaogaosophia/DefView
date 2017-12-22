package com.example.gaoshengnan.mytestforgit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gaoshengnan on 2017/12/20.
 */

public interface DataService {
    /**
     * 注解Body用于非表单请求？？
     * @param apiInfo
     * @return
     */
    @POST("client/shipper/getCarType")
    Call<ResponseBody> getData(@Body ApiInfo apiInfo);
}
