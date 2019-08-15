package com.example.chenjy.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class SessionTextSizeVO implements Parcelable {

    String name;
    String size;
    boolean checked;

    public SessionTextSizeVO(String name, String size, boolean checked) {
        this.name = name;
        this.size = size;
        this.checked = checked;
    }

    protected SessionTextSizeVO(Parcel in) {
        name = in.readString();
        size = in.readString();
        checked = in.readByte() != 0;
    }

    public static final Creator<SessionTextSizeVO> CREATOR = new Creator<SessionTextSizeVO>() {
        @Override
        public SessionTextSizeVO createFromParcel(Parcel in) {
            return new SessionTextSizeVO(in);
        }

        @Override
        public SessionTextSizeVO[] newArray(int size) {
            return new SessionTextSizeVO[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeByte((byte) (checked ? 1 : 0));
    }
}
