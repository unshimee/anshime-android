package com.wtm.anshime.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String gender;
    private String username;
    private String kakaoId;
    private String createdAt;

    public User(){ }

    public User(int id, String gender, String username, String kakaoId, String createdAt) {
        this.id = id;
        this.gender = gender;
        this.username = username;
        this.kakaoId = kakaoId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    protected User(Parcel in) {
        id = in.readInt();
        gender = in.readString();
        username = in.readString();
        kakaoId = in.readString();
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(gender);
        dest.writeString(username);
        dest.writeString(kakaoId);
        dest.writeString(createdAt);
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
