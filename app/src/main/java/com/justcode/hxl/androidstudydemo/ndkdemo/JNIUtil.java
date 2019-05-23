package com.justcode.hxl.androidstudydemo.ndkdemo;

import android.util.Log;

import java.net.PortUnreachableException;

public class JNIUtil {
    {
        System.loadLibrary("JNIControl");
    }

    //定义native方法
    //java 调用C代码对应的方法
    public native String sayHello();

    public native int sum(int x, int y);

    public native String addstr(String str);

    public native int[] increaseArrayEles(int[] intArray);

    public native int checkPwd(String pwd);

    //C调用java
    public native void callBackSayHello();

    public native void callBackSum();

    public int sumJava(int x, int y) {
        Log.d("JNI_JNI", "java:" + (x + y));
        return x + y;
    }

    public static void sayHelloJava(String s) {
        Log.d("JNI_JNI", "静态方法被调用:s=" + s);
    }
}
