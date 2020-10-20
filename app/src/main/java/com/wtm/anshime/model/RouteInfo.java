package com.wtm.anshime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteInfo implements Parcelable {

    private int id;
    private int userId;
    private String departAddress;
    private String arriveAddress;
    private String departAt;
    private String arriveAt;
    private String transportMethod;
    private String finishedAt;
    private String createdAt;
    private String updatedAt;

    public RouteInfo(){}

    public RouteInfo(int id, int userId, String departAddress, String arriveAddress,
                     String departAt, String arriveAt, String transportMethod, String finishedAt,
                     String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.departAddress = departAddress;
        this.arriveAddress = arriveAddress;
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.transportMethod = transportMethod;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected RouteInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        departAddress = in.readString();
        arriveAddress = in.readString();
        departAt = in.readString();
        arriveAt = in.readString();
        transportMethod = in.readString();
        finishedAt = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDepartAddress() {
        return departAddress;
    }

    public String getArriveAddress() {
        return arriveAddress;
    }

    public String getDepartAt() {
        return departAt;
    }

    public String getArriveAt() {
        return arriveAt;
    }

    public String getTransportMethod() {
        return transportMethod;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(departAddress);
        dest.writeString(arriveAddress);
        dest.writeString(departAt);
        dest.writeString(arriveAt);
        dest.writeString(transportMethod);
        dest.writeString(finishedAt);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteInfo> CREATOR = new Creator<RouteInfo>() {
        @Override
        public RouteInfo createFromParcel(Parcel in) {
            return new RouteInfo(in);
        }

        @Override
        public RouteInfo[] newArray(int size) {
            return new RouteInfo[size];
        }
    };
}
