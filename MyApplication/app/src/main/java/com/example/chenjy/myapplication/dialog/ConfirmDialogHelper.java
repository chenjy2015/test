package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.ValidUtils;
import com.example.chenjy.myapplication.adapter.holder.HolderHelper;

import androidx.appcompat.app.AppCompatDialog;

/**
 * Create by Melorin on 2019/1/21
 */
public class ConfirmDialogHelper extends AbstractDialogHelper {

    protected int mCountDown;

    ConfirmDialogHelper() {
        super();
    }

    ConfirmDialogHelper(AbstractDialogHelper helper) {
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

    public ConfirmDialogHelper countDown(int second) {
        this.mCountDown = second;
        return this;
    }

    @Override
    protected Dialog createDialog(Activity activity) {
        return new AppCompatDialog(activity, R.style.Dialog);
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        final Handler handler = new Handler();
        final HolderHelper helper = new HolderHelper(view);
        helper.<TextView>findView(R.id.tv_title, tv -> {
            if (ValidUtils.isValid(mTitle)) {
                tv.setText(mTitle);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        });
        helper.<TextView>findView(R.id.tv_message, tv -> {
            if (ValidUtils.isValid(mMessage)) {
                tv.setText(mMessage);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        });
        helper.<Button>findView(R.id.btn_positive, btn -> {
            if (ValidUtils.isValid(mPositiveText)) {
                btn.setText(mPositiveText);
                btn.setBackgroundColor(0xFF999999);
                btn.setVisibility(View.VISIBLE);
            } else {
                btn.setVisibility(View.GONE);
            }
        });
        helper.<Button>findView(R.id.btn_negative, btn -> {
            if (ValidUtils.isValid(mNegativeText)) {
                btn.setText(mNegativeText);
                btn.setOnClickListener(v -> {
                    if (mNegativeOnClickListener != null) {
                        mNegativeOnClickListener.onClick(dialog, -1);
                    }
                });
                btn.setVisibility(View.VISIBLE);
            } else {
                btn.setVisibility(View.GONE);
            }
        });
        if (mCountDown > 0) {
            dialog.setOnShowListener(__ -> {
                int i = 0;
                while (i < mCountDown) {
                    String text = String.format("%s(%s)", mPositiveText, mCountDown - i);
                    long delay = i * 1000L;
                    handler.postDelayed(() -> helper.<Button>findView(R.id.btn_positive, btn -> btn.setText(text)), delay);
                    i++;
                }
                long delay = i * 1000L;
                handler.postDelayed(() -> helper.<Button>findView(R.id.btn_positive, btn -> {
                            btn.setText(mPositiveText);
                            btn.setBackgroundColor(0xFF1087F7);
                            btn.setOnClickListener(v -> {
                                if (mPositiveOnClickListener != null) {
                                    mPositiveOnClickListener.onClick(dialog, -1);
                                }
                            });
                        })
                        , delay);
            });
        }
        dialog.setOnDismissListener(__ -> handler.removeCallbacksAndMessages(null));
        return view;
    }
}
