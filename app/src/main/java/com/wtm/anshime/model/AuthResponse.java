package com.wtm.anshime.model;


import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    private String [] errors;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

}
