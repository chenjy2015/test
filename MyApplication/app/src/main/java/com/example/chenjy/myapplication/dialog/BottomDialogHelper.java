package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.ValidUtils;
import com.example.chenjy.myapplication.action.IAction1;
import com.example.chenjy.myapplication.adapter.SingleAdapter;
import com.example.chenjy.myapplication.dialog.bean.DialogMenu;
import com.example.chenjy.myapplication.dialog.holder.MenuHolder;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by Melorin on 2018/12/26
 */
public class BottomDialogHelper extends AbstractDialogHelper {

    private List<DialogMenu> mDialogMenuList;
    private String mHint;
    private IAction1<DialogMenu> mOnMenuSelect;

    public BottomDialogHelper() {
        super();
    }

    BottomDialogHelper(AbstractDialogHelper helper) {
        super();
        mTitle = helper.mTitle;
        mMessage = helper.mMessage;
        mPositiveText = helper.mPositiveText;
        mNegativeText = helper.mNegativeText;
        mCanceledOnTouchOutside = helper.mCanceledOnTouchOutside;
        mCancelable = helper.mCancelable;
        mPositiveOnClickListener = helper.mPositiveOnClickListener;
        mNegativeOnClickListener = helper.mNegativeOnClickListener;
        mOnDismissListener = helper.mOnDismissListener;
    }

    public BottomDialogHelper hint(String hint) {
        this.mHint = hint;
        return this;
    }

    public BottomDialogHelper menu(DialogMenu... menus) {
        this.mDialogMenuList = new ArrayList<>(Arrays.asList(menus));
        return this;
    }

    public BottomDialogHelper onMenuSelect(IAction1<DialogMenu> onMenuSelect) {
        this.mOnMenuSelect = onMenuSelect;
        return this;
    }

    @Override
    protected Dialog createDialog(Activity activity) {
        return new BottomSheetDialog(activity);
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_chat_bottom, null);
        TextView tvHint = view.findViewById(R.id.tv_hint);
        if (ValidUtils.isValid(mHint)) {
            tvHint.setText(mHint);
            tvHint.setVisibility(View.VISIBLE);
        } else {
            tvHint.setVisibility(View.GONE);
        }
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> DialogHelper.dismiss(context, dialog));
        RecyclerView rvMenu = view.findViewById(R.id.rv_menu);
        SingleAdapter<DialogMenu> adapter = new SingleAdapter(context, mDialogMenuList, R.layout.holder_dialog_menu, MenuHolder.class);
        adapter.setOnOperationListener((type, position, view1, model) -> {
            DialogHelper.dismiss(context, dialog);
            if (mOnMenuSelect != null) {
                mOnMenuSelect.invoke(model);
            }
        });
        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    public Dialog show(Activity activity) {
        Dialog dialog = createDialog(activity);
        View view = createDialogView(activity, dialog);
        if (view != null) {
            dialog.setContentView(view);
        }
        dialog.show();
        return dialog;
    }
}