package com.example.chenjy.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.example.chenjy.myapplication.ProxyAction;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.ui.ProxyActivity;

import java.lang.ref.SoftReference;
import java.util.Arrays;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    private int id;
    Bitmap large = null;
    private static SoftReference<Bitmap> largeIcon;
    private static final String GROUP_ID = "wx_rebuild_group_id";
    private static final String CHANNEL_ID = "wx_rebuild_channel_id";
    private static final String GROUP_NAME = "消息";
    private static final String CHANNEL_NAME = "普通消息";
    private static final String CHANNEL_DESCRIPTION = "会话消息通知";
    private static String channelId;
    private static String groupId;

    private NotificationUtils() {

    }

    public static NotificationUtils getInstance() {
        return SingletonInstance.INSTANCE;
    }

    private void init(Context context) {
        if (largeIcon != null) {
            large = largeIcon.get();
        }
        if (large == null) {
            large = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            largeIcon = new SoftReference<>(large);
        }
    }

    private NotificationManager createNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Pair<String, String> createChannelId() {
        String time = TimeUtils.millis2String(System.currentTimeMillis());
        //channelId要唯一
        channelId = CHANNEL_ID + time;
        //groupId要唯一
        groupId = GROUP_ID + time;
        return new Pair<>(channelId, groupId);
    }

    private void deleteLastChannel(NotificationManager manager) {
        if (channelId != null) {
            deleteChannel(manager, channelId);
        }
    }

    private void deleteChannel(NotificationManager manager, String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.deleteNotificationChannel(channelId);
        }
    }


    @SuppressLint("WrongConstant")
    public void notify(Context context, NotifyInfo notifyInfo) {
        init(context);

        NotificationManager manager = createNotificationManager(context);

        if (manager != null) {
            /**
             * 判断如果是大于8.0系统版本则需要对channelID 做一个删除操作 否则改变的属性不起作用 而低版本的时候不需要channel通道概念故不做操作
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //查找是否存在channelID通知 如果有先做删除操作 因为channelID是唯一的一旦创建 多次创建也属于同一个通道 故不能修改其属性
                deleteLastChannel(manager);
                //创建唯一通道 channelID和groupID
                createChannelId();
                //分组（可选）
                NotificationChannelGroup group = new NotificationChannelGroup(groupId, GROUP_NAME);
                //创建group
                manager.createNotificationChannelGroup(group);
                NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                //补充channel的含义（可选）
                channel.setDescription(CHANNEL_DESCRIPTION);
                //将渠道添加进组（先创建组才能添加）
                channel.setGroup(groupId);
                //声音提示
                if (notifyInfo.isVoice) {
                    channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);
                } else {
                    channel.setSound(null, null);
                }
                //闪光灯提示
                channel.enableLights(notifyInfo.isVoice);
                //闪光灯颜色
                channel.setLightColor(notifyInfo.isLight != null ? notifyInfo.isLight[0] : 0);
                //振动提示
                channel.enableVibration(notifyInfo.isVibrate);
                //振动频率
                channel.setVibrationPattern(notifyInfo.isVibrate ? new long[]{0, 1000, 1000, 1000} : new long[]{0});
                //创建channel
                manager.createNotificationChannel(channel);
            }
            //创建通知时，标记你的渠道id
            NotificationCompat.Builder builder = create(context, channelId, notifyInfo);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, -1, ProxyActivity.getIntent(context, notifyInfo.proxyAction), PendingIntent.FLAG_UPDATE_CURRENT);
                // 横幅
                builder.setFullScreenIntent(pendingIntent, true);
//                builder.setContentIntent(pendingIntent);
            }
            manager.notify(id++, builder.build());
            LogUtils.d("id : " + id + "notify : " + builder.build().toString());
        }
    }


    private NotificationCompat.Builder create(Context context, String channelId, NotifyInfo notifyInfo) {
        //创建通知时，标记你的渠道id
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(notifyInfo.smallIcon)
                .setLargeIcon(notifyInfo.largeIcon)
                .setWhen(System.currentTimeMillis())
                .setDefaults(getDefaults(notifyInfo))
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(notifyInfo.isVoice ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) : null)
                .setTicker(notifyInfo.tinker)
                .setContentTitle(notifyInfo.title)
                .setContentText(notifyInfo.message)
//                .setLights(notifyInfo.isLight[0], notifyInfo.isLight[1], notifyInfo.isLight[2])
                .setVibrate(notifyInfo.isVibrate ? new long[]{0, 1000, 1000, 1000} : new long[0]); //通知栏消息震动
        LogUtils.d("create : " + builder.toString());
        return builder;
    }

    private int getDefaults(NotifyInfo notifyInfo) {
        LogUtils.d("getDefaults : " + notifyInfo.toString());
        if (notifyInfo.isVibrate && notifyInfo.isVoice && notifyInfo.isLight != null) {
            //声音 振动 灯光
            return Notification.DEFAULT_ALL;
        } else if (notifyInfo.isVibrate && notifyInfo.isVoice) {
            //振动 声音
            return Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
        } else if (notifyInfo.isVibrate && notifyInfo.isLight != null) {
            //振动 灯光
            return Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
        } else if (notifyInfo.isVoice && notifyInfo.isLight != null) {
            //声音 灯光
            return Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
        } else if (notifyInfo.isVoice) {
            //声音
            return Notification.DEFAULT_SOUND;
        } else if (notifyInfo.isVibrate) {
            //振动
            return Notification.DEFAULT_VIBRATE;
        } else if (notifyInfo.isLight != null) {
            //灯光
            return Notification.DEFAULT_LIGHTS;
        } else {
            //默认 取消震动,铃声，灯光 其他都不好使
            return NotificationCompat.FLAG_ONLY_ALERT_ONCE;
        }
    }

    public static class NotifyInfo implements Parcelable {
        String title;
        String message;
        String tinker;
        int smallIcon;
        boolean isVoice;
        Bitmap largeIcon;
        int[] isLight;
        boolean isVibrate;
        private ProxyAction proxyAction;


        protected NotifyInfo(Parcel in) {
            title = in.readString();
            message = in.readString();
            tinker = in.readString();
            smallIcon = in.readInt();
            isVoice = in.readByte() != 0;
            largeIcon = in.readParcelable(Bitmap.class.getClassLoader());
            isLight = in.createIntArray();
            isVibrate = in.readByte() != 0;
            proxyAction = in.readParcelable(ProxyAction.class.getClassLoader());
        }

        private NotifyInfo(Builder builder) {
            title = builder.title;
            message = builder.message;
            tinker = builder.tinker;
            smallIcon = builder.smallIcon;
            isVoice = builder.isVoice;
            largeIcon = builder.largeIcon;
            isLight = builder.isLight;
            isVibrate = builder.isVibrate;
            proxyAction = builder.proxyAction;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(message);
            dest.writeString(tinker);
            dest.writeInt(smallIcon);
            dest.writeByte((byte) (isVoice ? 1 : 0));
            dest.writeParcelable(largeIcon, flags);
            dest.writeIntArray(isLight);
            dest.writeByte((byte) (isVibrate ? 1 : 0));
            dest.writeParcelable(proxyAction, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<NotifyInfo> CREATOR = new Creator<NotifyInfo>() {
            @Override
            public NotifyInfo createFromParcel(Parcel in) {
                return new NotifyInfo(in);
            }

            @Override
            public NotifyInfo[] newArray(int size) {
                return new NotifyInfo[size];
            }
        };


        public static final class Builder {
            private String title;
            private String message;
            private String tinker;
            private int smallIcon;
            private boolean isVoice;
            private Bitmap largeIcon;
            private int[] isLight;
            private boolean isVibrate;
            private ProxyAction proxyAction;

            public Builder() {
            }

            public Builder title(String val) {
                title = val;
                return this;
            }

            public Builder message(String val) {
                message = val;
                return this;
            }

            public Builder tinker(String val) {
                tinker = val;
                return this;
            }

            public Builder smallIcon(int val) {
                smallIcon = val;
                return this;
            }

            public Builder isVoice(boolean val) {
                isVoice = val;
                return this;
            }

            public Builder largeIcon(Bitmap val) {
                largeIcon = val;
                return this;
            }

            public Builder isLight(int[] val) {
                isLight = val;
                return this;
            }

            public Builder isVibrate(boolean val) {
                isVibrate = val;
                return this;
            }

            public Builder proxyAction(ProxyAction val) {
                proxyAction = val;
                return this;
            }

            public NotifyInfo build() {
                return new NotifyInfo(this);
            }
        }

        @Override
        public String toString() {
            return "NotifyInfo{" +
                    "title='" + title + '\'' +
                    ", message='" + message + '\'' +
                    ", tinker='" + tinker + '\'' +
                    ", smallIcon=" + smallIcon +
                    ", isVoice=" + isVoice +
                    ", largeIcon=" + largeIcon +
                    ", isLight=" + Arrays.toString(isLight) +
                    ", isVibrate=" + isVibrate +
                    ", proxyAction=" + proxyAction +
                    '}';
        }
    }


    private static class SingletonInstance {
        private static final NotificationUtils INSTANCE = new NotificationUtils();
    }

}
