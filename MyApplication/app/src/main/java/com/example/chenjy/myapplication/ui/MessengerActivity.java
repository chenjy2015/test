package com.example.chenjy.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.services.MessengerService;

public class MessengerActivity extends AppCompatActivity {

    private boolean mBound;
    private Messenger mMessenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(serviceConnection);
            mBound = false;
        }
    }

    public void sayHello(View v){
        if(!mBound){
            return;
        }
        Message msg = Message.obtain(null, 0 , 0, 0);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

