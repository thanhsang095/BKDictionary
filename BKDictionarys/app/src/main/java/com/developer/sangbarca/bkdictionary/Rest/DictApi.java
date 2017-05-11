package com.developer.sangbarca.bkdictionary.Rest;

import com.developer.sangbarca.bkdictionary.Models.BackUpResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nhat on 07/05/2017.
 */

public interface DictApi {
    @GET("backup")
    Call<BackUpResponse> backupData();


}
