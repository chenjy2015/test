package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.ValidUtils;
import com.example.chenjy.myapplication.adapter.holder.HolderHelper;

import androidx.appcompat.app.AppCompatDialog;


public class InputConfirmDialogHelper extends AbstractDialogHelper {

    InputConfirmDialogHelper() {
        super();
    }

    InputConfirmDialogHelper(AbstractDialogHelper helper) {
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
        mDialogClickListener = helper.mDialogClickListener;
    }

    @Override
    protected Dialog createDialog(Activity activity) {
        return new AppCompatDialog(activity, R.style.Dialog);
    }


    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_confirm, null);
        final HolderHelper helper = new HolderHelper(view);
        helper.<TextView>findView(R.id.tv_title, tv -> {
            if (ValidUtils.isValid(mTitle)) {
                tv.setText(mTitle);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        });
        helper.<TextView>findView(R.id.tv_positive, btn -> {
            if (ValidUtils.isValid(mPositiveText)) {
                btn.setText(mPositiveText);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(v -> {
                    helper.<TextView>findView(R.id.et_content, etContent -> {
                                if (mDialogClickListener != null) {
                                    mDialogClickListener.onClick(dialog, etContent.getText().toString(), -1);
                                }
                            }
                    );
                });
            } else {
                btn.setVisibility(View.GONE);
            }
        });
        helper.<TextView>findView(R.id.tv_negative, btn -> {
            if (ValidUtils.isValid(mNegativeText)) {
                btn.setText(mNegativeText);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(v ->{
                    if (mNegativeOnClickListener != null) {
                        mNegativeOnClickListener.onClick(dialog,-1);
                    }
                });
            } else {
                btn.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
