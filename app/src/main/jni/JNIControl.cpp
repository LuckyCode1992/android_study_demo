//
// Created by hxl on 2019/5/16.
//

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

char* jstringToChar(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

JNIEXPORT jstring JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_addstr(JNIEnv *env, jobject instance, jstring str_) {
    char* fromjava = jstringToChar(env, str_);
     char* fromc = const_cast<char *>("add i am c");

    strcat(fromjava, fromc); //拼接两个字符串
    return env->NewStringUTF(fromjava);
}

//JNIEXPORT jint JNICALL
//Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_checkPwd(JNIEnv *env, jobject instance, jstring pwd_) {
//
//
//}

//JNIEXPORT jintArray JNICALL
//Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_increaseArrayEles(JNIEnv *env, jobject instance,
//                                                                         jintArray intArray_) {
//
//}

//JNIEXPORT jint JNICALL
//Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_checkPwd(JNIEnv *env, jobject instance, jstring pwd_) {
//
//}



