package com.xpf.aidl_example;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by x-sir on 2019.01.12 :)
 * Function:
 */
public class RectBean implements Parcelable {

    public int left;
    public int top;
    public int right;
    public int bottom;

    public static final Creator<RectBean> CREATOR = new Creator<RectBean>() {
        public RectBean createFromParcel(Parcel in) {
            return new RectBean(in);
        }

        public RectBean[] newArray(int size) {
            return new RectBean[size];
        }
    };

    public RectBean() {

    }

    private RectBean(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(left);
        out.writeInt(top);
        out.writeInt(right);
        out.writeInt(bottom);
    }

}
