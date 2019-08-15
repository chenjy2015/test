package com.example.chenjy.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.chenjy.myapplication.ProxyAction;
import com.example.chenjy.myapplication.contants.SettingConst;

public class ProxyActivity extends IProxyActivity {

    public static Intent getIntent(Context context, ProxyAction proxyAction) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(SettingConst.IProxyActionKey.action, proxyAction);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProxy();
        parseAction();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initProxy();
        parseAction();
    }

    @Override
    public void initProxy() {
        if (iProxyAction == null) {
            if (getIntent() != null) {
                iProxyAction = getIntent().getParcelableExtra(SettingConst.IProxyActionKey.action);
            }
        }
    }


    @Override
    public void parseAction() {
        switch (iProxyAction.action) {
            case SettingConst.IProxyActions.SORT_ACTIVITY:
                SortActivity.startActivity(this, (String[]) iProxyAction.object);
                break;

            case SettingConst.IProxyActions.VIDEO_PLAYER_ACTIVITY:
                VideoPlayerActivity.startActivity(this, (String) iProxyAction.object);
                break;
        }
        finish();
    }
}
