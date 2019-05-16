package com.justcode.hxl.androidstudydemo.ndkdemo;

public class JNIUtil {
    {
        System.loadLibrary("JNIControl");
    }

    //定义native方法
    //调用C代码对应的方法
    public  native String sayHello();
}
