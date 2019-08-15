package com.example.chenjy.myapplication.retrofit;

import retrofit2.Retrofit;

public class ServiceFactory {

    public static Object factory(Config config, Retrofit retrofit, ServiceType serviceType) {
        if (config == null || config.get().size() == 0) {
            throw new NullPointerException("this config cannot be null and need init");
        }
        if (config.get(serviceType) == null) {
            throw new InitializationException("this type has not initialization");
        }
        if (serviceType == ServiceType.BOOK_SERVICE) {
            return retrofit.create(config.get(serviceType));
        }
        return null;
    }
}
