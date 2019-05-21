//
// Created by hxl on 2019/5/16.
//
//增加日志
#include <android/log.h>

#ifndef  LOG_TAG
#define  LOG_TAG "JNI_JNI"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#endif

#include <jni.h>
#include <cstring>
#include <cstdlib>
#include "com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil.h"
//king_bird_ndkjnidemo_JNIUtils_printStringByJni 包名+文件名+文件内方法名
JNIEXPORT jstring JNICALL Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_sayHello
        (JNIEnv *env, jobject instance) {
    //字符串返回
    return env->NewStringUTF("没想到吧！我竟然会JNI了！！！");
}

JNIEXPORT jint JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_sum(JNIEnv *env, jobject instance, jint x, jint y) {

    int result = x + y;
    return result;

}

char *jstringToChar(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

JNIEXPORT jstring JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_addstr(JNIEnv *env, jobject instance, jstring str_) {
    char *fromjava = jstringToChar(env, str_);
    char *fromc = const_cast<char *>("add i am c");

    strcat(fromjava, fromc); //拼接两个字符串
    return env->NewStringUTF(fromjava);
}


JNIEXPORT jintArray JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_increaseArrayEles(JNIEnv *env, jobject instance,
                                                                         jintArray intArray_) {
    //1.得到数组的长度
    jsize size = (*env).GetArrayLength(intArray_);
    LOGI("size:%d", size);
    //创建一个长度为size的新数组
    jintArray array = env->NewIntArray(size);
    // 把 Java 传递下来的数组 用 jint* 存起来
    jint *body = (*env).GetIntArrayElements(intArray_, JNI_FALSE);

    //遍历数组，每个元素加10
    jint i = 0;
    jint num[size];
    for (int i = 0; i < size; i++) {
        num[i] = body[i] + 10;
        LOGI("num[i]:%d-%d", i, num[i]);
    }
    // 4.返回结果

    //给 需要返回的数组赋值
    env->SetIntArrayRegion(array, 0, size, num);

    return array;

}


JNIEXPORT jint JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_checkPwd(JNIEnv *env, jobject instance, jstring pwd_) {

    //密码
    char *orginPwd = "123456";
    char *formPwd = jstringToChar(env, pwd_);
    //比较字符串是否相同
    int code = strcmp(orginPwd, formPwd);
    return code;

}
JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSayHello(JNIEnv *env, jobject instance){

}

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSum(JNIEnv *env, jobject instance){

}



