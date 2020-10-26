package com.wtm.anshime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthResponse implements Parcelable {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    private List<String> errors;

    public AuthResponse(){}

    public AuthResponse(String accessToken, String refreshToken, List<String> errors) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errors = errors;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public List<String> getErrors() {
        return errors;
    }

    protected AuthResponse(Parcel in) {
        accessToken = in.readString();
        refreshToken = in.readString();
        errors = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeString(refreshToken);
        dest.writeStringList(errors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthResponse> CREATOR = new Creator<AuthResponse>() {
        @Override
        public AuthResponse createFromParcel(Parcel in) {
            return new AuthResponse(in);
        }

        @Override
        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };
}
