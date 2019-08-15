package com.example.chenjy.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.example.chenjy.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @route:
 * @descript: 视频播放界面
 * @create: chenjiayou
 * create at 2018/11/30 15:33
 */
public class VideoPlayerActivity extends Activity {

    public static final String KEY_NAME = "name";
    public static final String KEY_PATH = "path";
    public static final String KEY_URL = "url";
    public static final String KEY_TYPE = "type";

    //网络路径
    private String mUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    //本地路径
    private String mPath;
    private String mFileName;
    private int mUrlType; // 地址类型 0本地 1网络

    private List<VideoijkBean> list = new ArrayList<>();

    private PlayerView player;
    private PowerManager.WakeLock wakeLock;

    private ViewDataBinding mViewDataBinding;


    public static void startActivity(Context context, String name, String path) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(KEY_NAME, name);
        intent.putExtra(KEY_PATH, path);
        intent.putExtra(KEY_TYPE, 0);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TYPE, 1);
        context.startActivity(intent);
    }


//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_video_player;
//    }
//
//    @Override
//    protected void initView() {
//
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);
        init();
        initEvent();
        initData();
    }

    @SuppressLint("InvalidWakeLockTag")
    protected void init() {
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        mUrlType = getIntent().getIntExtra(KEY_TYPE, 0);
        mUrl = getIntent().getStringExtra(KEY_URL);
        mPath = getIntent().getStringExtra(KEY_PATH);
        mFileName = getIntent().getStringExtra(KEY_NAME);


        if (mUrlType == 0) {
            if (TextUtils.isEmpty(mPath)) {
                showNotValidPathDialog("文件路径无效, 即将退出!");
                return;
            }
        } else {
            if (TextUtils.isEmpty(mUrl)) {
                showNotValidPathDialog("文件路径无效, 即将退出!");
                return;
            }
        }
    }

    protected void initEvent() {

    }

    protected void initData() {
//        mUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        mPath = "/storage/emulated/0/bgy2018/file/DFA2R34FAS12.mp4";
//        if (mUrlType == 0) {
            playLocal(mPath);
//        } else {
//            playUrlTest();
//        }
    }


    /**
     * 播放本地地址
     */
    private void playLocal(String url) {
        player = new PlayerView(this)
                .setTitle(mFileName)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideRotation(true)
                .forbidTouch(false)
                .setForbidHideControlPanl(true)
                .showThumbnail(ivThumbnail -> Glide.with(VideoPlayerActivity.this).load(url).into(ivThumbnail))
                .setPlaySource(url)
                .startPlay();
    }


    /**
     * 播放网络地址
     */
    private void playUrl() {
        VideoijkBean m1 = new VideoijkBean();
        m1.setStream("标清");
        m1.setUrl(mUrl);
        list.add(m1);
        player = new PlayerView(this)
                .setTitle("什么")
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .setForbidHideControlPanl(true)
                .setPlaySource(mUrl)
                .startPlay();
    }


    /**
     * 播放网络地址
     */
    private void playUrlTest() {
        String url1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String url2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        VideoijkBean m1 = new VideoijkBean();
        m1.setStream("标清");
        m1.setUrl(url1);
        VideoijkBean m2 = new VideoijkBean();
        m2.setStream("高清");
        m2.setUrl(url2);
        list.add(m1);
        list.add(m2);
        player = new PlayerView(this)
                .setTitle("什么")
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .setForbidHideControlPanl(true)
                .showThumbnail(ivThumbnail ->
                        Glide.with(VideoPlayerActivity.this)
                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .apply(
                                        RequestOptions
                                                .diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)

                                )
                                .into(ivThumbnail))
                .setPlaySource(list)
                .startPlay();
    }

    /**
     * 播放本地视频
     */

    private String getLocalVideoPath(String name) {
        String sdCard = Environment.getExternalStorageDirectory().getPath();
        String uri = sdCard + File.separator + name;
        return uri;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
//        MediaUtils.muteAudioFocus(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
//        MediaUtils.muteAudioFocus(this, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    public void showNotValidPathDialog(String error) {

    }

}
