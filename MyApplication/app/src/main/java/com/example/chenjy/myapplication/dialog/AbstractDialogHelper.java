package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.chenjy.myapplication.dialog.callback.DialogClickListener;

/**
 * Created by melorin on 2017/10/27.
 */
public abstract class AbstractDialogHelper implements IDialogHelper {

    protected String mTitle;
    protected String mMessage;
    protected String mPositiveText;
    protected String mNegativeText;
    protected boolean mCanceledOnTouchOutside = true;
    protected boolean mCancelable = true;
    protected DialogInterface.OnClickListener mPositiveOnClickListener;
    protected DialogInterface.OnClickListener mNegativeOnClickListener;
    protected DialogInterface.OnDismissListener mOnDismissListener;

    //需要有返回值。
    protected DialogClickListener mDialogClickListener;
    @Override
    public IDialogHelper canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    @Override
    public IDialogHelper cancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        return this;
    }

    @Override
    public IDialogHelper onDismissListener(Dialog.OnDismissListener dismissListener) {
        this.mOnDismissListener = dismissListener;
        return this;
    }

    @Override
    public IDialogHelper title(String title) {
        this.mTitle = title;
        return this;
    }

    @Override
    public IDialogHelper message(String message) {
        this.mMessage = message;
        return this;
    }

    @Override
    public IDialogHelper positiveButton(String positiveText) {
        this.mPositiveText = positiveText;
        return this;
    }

    @Override
    public IDialogHelper negativeButton(String negativeText) {
        this.mNegativeText = negativeText;
        return this;
    }

    @Override
    public IDialogHelper positiveClickListener(DialogInterface.OnClickListener listener) {
        this.mPositiveOnClickListener = listener;
        return this;
    }

    @Override
    public IDialogHelper negativeClickListener(DialogInterface.OnClickListener listener) {
        this.mNegativeOnClickListener = listener;
        return this;
    }

    @Override
    public IDialogHelper onDialogClickListener(DialogClickListener dismissListener) {
        this.mDialogClickListener = dismissListener;
        return this;
    }

    @Override
    public BottomDialogHelper bottom() {
        return new BottomDialogHelper(this);
    }

    @Override
    public InputConfirmDialogHelper inputConfirm() {
        return new InputConfirmDialogHelper(this);
    }

    @Override
    public ConfirmDialogHelper confirm() {
        return new ConfirmDialogHelper(this);
    }

    public IosBottomDialogHelper iosBottom() {
        return new IosBottomDialogHelper(this);
    }

    @Override
    public Dialog show(Activity activity) {
        Dialog dialog = createDialog(activity);
        View view = createDialogView(activity, dialog);
        dialog.setTitle(mTitle);
        dialog.setCancelable(mCancelable);
        dialog.setCanceledOnTouchOutside(mCancelable && mCanceledOnTouchOutside);
        if (null != mOnDismissListener) {
            dialog.setOnDismissListener(mOnDismissListener);
        }
        dialog.show();
        if (view != null) {
            dialog.setContentView(view);
            setDialogAttrs(activity,dialog);
            dialog.show();
        }
        return dialog;
    }
    protected void setDialogAttrs(Activity activity,Dialog dialog){

    }
    /**
     * 创建对话框
     *
     * @param activity
     * @return
     */
    protected abstract Dialog createDialog(Activity activity);

    /**
     * 创建对话框View
     *
     * @param context activity
     * @param dialog
     * @return
     */
    protected abstract View createDialogView(Activity context, Dialog dialog);
}
