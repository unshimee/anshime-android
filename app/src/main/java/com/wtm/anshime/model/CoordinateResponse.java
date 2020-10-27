package com.wtm.anshime.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
* {
1depth	string

최상위 지역명(시/군/면)
2depth	string

차상위 지역명(구/면)
3depth	string

최하위 지역명(동/읍/리
}
* */
public class CoordinateResponse implements Parcelable {

    @SerializedName("1depth")
    private String depth1;

    @SerializedName("2depth")
    private String depth2;

    @SerializedName("3depth")
    private String depth3;

    @SerializedName("errors")
    private List<String> errors;

    protected CoordinateResponse(Parcel in) {
        depth1 = in.readString();
        depth2 = in.readString();
        depth3 = in.readString();
        errors = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(depth1);
        dest.writeString(depth2);
        dest.writeString(depth3);
        dest.writeStringList(errors);
    }

    public CoordinateResponse(){}

    public CoordinateResponse(String depth1, String depth2, String depth3) {
        this.depth1 = depth1;
        this.depth2 = depth2;
        this.depth3 = depth3;
    }

    public CoordinateResponse(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoordinateResponse> CREATOR = new Creator<CoordinateResponse>() {
        @Override
        public CoordinateResponse createFromParcel(Parcel in) {
            return new CoordinateResponse(in);
        }

        @Override
        public CoordinateResponse[] newArray(int size) {
            return new CoordinateResponse[size];
        }
    };

    public String getDepth1() {
        return depth1;
    }

    public String getDepth2() {
        return depth2;
    }

    public String getDepth3() {
        return depth3;
    }

    public List<String> getErrors() {
        return errors;
    }
}
