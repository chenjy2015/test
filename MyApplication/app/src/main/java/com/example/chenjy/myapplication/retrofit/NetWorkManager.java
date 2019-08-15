package com.example.chenjy.myapplication.retrofit;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetWorkManager {

    RequestHelper requestHelper;
    BookService bookService;
    Config config;

    private NetWorkManager() {
        requestHelper = new RequestHelper();
        config = new Config();
    }

    public static NetWorkManager getInstance() {
        return SingletonInstance.INSTANCE;
    }

    private static class SingletonInstance {
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public NetWorkManager init(@NotNull Context context, @NotNull String baseUrl) {
        config.register(ServiceType.BOOK_SERVICE, BookService.class);
        requestHelper.init(context, baseUrl, config);
        bookService = (BookService) requestHelper.getService(ServiceType.BOOK_SERVICE);
        return this;
    }

    public void getBook(int id) {
        Call<ResponseBody> call = bookService.getBook(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        LogUtils.json("getBook : ", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.json("getBook : ", t.getMessage());
            }
        });
    }


}