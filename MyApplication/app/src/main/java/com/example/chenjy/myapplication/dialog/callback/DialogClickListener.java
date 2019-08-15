package com.example.chenjy.myapplication.dialog.callback;

import android.content.DialogInterface;

public interface DialogClickListener<T>{
    void onClick(DialogInterface dialog, T info, int which);
}
