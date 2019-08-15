package com.example.chenjy.myapplication.livedata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.chenjy.myapplication.utils.L;

import androidx.lifecycle.LiveData;

public class NetworkLiveData extends LiveData<NetworkInfo> {
    private static final String TAG = "NetworkLiveData";

    private final Context mContext;
    static NetworkLiveData networkLiveData;
    private NetworkReceiver networkReceiver;
    private IntentFilter intentFilter;

    private NetworkLiveData(Context context) {
        mContext = context.getApplicationContext();
        networkReceiver = new NetworkReceiver();
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public static NetworkLiveData getInstance(Context context) {
        if (networkLiveData == null) {
            networkLiveData = new NetworkLiveData(context);
        }
        return networkLiveData;
    }

    @Override
    protected void onActive() {
        super.onActive();
        L.d(TAG, "onActive():");
        mContext.registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        L.d(TAG, "onInactive");
        mContext.unregisterReceiver(networkReceiver);
    }

    /**
     * 网络状态监听
     */
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            getInstance(context).setValue(networkInfo);
        }
    }
}
