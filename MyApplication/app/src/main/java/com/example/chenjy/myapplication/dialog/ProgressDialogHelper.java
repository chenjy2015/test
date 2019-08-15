package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.chenjy.myapplication.R;


/**
 * Created by melorin on 2017/10/27.
 */

public class ProgressDialogHelper extends AbstractDialogHelper {

    @Override
    protected Dialog createDialog(Activity activity) {
        Dialog progressDialog = new ProgressDialog(activity, R.style.Dialog);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        progressDialog.setMessage(mMessage);
        return progressDialog;
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_progress_default, null);
        ((TextView) layout.findViewById(R.id.tv_message)).setText(mMessage);
        return layout;
    }
}