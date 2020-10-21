package com.wtm.anshime.api;

import com.wtm.anshime.model.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationApiService {
    @GET("coord2jibun.do")
    Call<Address> getAddressFromGps(@Query("x") String x,
                                    @Query("y") String y,
                                    @Query("output") String output,
                                    @Query("apiKey") String apiKey, @Query("domain") String domain);
}
