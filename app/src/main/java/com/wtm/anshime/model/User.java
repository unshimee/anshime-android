package com.wtm.anshime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    private String userId;
    private String gender;
    private String email;
    @SerializedName("username")
    private String userName;
    private String authToken;
    private String refreshToken;

    protected User(Parcel in) {
        userId = in.readString();
        gender = in.readString();
        email = in.readString();
        userName = in.readString();
        authToken = in.readString();
        refreshToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(authToken);
        dest.writeString(refreshToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
