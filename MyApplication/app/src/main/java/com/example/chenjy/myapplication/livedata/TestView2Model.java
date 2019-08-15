package com.example.chenjy.myapplication.livedata;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TestView2Model extends ViewModel {
    private String key;

    private MutableLiveData<String> mNameEvent = new MutableLiveData<>();

    public TestView2Model(String key) {
        this.key = key;


//        String s = "123";
//        byte s = 0;
        short s = 0;
        switch (s){
            case 0:
                break;

            case 1:
                break;
        }

        try {
            try {

                TestView2Model testView2Model = TestView2Model.class.getConstructor(String.class).newInstance("s");
                Constructor<TestView2Model> constructor = TestView2Model.class.getDeclaredConstructor(String.class);
                TestView2Model testView2Model2 = constructor.newInstance("ss");
                testView2Model.clone();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public MutableLiveData<String> getNameEvent() {
        return mNameEvent;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private String mKey;

        public Factory(String key) {
            mKey = key;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TestView2Model(mKey);
        }

        public String getKey() {
            return mKey;
        }
    }
}
