package com.wtm.anshime.api;

import com.wtm.anshime.model.AuthBody;
import com.wtm.anshime.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("auth/kakao-signin/")
    Call<AuthResponse> signInKakao(@Body AuthBody authBody);

}
