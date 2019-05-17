package com.justcode.hxl.androidstudydemo.ndkdemo;

import java.net.PortUnreachableException;

public class JNIUtil {
    {
        System.loadLibrary("JNIControl");
    }

    //定义native方法
    //调用C代码对应的方法
    public native String sayHello();

    public native int sum(int x, int y);

    public native String addstr(String str);

    public native int[] increaseArrayEles(int[] intArray);

    public native int checkPwd(String pwd);
}
