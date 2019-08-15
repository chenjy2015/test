package com.example.chenjy.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ProxyAction<T extends Object> implements Parcelable {

    public int action;

    public T object;

    public ProxyAction(int action, T object) {
        this.action = action;
        this.object = object;
    }


    protected ProxyAction(Parcel in) {
        action = in.readInt();
    }

    public static final Creator<ProxyAction> CREATOR = new Creator<ProxyAction>() {
        @Override
        public ProxyAction createFromParcel(Parcel in) {
            return new ProxyAction(in);
        }

        @Override
        public ProxyAction[] newArray(int size) {
            return new ProxyAction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(action);
    }

}
