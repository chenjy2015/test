package com.example.chenjy.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MessengerService extends Service {


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "hello, trampcr", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

        Messenger mMessenger = new Messenger(new IncomingHandler());

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
            return mMessenger.getBinder();
        }
}
