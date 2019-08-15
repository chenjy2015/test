package com.example.chenjy.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chenjy.myapplication.ActivityCollector;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseActivity;

public class LaunchModeActivity extends BaseActivity {


    public static int count = 0;
    private static boolean isStart = false;
    Button testBtn;

    public static void startActivity(Context context) {
        boolean isExist = ActivityCollector.isActivityExist(LaunchModeActivity.class);
        Log.d("LaunchActivity", "isExit : " + (isExist ? "true" : "false"));
        if (!isExist && !isStart) {
            Log.d("LaunchActivity", "LaunchModeActivity.startActivity count == " + (++count));
            context.startActivity(new Intent(context, LaunchModeActivity.class));
            isStart = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);

        testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LaunchActivity", "LaunchActivity is launch : " + ActivityCollector.isActivityExist(LaunchModeActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart = false;
    }
}
