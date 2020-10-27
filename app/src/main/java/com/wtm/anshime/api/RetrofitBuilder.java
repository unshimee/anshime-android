package com.wtm.anshime.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wtm.anshime.utils.Constants.BASE_URL;
import static com.wtm.anshime.utils.Constants.LOCATIONS_API_BASE_URL;

public class RetrofitBuilder {
    private static RetrofitBuilder instance;

    public static RetrofitBuilder getInstance() {
        if(instance == null){
            instance = new RetrofitBuilder();
        }
        return instance;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(LOCATIONS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private Retrofit baseRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public LocationApiService locationApiService = retrofit.create(LocationApiService.class);

    public AuthApiService authApiService = baseRetrofit.create(AuthApiService.class);

    public HomeApiService homeApiService = baseRetrofit.create(HomeApiService.class);
}
