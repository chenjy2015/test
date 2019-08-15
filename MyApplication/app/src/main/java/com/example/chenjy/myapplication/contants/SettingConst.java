package com.example.chenjy.myapplication.contants;

public interface SettingConst {

    interface GeneralSettingModel {
        String new_msg_notify = "new_msg_notify";
        String voice_notify = "voice_notify";
        String vibration_notify = "vibration_notify";
        String sync_msg = "sync_msg";
        String receiver_mode = "Receiver mode";
        String login_out = "login_out";
        String login_account = "account";

    }

    interface SessionTextSizeModelKey {
        String text_size_model = "text_size_model";
    }

    interface SessionTextSizeModel {
        String flow_system = "1.0";
        String small = "0.9";
        String medium = "1.1";
        String big = "1.2";
        String super_big = "1.3";
    }

    interface SessionBgSelectKey {
        String session_bg_select_key = "session_bg_select_key";
    }

    interface SessionBgSelect {
        int SELECT_BG = 0; //选择背景图
        int SELECT_FROM_ALBUM = 1; //从手机相册选择
        int SELECT_FROM_CAMERA = 2; //拍照选择
    }

    interface LanguageType {
        String Chinese = "中文";
        String English = "English";
    }

    interface LanguageSettingKey {
        String language_set_key = "language";
    }

    interface LoginUserInfoKey {
        String login_user_info_key = "user_info";
    }

    interface IProxyActionKey {
        String action = "action";
    }

    interface IProxyActions {
        int SORT_ACTIVITY = 101;
        int VIDEO_PLAYER_ACTIVITY = 102;
    }

}
