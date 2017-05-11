package com.developer.sangbarca.bkdictionary.Rest;

import com.developer.sangbarca.bkdictionary.Models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nhat on 06/05/2017.
 */

public interface UserApi {
    @FormUrlEncoded
    @POST("user/login")
    Call<UserResponse> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/logout")
    Call<UserResponse> logout(@Field("id") String id);

}
