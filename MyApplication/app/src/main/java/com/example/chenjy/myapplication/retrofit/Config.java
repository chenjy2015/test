package com.example.chenjy.myapplication.retrofit;

import java.util.HashMap;
import java.util.Map;

public class Config<T> implements IConfig {

    protected Map<ServiceType, Class<T>> serviceMap = new HashMap<>();

    @Override
    public void register(ServiceType serviceType, Class cls) {
        serviceMap.put(serviceType, cls);
    }

    @Override
    public Class<T> get(ServiceType serviceType) {
        return serviceMap.get(serviceType);
    }

    @Override
    public void clear() {
        serviceMap.clear();
    }

    @Override
    public Map<ServiceType, Class<T>> get() {
        return serviceMap;
    }

}
