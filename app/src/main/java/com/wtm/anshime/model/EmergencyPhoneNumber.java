package com.wtm.anshime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EmergencyPhoneNumber implements Parcelable {

    private int id;
    private int userId;
    private String phoneNumber;

    public EmergencyPhoneNumber(){}

    public EmergencyPhoneNumber(int id, int userId, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
    }

    protected EmergencyPhoneNumber(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EmergencyPhoneNumber> CREATOR = new Creator<EmergencyPhoneNumber>() {
        @Override
        public EmergencyPhoneNumber createFromParcel(Parcel in) {
            return new EmergencyPhoneNumber(in);
        }

        @Override
        public EmergencyPhoneNumber[] newArray(int size) {
            return new EmergencyPhoneNumber[size];
        }
    };
}
