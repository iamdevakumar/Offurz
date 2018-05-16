package com.shadowws.offurz.web;

import com.shadowws.offurz.Pojo.HomePageAll;
import com.shadowws.offurz.Pojo.Login;
import com.shadowws.offurz.Pojo.SellerRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Lenovo on 2/17/2018.
 */

public interface APIInterface {
    @GET("/login.php")
    Call<Login> LoginGet();

    @GET("/all_an.php")
    Call<HomePageAll> getAllproductsDetails();

    @Headers({"Accept: application/json"})
    @POST("/login.php")
    Call<SellerRegister> sellerRegisterPost(@Body SellerRegister sellerReg);

    @POST("/seller_register.php")
    @FormUrlEncoded
    Call<Login> savePost(@Field("username") String username,
                        @Field("password") String password);

}
