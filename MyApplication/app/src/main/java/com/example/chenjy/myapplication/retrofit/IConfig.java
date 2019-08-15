package com.example.chenjy.myapplication.retrofit;

import java.util.Map;

public interface IConfig<T> {
    void register(ServiceType serviceType, Class<T> cls);
    Class<T> get(ServiceType serviceType);
    void clear();
    Map<ServiceType,IService> get();
}
