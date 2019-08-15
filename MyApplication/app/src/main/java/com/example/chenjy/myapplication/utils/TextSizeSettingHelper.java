package com.example.chenjy.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.SessionTextSizeVO;
import com.example.chenjy.myapplication.base.activity.BaseActivity;
import com.example.chenjy.myapplication.binder.SessionTextSizeBinder;
import com.example.chenjy.myapplication.databinding.DialogSettingTextSizeBinding;
import com.example.chenjy.myapplication.dialog.DialogHelper;
import com.example.chenjy.myapplication.utils.bus.Bus;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.drakeet.multitype.MultiTypeAdapter;

public class TextSizeSettingHelper {

    private Activity act;

    private List<SessionTextSizeVO> sizeVOS;

    private String[] names;

    private String[] sizes;

    private View view;

    private DialogSettingTextSizeBinding viewDataBinding;

    private MultiTypeAdapter adapter;

    private SettingHelper settingHelper;

    private Dialog dialog;

    private Handler handler;

    public TextSizeSettingHelper(Activity context) {
        this.act = context;
        init(context);
        initView(context);
    }

    private void init(Context context) {
        if (handler == null) {
            handler = new Handler();
        }
        if (settingHelper == null) {
            settingHelper = new SettingHelper();
        }
        if (sizeVOS == null) {
            sizeVOS = new ArrayList<>();
        }
        sizeVOS.clear();
        if (names == null) {
            names = context.getResources().getStringArray(R.array.text_size_model);
            sizes = context.getResources().getStringArray(R.array.text_size_value);
        }
//        //从数据库中读取 默认选中位置
//        settingHelper.getSessionTextSize(s -> {
//            //如果没有设置过字号 默认设置一个 跟随系统
//            if (!ValidUtils.isValid(s)) {
//                settingHelper.setSessionTextSize(s, aBoolean -> {
//                    setData(sizes[0]);
//                    act.setTextSizeModel(Float.parseFloat(s));
//                });
//            } else {
//                setData(s);
//            }
//        });
        setData(sizes[0]);
    }

    private void setData(String defaultSize) {
        for (int i = 0; i < names.length; i++) {
            if (sizes[i].equals(defaultSize)) {
                sizeVOS.add(new SessionTextSizeVO(names[i], sizes[i], true));
//                act.setTextSizeModel(Float.parseFloat(sizes[i]));
            } else {
                sizeVOS.add(new SessionTextSizeVO(names[i], sizes[i], false));
            }
        }
    }

    private void initView(Context context) {
        if (adapter == null) {
            adapter = new MultiTypeAdapter();
            adapter.setItems(sizeVOS);
            adapter.register(SessionTextSizeVO.class, new SessionTextSizeBinder(this::onItemClick));
        }
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_setting_text_size, null);
            viewDataBinding = DataBindingUtil.bind(view);
            viewDataBinding.recycler.setAdapter(adapter);
            viewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(context));
            viewDataBinding.recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).size(1).build());
        }
    }

    public void show() {
        init(act);
        if (dialog == null) {
            initView(act);
            dialog = DialogHelper.create(DialogHelper.DialogType.ALERT).show(act);
            dialog.setContentView(view);
        }
        handler.postDelayed(() -> {
            adapter.notifyDataSetChanged();
            dialog.show();
        }, 300);
    }

    private void onItemClick(int position) {
//        settingHelper.setSessionTextSize(sizeVOS.get(position).getSize(),
//                aBoolean -> {
//                    dialog.dismiss();
//                    new Handler().postDelayed(() -> showApplyRestartHint(position), 100);
//                });
        showApplyRestartHint(position);
    }


    private void showApplyRestartHint(int position) {
        DialogHelper.create(DialogHelper.DialogType.ALERT)
                .title("会话字号应用")
                .message("要字号设置生效，需要重启APP 是否确定重启？")
                .positiveButton(act.getString(R.string.confirm))
                .negativeButton(act.getString(R.string.cancel))
                .positiveClickListener((dialog, which) -> {
                    dialog.dismiss();
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        toggleChecked(position);
                        new Handler().postDelayed(() -> new SettingHelper().restartIntentApp(act), 100);
                    }
                })
                .show(act);
    }


    private void toggleChecked(int position) {
        for (int i = 0; i < sizeVOS.size(); i++) {
            if (i == position) {
                sizeVOS.get(position).setChecked(true);
//                act.setTextSizeModel(Float.parseFloat(sizeVOS.get(i).getSize()));
                Bus.post("SizeSetting", Float.parseFloat(sizeVOS.get(i).getSize()));
            } else {
                sizeVOS.get(i).setChecked(false);
            }
        }
    }
}
