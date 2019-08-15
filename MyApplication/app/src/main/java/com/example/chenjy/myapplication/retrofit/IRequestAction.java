package com.example.chenjy.myapplication.retrofit;

import android.content.Context;

public interface IRequestAction<S> {


    void init(Context context, String baseUrl,Config config);

    S getService(ServiceType serviceType);

}
