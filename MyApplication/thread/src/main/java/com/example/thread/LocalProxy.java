package com.example.thread;

public class LocalProxy<T, V, S> {

    private T instance = null;
    private V value = null;
    private S show = null;

    public LocalProxy(T instance) {
        this.instance = instance;
    }

    public T get() {
        return instance;
    }

    public void next(V v) {
        value = v;
    }

    public void show() {
        System.out.println("T : " + get() + " value: " + value);
    }
}

