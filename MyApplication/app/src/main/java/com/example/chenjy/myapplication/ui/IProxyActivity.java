package com.example.chenjy.myapplication.ui;

import android.app.Activity;

import com.example.chenjy.myapplication.ProxyAction;

public abstract class IProxyActivity extends Activity {

    ProxyAction iProxyAction;

    public abstract void initProxy();

    public abstract void parseAction();

}
