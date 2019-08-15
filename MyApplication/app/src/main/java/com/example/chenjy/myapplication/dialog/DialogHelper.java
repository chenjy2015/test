package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.StringRes;

/**
 * Created by melorin on 2017/10/27.
 */

public class DialogHelper {

    private DialogHelper(int type) {
    }

    public static IDialogHelper create(DialogType type) {
        return getDialogHelper(type);
    }

    /**
     * @param type
     * @return
     */
    private static IDialogHelper getDialogHelper(DialogType type) {
        IDialogHelper helper;
        switch (type) {
            case PROGRESS:
                helper = new ProgressDialogHelper();
                break;
            case ALERT:
                helper = new AlertDialogHelper();
                break;
            case BOTTOM:
                helper = new BottomDialogHelper();
                break;
            case CONFIRM:
                helper = new ConfirmDialogHelper();
                break;
            case IOS_BOTTOM:
                helper = new IosBottomDialogHelper();
                break;
            case POPUP_AD:
                helper = new PopupAdDialogHelper();
                break;
            case INPUT_CONFIRM:
                helper = new InputConfirmDialogHelper();
                break;
            case BROWSER_SHARE:
                helper = new BrowserShareDialogHelper();
                break;
            default:
                helper = new DefaultDialogHelper();
                break;
        }
        return helper;
    }

    public static void dismiss(Activity activity, Dialog dialog) {
        if (activity != null && !activity.isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }

    public static Dialog simpleProgress(Activity activity, @StringRes int message) {
        return simpleProgress(activity, activity.getResources().getString(message));
    }

    public static Dialog simpleProgress(Activity activity, String message) {
        return simpleProgress(activity,message,true,true);
    }

    public static Dialog simpleProgress(Activity activity, String message,boolean cancelable,boolean canceledOnTouchOutside) {
        return DialogHelper.create(DialogType.PROGRESS)
                .message(message)
                .canceledOnTouchOutside(canceledOnTouchOutside)
                .cancelable(cancelable)
                .show(activity);
    }

    public static Dialog justAlert(Activity activity, @StringRes int message, @StringRes int positiveText) {
        return justAlert(activity, activity.getResources().getString(message), activity.getResources().getString(positiveText));
    }

    public static Dialog justAlert(Activity activity, String message, String positiveText) {
        return DialogHelper.create(DialogHelper.DialogType.ALERT)
                .message(message)
                .positiveButton(positiveText)
                .show(activity);
    }

    public enum DialogType {
        PROGRESS,
        ALERT,
        DEFAULT,
        BOTTOM,
        CONFIRM,
        IOS_BOTTOM,
        POPUP_AD,
        INPUT_CONFIRM,
        BROWSER_SHARE
    }
}
