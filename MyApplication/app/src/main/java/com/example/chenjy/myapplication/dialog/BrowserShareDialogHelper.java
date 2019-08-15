package com.example.chenjy.myapplication.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.action.IAction1;
import com.example.chenjy.myapplication.adapter.SingleAdapter;
import com.example.chenjy.myapplication.dialog.bean.DialogMenu;
import com.example.chenjy.myapplication.dialog.holder.BrowserShareHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by Melorin on 2018/12/26
 */
public class BrowserShareDialogHelper extends AbstractDialogHelper {

    private String mTitle;
    @ColorInt
    private int mNegativeColor = 0xFF6A6E72;
    private List<DialogMenu> mDialogMenuList;
    private IAction1<DialogMenu> mOnMenuSelect;

    public BrowserShareDialogHelper() {
        super();
    }


    BrowserShareDialogHelper(AbstractDialogHelper helper) {
        super();
        mTitle = helper.mTitle;
        mMessage = helper.mMessage;
        mPositiveText = helper.mPositiveText;
        mNegativeText = helper.mNegativeText;
        mCanceledOnTouchOutside = helper.mCanceledOnTouchOutside;
        mCancelable = helper.mCancelable;
        mNegativeOnClickListener = helper.mNegativeOnClickListener;
        mOnDismissListener = helper.mOnDismissListener;
    }

    @Override
    public IDialogHelper positiveButton(String positiveText) {
        return this;
    }

    @Override
    public IDialogHelper positiveClickListener(DialogInterface.OnClickListener listener) {
        return this;
    }

    public BrowserShareDialogHelper title(String title) {
        this.mTitle = title;
        return this;
    }

    public BrowserShareDialogHelper negativeColor(@ColorInt int color) {
        this.mNegativeColor = color;
        return this;
    }

    public BrowserShareDialogHelper menu(DialogMenu... menus) {
        this.mDialogMenuList = new ArrayList<>(Arrays.asList(menus));
        return this;
    }

    public BrowserShareDialogHelper onMenuSelect(IAction1<DialogMenu> onMenuSelect) {
        this.mOnMenuSelect = onMenuSelect;
        return this;
    }

    @Override
    protected Dialog createDialog(Activity activity) {
        return new Dialog(activity, R.style.ActionSheet);
    }

    @Override
    protected View createDialogView(Activity context, Dialog dialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_browser_share, null);
        view.setMinimumWidth(10000);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = -10000;
        lp.gravity = Gravity.BOTTOM;
        dialog.onWindowAttributesChanged(lp);

        TextView tvNegative = view.findViewById(R.id.tv_negative);
        tvNegative.setText(mNegativeText);
        tvNegative.setTextColor(mNegativeColor);
        tvNegative.setOnClickListener(v -> {
            DialogHelper.dismiss(context, dialog);
            if (mNegativeOnClickListener != null) {
                mNegativeOnClickListener.onClick(dialog, -1);
            }
        });

        view.findViewById(R.id.v_divider).setVisibility(View.GONE);
        RecyclerView rvMenu = view.findViewById(R.id.rv_menu);
        SingleAdapter<DialogMenu> adapter = new SingleAdapter(context, mDialogMenuList, R.layout.holder_dialog_browser_share_menu, BrowserShareHolder.class);
        adapter.setOnOperationListener((type, position, view1, model) -> {
            DialogHelper.dismiss(context, dialog);
            if (mOnMenuSelect != null) {
                mOnMenuSelect.invoke(model);
            }
        });
        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new GridLayoutManager(context,3));
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
