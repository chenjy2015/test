package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import com.example.chenjy.myapplication.dialog.callback.DialogClickListener;

/**
 * Created by melorin on 2017/10/27.
 */
public interface IDialogHelper {

    IDialogHelper canceledOnTouchOutside(boolean canceledOnTouchOutside);

    IDialogHelper cancelable(boolean cancelable);

    IDialogHelper onDismissListener(DialogInterface.OnDismissListener dismissListener);

    IDialogHelper title(String title);

    IDialogHelper message(String message);

    IDialogHelper positiveButton(String positiveText);

    IDialogHelper negativeButton(String negativeText);

    IDialogHelper positiveClickListener(DialogInterface.OnClickListener listener);

    IDialogHelper negativeClickListener(DialogInterface.OnClickListener listener);

    <T> IDialogHelper onDialogClickListener(DialogClickListener<T> dismissListener);

    BottomDialogHelper bottom();

    ConfirmDialogHelper confirm();

    InputConfirmDialogHelper inputConfirm();

    IosBottomDialogHelper iosBottom();

    Dialog show(Activity activity);

}
