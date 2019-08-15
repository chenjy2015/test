package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

/**
 * Create by Melorin on 2018/6/8
 */
public class AlertDialogHelper extends AbstractDialogHelper {

    @Override
    protected Dialog createDialog(Activity activity) {
        return new AlertDialog.Builder(activity)
                .setMessage(mMessage)
                .setPositiveButton(mPositiveText, mPositiveOnClickListener)
                .setNegativeButton(mNegativeText, mNegativeOnClickListener)
                .create();
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        return null;
    }
}
