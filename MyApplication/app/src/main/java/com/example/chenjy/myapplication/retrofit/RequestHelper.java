package com.example.chenjy.myapplication.retrofit;

import android.content.Context;

import retrofit2.Retrofit;

public class RequestHelper<T> implements IRequestAction {

    private Context context;
    private String baseUrl;
    private Retrofit retrofit;
    private Config config;

    @Override
    public void init(Context context, String baseUrl, Config config) {
        this.context = context;
        this.config = config;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
    }

    @Override
    public T getService(ServiceType serviceType) {
        return (T) ServiceFactory.factory(config, retrofit, serviceType);
    }

}
