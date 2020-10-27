package com.wtm.anshime.api;


import com.wtm.anshime.model.CoordinateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// home/ 로 시작하는 서버 API 에 대한 인터페이스
public interface HomeApiService {

    @GET("home/convert-coord-to-address/")
    Call<CoordinateResponse> convertCoordinateToAddress(
            @Query("x") String x,
            @Query("y") String y
        );

}
