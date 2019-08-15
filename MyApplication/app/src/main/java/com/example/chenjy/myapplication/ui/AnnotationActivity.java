package com.example.chenjy.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.annotation.AutoWired;
import com.example.chenjy.myapplication.annotation.bean.Female;
import com.example.chenjy.myapplication.annotation.bean.Male;
import com.example.chenjy.myapplication.annotation.process.AutoWiredProcess;
import com.example.chenjy.myapplication.annotation.BindView;
import com.example.chenjy.myapplication.annotation.DynamicIntentKey;
import com.example.chenjy.myapplication.annotation.process.ButterKnifeProcess;
import com.example.chenjy.myapplication.annotation.process.DynamicUtil;
import com.example.chenjy.myapplication.bean.Car;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnnotationActivity extends AppCompatActivity {

    // 动态 初始化
    @AutoWired
    Car car;

    // 标记 获取intent传值
    @DynamicIntentKey("key_name")
    private String dynamicName;

    // 根据ID找到view 省去了findviewById过程
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;

    Male male;
    Female female;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ButterKnifeProcess.bind(this);
        AutoWiredProcess.bind(this);
        DynamicUtil.inject(this);


        new Handler().postDelayed(() -> ToastUtils.showShort(car.toString()), 10);
        new Handler().postDelayed(() -> tvTitle.setText(dynamicName), 10);
        new Handler().postDelayed(() -> tvName.setText("welcome to china"),10);
    }
}
