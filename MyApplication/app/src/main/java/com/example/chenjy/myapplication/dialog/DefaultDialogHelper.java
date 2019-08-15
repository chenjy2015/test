package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import androidx.appcompat.app.AppCompatDialog;

/**
 * Created by melorin on 2017/10/27.
 */

public class DefaultDialogHelper extends AbstractDialogHelper {

    @Override
    protected Dialog createDialog(Activity activity) {
        Dialog dialog = new AppCompatDialog(activity);
        return dialog;
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        return null;
    }
}
