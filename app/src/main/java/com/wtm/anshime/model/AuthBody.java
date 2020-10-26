package com.wtm.anshime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AuthBody implements Parcelable {
    private String email;
    private String gender;
    @SerializedName("kakao_id")
    private String kakaoId;
    @SerializedName("username")
    private String userName;

    @Override
    public String toString() {
        return "AuthBody{" +
                "email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", kakaoId='" + kakaoId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public AuthBody(String email, String gender, String kakaoId, String userName) {
        this.email = email;
        this.gender = gender;
        this.kakaoId = kakaoId;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public String getUserName() {
        return userName;
    }

    public AuthBody(Parcel in) {
        email = in.readString();
        gender = in.readString();
        kakaoId = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(kakaoId);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthBody> CREATOR = new Creator<AuthBody>() {
        @Override
        public AuthBody createFromParcel(Parcel in) {
            return new AuthBody(in);
        }

        @Override
        public AuthBody[] newArray(int size) {
            return new AuthBody[size];
        }
    };
}
