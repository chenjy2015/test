package com.example.chenjy.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenjy.myapplication.IMyAidlInterface;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.services.AIDLService;

public class AIDLDemoActivity extends AppCompatActivity {

    private IMyAidlInterface myAidlInterface;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidldemo);

        Intent intent = new Intent(AIDLDemoActivity.this, AIDLService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void start(View view) {
        try {
            String name = myAidlInterface.getName("I'm click");
            ToastUtils.showShort("name = " + name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
