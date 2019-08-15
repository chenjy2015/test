package com.example.chenjy.myapplication.ui;


import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.blankj.utilcode.util.TimeUtils;
import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.ProxyAction;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.contants.SettingConst;
import com.example.chenjy.myapplication.databinding.ActivityNoticationBinding;
import com.example.chenjy.myapplication.utils.NotificationUtils;
import com.jakewharton.rxbinding2.view.RxView;

/**
 * 测试通知界面
 */
public class NotificationActivity extends BaseUIActivity<ActivityNoticationBinding> {

    public static String[] ds = {
            "刘老师的积分", "张三", "李四", "王五", "中山", "叶俊招", "余翰林", "黄思华", "陈振宇", "镇宝山",
            "留的话", "国福彩", "立马", "12马华峰", "水电费", "3123玩儿翁", "下支撑", "阿斯蒂芬Q", "好几款", "斯顿发我S", "海景房", "玩儿若", "婆婆",
            "平行线", "半年", "剖腹", "isad扶贫", "#阿斯蒂芬", "现网", "发大水"
    };

    public static final String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";

    @Override
    public int getLayout() {
        return R.layout.activity_notication;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initEvent() {

        addDisposable(
                RxView.clicks(mViewDataBinding.sort)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> notify(0))
        );
        addDisposable(
                RxView.clicks(mViewDataBinding.video)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> notify(1))
        );
    }

    @Override
    public void initData() {

    }

    @SuppressWarnings("unchecked")
    public void notify(int type) {
        ProxyAction proxyAction;
        boolean isVibrator = mViewDataBinding.vibrator.isChecked();
        boolean isVoice = mViewDataBinding.voice.isChecked();
        boolean isLight = mViewDataBinding.light.isChecked();
        switch (type) {
            case 0:
                proxyAction = new ProxyAction(SettingConst.IProxyActions.SORT_ACTIVITY, ds);
                NotificationUtils.getInstance().notify(this, createNotification(isVibrator, isVoice, isLight, proxyAction));
                break;

            case 1:
                proxyAction = new ProxyAction(SettingConst.IProxyActions.VIDEO_PLAYER_ACTIVITY, url);
                NotificationUtils.getInstance().notify(this, createNotification(isVibrator, isVoice, isLight, proxyAction));
                break;
        }
    }

    public NotificationUtils.NotifyInfo createNotification(boolean isVibrator, boolean isVoice, boolean isLight, ProxyAction proxyAction) {
        return new NotificationUtils.NotifyInfo.Builder()
                .title("JSM通知消息")
                .tinker("message : " + TimeUtils.millis2String(System.currentTimeMillis()))
                .isLight(isLight ? new int[]{Color.BLUE, 2000, 1000} : new int[]{0, 0, 0})
                .isVibrate(isVibrator)
                .isVoice(isVoice)
                .proxyAction(proxyAction)
                .largeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .smallIcon(R.mipmap.ic_launcher)
                .build();

    }

}
