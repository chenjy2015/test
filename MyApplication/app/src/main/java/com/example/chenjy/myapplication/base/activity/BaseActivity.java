package com.example.chenjy.myapplication.base.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.LogUtils;
import com.example.chenjy.myapplication.ActivityCollector;
import com.example.chenjy.myapplication.base.i.IViewModel;
import com.example.chenjy.myapplication.contants.SettingConst;
import com.example.chenjy.myapplication.utils.TUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 基础Activity，所有Activity必须继承于此类
 * <p>
 * Create by Melorin on 2018/11/20
 */
public class BaseActivity<ViewModel extends IViewModel> extends AppCompatActivity implements LifecycleProvider<ActivityEvent> {

    protected ViewModel mViewModel;
    private CompositeDisposable mCompositeDisposable;
    protected boolean mIsForeground;
    private static final String LOG_TAG = "BaseActivity";


    protected ViewModel getViewModel() {
        return TUtil.get(this);
    }

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Android 8.0 系统一个限制 如果是透明背景则不能固定方向否则报错
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            LogUtils.i(LOG_TAG, "onCreate fixOrientation when Oreo, result = " + result);
        }
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this, getClass());
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mViewModel = getViewModel();
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            LogUtils.i(LOG_TAG, "avoid calling setRequestedOrientation when Oreo.");
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        mIsForeground = true;
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        mIsForeground = false;
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        clearDisposable();
        ActivityCollector.removeActivity(this);
        if (mViewModel != null) {
            mViewModel.onDestroy();
            mViewModel = null;
        }
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    private static float fontScale = -1; //动态设置放大倍数
    private static float MULTIPLE = 1.0f; //默认放大倍数
    private static String languageType;

    public void setTextSizeModel(float fontScale) {
        this.fontScale = fontScale;
    }

    public void setLanguageType(String languageType){
        this.languageType = languageType;
    }

    @Override
    public Resources getResources() {

        Resources resources = super.getResources();
        if (resources != null) {
            if (fontScale < 0) {
                fontScale = 1 * MULTIPLE;
            }
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            // 应用字号设置
            config.fontScale = fontScale;
            // 应用用户选择语言
            if (languageType == null){
                config.locale = Locale.getDefault();
            }else {
                config.locale = languageType.equals(SettingConst.LanguageType.Chinese) ? Locale.CHINESE : Locale.ENGLISH;
            }
             resources.updateConfiguration(config, dm);
        }
        return resources;
    }

}
