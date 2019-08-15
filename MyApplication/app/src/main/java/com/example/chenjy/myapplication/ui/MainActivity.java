package com.example.chenjy.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chenjy.myapplication.MySqliteOpenHelper;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.TableVO;
import com.example.chenjy.myapplication.UserVO;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.binder.MenuBinder;
import com.example.chenjy.myapplication.databinding.ActivityDbTestBinding;
import com.example.chenjy.myapplication.ui.thread.ThreadDemoActivity;
import com.example.chenjy.myapplication.utils.VibratorUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseUIActivity<ActivityDbTestBinding> implements MenuBinder.OnMenuClickListener {

    MyHandler mHandler;
    MySqliteOpenHelper mOpenHelper;
    MultiTypeAdapter multiTypeAdapter;
    List<String> menus = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.activity_db_test;
    }

    @Override
    public void initView() {
        mViewDataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(String.class, new MenuBinder(this));
        multiTypeAdapter.setItems(menus);
        mViewDataBinding.recycler.setAdapter(multiTypeAdapter);
    }

    @Override
    public void initEvent() {
//        addDisposable(
//                RxView.clicks(mViewDataBinding.insert)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> save())
//        );
//
//        addDisposable(RxView.clicks(mViewDataBinding.insert)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> save()));
//
//        addDisposable(
//                RxView.clicks(mViewDataBinding.query)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> query()));
//
//        addDisposable(RxView.clicks(mViewDataBinding.multi)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> startActivity(new Intent(MainActivity.this, MultiSelectActivity.class))));
//
//        addDisposable(
//                RxView.clicks(mViewDataBinding.multi2)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> startActivity(new Intent(MainActivity.this, MultiSelect2Activity.class))));
//        addDisposable(
//                RxView.clicks(mViewDataBinding.multi3)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> startActivity(new Intent(MainActivity.this, MultiSelect3Activity.class))));
//
//        addDisposable(RxView.clicks(mViewDataBinding.slidingImg)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> startActivity(new Intent(MainActivity.this, LargeImageActivity.class))));
//
//        addDisposable(
//                RxView.clicks(mViewDataBinding.scale)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> startActivity(new Intent(MainActivity.this, ScaleImageActivity.class))));
//
//        addDisposable(RxView.clicks(mViewDataBinding.sketchImg)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> startActivity(new Intent(MainActivity.this, SketchListActivity.class))));
//
//        addDisposable(
//                RxView.clicks(mViewDataBinding.video)
//                        .compose(new DebounceObservableTransformer<>())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(o -> VideoPlayerActivity.startActivity(MainActivity.this, "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4")));
//
//        addDisposable(RxView.clicks(mViewDataBinding.vibrator)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    if (VibratorUtils.isVibrator) {
//                        VibratorUtils.stopVibrate(this);
//                        mViewDataBinding.vibrator.setText("vibator");
//                    } else {
//                        mViewDataBinding.vibrator.setText("stop vibator");
//                        VibratorUtils.vibrate(this, new long[]{0, 100, 500, 300}, true);
//                    }
//                }));
//
//        addDisposable(RxView.clicks(mViewDataBinding.sort)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, SortActivity.class));
//                }));
//
//        addDisposable(RxView.clicks(mViewDataBinding.notification)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, NotificationActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.emoji)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, EmojiActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.webview)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, WebViewActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.textSize)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, TextSizeSettingActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.permission)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, PermissionActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.viewPager)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, SketchViewPagerActivity.class));
//                }));
//        addDisposable(RxView.clicks(mViewDataBinding.zip)
//                .compose(new DebounceObservableTransformer<>())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    startActivity(new Intent(this, ZipActivity.class));
//                }));

    }

    @Override
    public void initData() {
        mHandler = new MyHandler();
        mOpenHelper = new MySqliteOpenHelper(this);
        menus.add("插入");
        menus.add("查询");
        menus.add("多选界面");
        menus.add("多选界面2");
        menus.add("多选界面3");
        menus.add("滑动浏览");
        menus.add("缩放大图");
        menus.add("sketch");
        menus.add("video");
        menus.add("vibrator");
        menus.add("按字母排序");
        menus.add("通知测试");
        menus.add("表情测试");
        menus.add("webview测试");
        menus.add("会话字号测试");
        menus.add("权限测试");
        menus.add("大图测试");
        menus.add("viewpager");
        menus.add("zip");
        menus.add("annotation");
        menus.add("retrofit");
        menus.add("thread 测试");
        menus.add("OKHttp3 测试");
        menus.add("多进程测试");
        menus.add("多进程测试 messenger");
        menus.add("多进程测试 aidl");
        menus.add("live data");
        multiTypeAdapter.notifyDataSetChanged();
    }

    public void save() {
        new Thread(() -> {
            SQLiteDatabase database = mOpenHelper.getWritableDatabase();
            for (int i = 0; i < 200; i++) {
                ContentValues values = new ContentValues();
                values.put(TableVO.USER_ID, String.valueOf(i + 1));
                values.put(TableVO.USER_NAME, String.valueOf("vip:" + (i + 1)));
                values.put(TableVO.USER_DEPT, i % 10 == 0 ? "wx" : "bgy");
                database.insert(MySqliteOpenHelper.USER_TABLE, null, values);
//                    Log.e("database_insert", values.toString());
                Message msg = Message.obtain();
                msg.what = 1;
                msg.arg1 = i + 1;
                mHandler.sendMessage(msg);
            }
            database.close();

        }).start();
    }


    public void query() {
        new Thread(() -> {
            List<UserVO> userVOList = new ArrayList();
            SQLiteDatabase database = mOpenHelper.getReadableDatabase();
//                String sql = "select * from " + MySqliteOpenHelper.USER_TABLE + "where " + TableVO.USER_DEPT + "like '%wx%'";
            StringBuilder selection = new StringBuilder();
            selection.append(TableVO.USER_DEPT).append(" like '").append("wx").append("%'");
            try {
                Cursor cursor = database.query(MySqliteOpenHelper.USER_TABLE, new String[]{TableVO.USER_ID, TableVO.USER_NAME, TableVO.USER_DEPT}, selection.toString(), null, null, null, null);
                while (cursor != null && cursor.moveToNext()) {
                    int userIdColumnIndex = cursor.getColumnIndex(TableVO.USER_ID);
                    int userNameColumnIndex = cursor.getColumnIndex(TableVO.USER_NAME);
                    int userDeptColumnIndex = cursor.getColumnIndex(TableVO.USER_DEPT);
                    String userId = cursor.getString(userIdColumnIndex);
                    String userName = cursor.getString(userNameColumnIndex);
                    String userDept = cursor.getString(userDeptColumnIndex);
                    UserVO userVO = new UserVO(userId, userName, userDept);
                    userVOList.add(userVO);
                    selection.append(userVO.toString()).append("\r\n");
                }
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = selection.toString();
                mHandler.sendMessage(msg);
                recyclerCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = "query exception";
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public void recyclerCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }


    public static int count = 0;

    @Override
    public void onMenuClick(String name, int index) {
        switch (index) {
            case 0:
                save();
                break;
            case 1:
                query();
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, MultiSelectActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, MultiSelect2Activity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, MultiSelect3Activity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, LargeImageActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, ScaleImageActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, SketchListActivity.class));
                break;
            case 8:
                VideoPlayerActivity.startActivity(MainActivity.this, "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
                break;
            case 9:
                if (VibratorUtils.isVibrator) {
                    VibratorUtils.stopVibrate(this);
//                    mViewDataBinding.vibrator.setText("vibator");
                    menus.set(index, "vibator");
                } else {
//                    mViewDataBinding.vibrator.setText("stop vibator");
                    menus.set(index, "stop vibator");
                    VibratorUtils.vibrate(this, new long[]{0, 100, 500, 300}, true);
                }
                multiTypeAdapter.notifyItemChanged(index);
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, SortActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, EmojiActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case 14:
                startActivity(new Intent(this, TextSizeSettingActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, BigImageActivity.class));
                break;
            case 17:
                startActivity(new Intent(this, SketchViewPagerActivity.class));
                break;
            case 18:
                startActivity(new Intent(this, ZipActivity.class));
                break;
            case 19:
                Intent intent = new Intent(this, AnnotationActivity.class);
                intent.putExtra("key_name", "AnnotationTestDemo");
                startActivity(intent);
                break;
            case 20:
                startActivity(new Intent(this, RetrofitDemoActivity.class));
                break;
            case 21:
                startActivity(new Intent(this, ThreadDemoActivity.class));
                break;
            case 22:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case 23:
                startActivity(new Intent(this, ProcessTestActivity.class));
                break;
            case 24:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case 25:
                startActivity(new Intent(this, AIDLDemoActivity.class));
                break;
            case 26:
                startActivity(new Intent(this, LiveDataActivity.class));
                break;


        }
    }

    public class MyHandler extends Handler {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == 199) {
                        mViewDataBinding.pb.setProgress(100);
                        mViewDataBinding.tv.setText("progress: 100 >> database write finish");
                    } else {
                        mViewDataBinding.pb.setProgress(msg.arg1 / (200 / 100));
                        mViewDataBinding.tv.setText("progress: " + msg.arg1);
                    }
                    //launch test
                    Message m = Message.obtain();
                    m.what = 2;
                    m.arg1 = msg.arg1;
                    sendMessageDelayed(m, 1000);
//                    sendMessage(m);
                    break;
                case 2:
                    if (msg.arg1 % 10 == 0) {
                        LaunchModeActivity.startActivity(MainActivity.this);
                        Log.d("LaunchActivity", "ScaleImageActivity count = " + (++count));
                    }
                    break;
                case 3:
                    String str = (String) msg.obj;
                    mViewDataBinding.tv.setText(str);
                    break;
            }
        }
    }
}
