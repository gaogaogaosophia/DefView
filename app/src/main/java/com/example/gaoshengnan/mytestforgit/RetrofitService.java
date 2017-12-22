package com.example.gaoshengnan.mytestforgit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gaoshengnan on 2017/12/20.
 *
 * Retrofit将网络请求转变成了java interface的形式
 *
 * getUser：我的wiki主页
 * https://wiki.sankuai.com/display/~gaoshengnan
 * getPage：我的《埋点》博文
 * https://wiki.sankuai.com/pages/viewpage.action?pageId=1255723626
 */

public interface RetrofitService {

        /**
         * GET中的字符串表示相对URL
         * BaseUrl需要以/结尾，这样每个接口定义的相对URL就不需要以/开始
         * @param username
         * @return
         */
        @GET("display/~{username}")
        Call<ResponseBody> getUser(@Path("username") String username);

        @GET("pages/viewpage.action")
        Call<ResponseBody> getPage(@Query("pageId") long pageId);
}
