package com.example.chenjy.myapplication.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

import com.example.chenjy.myapplication.ChatEmojiBean;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;


public class EmojiHelper {

    private Context mContext;
    private int offset = 10;
    /**
     * 小点的表情图片获取
     */
    private Html.ImageGetter faceGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable d = null;
            // 显示表情
            int sourceID = ChatEmojiBean.find(source);
            if (sourceID != -1) {
                d = mContext.getResources().getDrawable(sourceID);

                int width = d.getIntrinsicWidth();
                int height = d.getIntrinsicHeight();

                if ((width > 120) || (height > 120)) {
                    width = width / 6 + 10 + offset;
                    height = height / 6 + 10 + offset;
                } else {
                    width = width / 2;
                    height = height / 2;
                }

                d.setBounds(0, 0, width, height);
            }
            return d;
        }
    };

    public EmojiHelper(Context context) {
        this(context, 0);
    }

    public EmojiHelper(Context context, int offset) {
        this.mContext = context;
        this.offset = offset;
    }

    public static String box(String emoji) {
        return String.format("[/%s]", emoji);
    }

    public static boolean isEmojiCustom(String content) {
        return EmojiHelper.findEmojiIdByContent(content) != -1;
    }

    public static int findEmojiIdByContent(String message) {
        return ChatEmojiBean.findEmojiCustom(EmojiHelper.getEmojiContent(message));
    }

    /**
     * 获取转义符图片内容
     *
     * @return
     */
    private static String getEmojiContent(String emoji) {
        String regxp_img = "\\[/(\\S*?)]";
        Pattern p = Pattern.compile(regxp_img);
        Matcher m = p.matcher(emoji);
        boolean flag = m.find();
        if (flag) {
            String findStr = m.group(1);
            return findStr;
        }
        return emoji;
    }

    public static String formatEmojiContent(String key, String url) {
        return String.format("{\"type\":\"%s\",\"key\":\"%s\",\"url\":\"%s\"}", "emojiMsg", key, url);
    }

    public Spanned flat(String message) {
        message = message.replace("&", "&amp;").replace(" ", "&nbsp;");
        message = message.replace("<", "&lt;").replace(">", "&gt;");
        message = message.replace("\r\n", "<br>");
        message = message.replace("\r", "<br>").replace("\n", "<br>");
        return Html.fromHtml(
                findEmoticon(message)
                , faceGetter
                , null);
    }

    /**
     * 获取表情符号
     *
     * @param message
     * @return
     */
    public String findEmoticon(@NonNull String message) {
        Map<String, String> emojiMap = new HashMap<String, String>();
        String emojiMsg = message;
        final String regex = "\\[/[^\\]]+]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(message);
        boolean flag = m.find();
        while (flag) {
            String findStr = m.group();
            String emojiStr = getEmojiContent(findStr);
            // 包含
            if (ChatEmojiBean.find(emojiStr) != -1) {
                String boxEmoji = "<img src=\"" + emojiStr + "\"/>";
                emojiMap.put(findStr, boxEmoji);
            }
            flag = m.find();
        }
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojiMsg = emojiMsg.replace(entry.getKey(), entry.getValue());
        }
        return emojiMsg;
    }
}
