//
// Created by hxl on 2019/5/16.
//

#include <jni.h>
#include "com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil.h"
//king_bird_ndkjnidemo_JNIUtils_printStringByJni 包名+文件名+文件内方法名
JNIEXPORT jstring JNICALL Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_sayHello
        (JNIEnv *env, jobject instance) {
    //字符串返回
return env->NewStringUTF("没想到吧！我竟然会JNI了！！！");
}