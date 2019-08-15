package com.example.thread;

public class MyClass<T> {

    public static void main(String[] args) {
        LocalProxy<Car, Integer, String> proxy = new MyLocalProxy(new Car("宝马","650000"));
        proxy.next(100);
        proxy.show();

        threadLocal.set(60);
    }

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return super.initialValue();
        }

        @Override
        public void set(Integer integer) {
            super.set(integer);
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public Integer get() {
            return super.get();
        }
    };

    private static class MyLocalProxy extends LocalProxy<Car,Integer,String>{

        public MyLocalProxy(Car instance) {
            super(instance);
        }
    }
}
