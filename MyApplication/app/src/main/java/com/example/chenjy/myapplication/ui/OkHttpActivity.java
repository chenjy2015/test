package com.example.chenjy.myapplication.ui;

import android.os.Environment;
import android.util.Log;

import com.example.chenjy.myapplication.DebounceObservableTransformer;
import com.example.chenjy.myapplication.R;
import com.example.chenjy.myapplication.base.activity.BaseUIActivity;
import com.example.chenjy.myapplication.databinding.ActivityOkHttpBinding;
import com.example.chenjy.myapplication.intercept.LoggingInterceptor;
import com.example.chenjy.myapplication.utils.L;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;

public class OkHttpActivity extends BaseUIActivity<ActivityOkHttpBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_ok_http;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        addDisposable(
                RxView.clicks(mViewDataBinding.get)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> get())
        );
        addDisposable(
                RxView.clicks(mViewDataBinding.post)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> post())
        );
        addDisposable(
                RxView.clicks(mViewDataBinding.postStream)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> postStream())
        );
        addDisposable(
                RxView.clicks(mViewDataBinding.postFile)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> postFile())
        );
        addDisposable(
                RxView.clicks(mViewDataBinding.postTable)
                        .compose(new DebounceObservableTransformer<>())
                        .subscribe(o -> postTable())
        );

    }

    @Override
    public void initData() {

    }

    public static final String TAG = "OKHTTPActivity";

    public void get() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d(TAG, "OKHttpClient - GET onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d(TAG, "OKHttpClient - GET onResponse: " + response.body().string());
            }
        });
    }

    public void post() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder().url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d(TAG, "OKHttpClient - POST onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    L.d(TAG, "OKHttpClient - POST " + headers.name(i) + ":" + headers.value(i));
                }
                L.d(TAG, "OKHttpClient - POST onResponse: " + response.body().string());
            }
        });
    }

    public void postStream() {
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am Jdqm.");
            }
        };

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public void postFile() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        String root = Environment.getExternalStorageDirectory().getPath();
        String fileRoot = "wx2019";
        String path = root + File.separator + fileRoot + File.separator + "2019-08-12.log";
        File file = new File(path);
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, file))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public void postTable() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
