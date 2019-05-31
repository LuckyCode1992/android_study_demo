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
#include <unistd.h>
#include <sys/wait.h>
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
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSayHello(JNIEnv *env, jobject instance) {
//1.得到字节码
    jclass jclazz = (*env).FindClass("com/justcode/hxl/androidstudydemo/ndkdemo/JNIUtil");
//2.得到方法
    jmethodID jmethodIDs = env->GetStaticMethodID(jclazz, "sayHelloJava", "(Ljava/lang/String;)V");

//3.实例化该类
    //jobject jobject0 = env->AllocObject(jclazz);
//4.调用方法
    jstring str = env->NewStringUTF("哈哈哈");
    env->CallStaticVoidMethod(jclazz, jmethodIDs, str);
}

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSum(JNIEnv *env, jobject instance) {
//1.得到字节码
    jclass jclazz = (*env).FindClass("com/justcode/hxl/androidstudydemo/ndkdemo/JNIUtil");
//2.得到方法
    jmethodID jmethodIDs = env->GetMethodID(jclazz, "sumJava", "(II)I");

//3.实例化该类
    jobject jobject0 = env->AllocObject(jclazz);
//4.调用方法
    jint value = env->CallIntMethod(jobject0, jmethodIDs, 55, 25);
    LOGI("value:%d", value);
}


/**
 *
 * @param env
 * @param instance  谁调用了当前这个方法对应的jni的java的接口，就是谁的实例
 */
JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_c2javashow(JNIEnv *env, jobject instance) {
    //1.得到字节码
    jclass jclazz = (*env).FindClass("com/justcode/hxl/androidstudydemo/ndkdemo/JNIActivity");
//2.得到方法
    jmethodID jmethodIDs = env->GetMethodID(jclazz, "javashow", "()V");

//3.实例化该类
    jobject jobject0 = env->AllocObject(jclazz);
//4.调用方法
    env->CallVoidMethod(jobject0, jmethodIDs);
}


/**
 *
 * @param env
 * @param instance  谁调用了当前这个方法对应的jni的java的接口，就是谁的实例
 */
JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIActivity_c2javashow2(JNIEnv *env, jobject instance) {
    //1.得到字节码
    jclass jclazz = (*env).FindClass("com/justcode/hxl/androidstudydemo/ndkdemo/JNIActivity");
//2.得到方法
    jmethodID jmethodIDs = env->GetMethodID(jclazz, "javashow", "()V");

//3.实例化该类
//    jobject jobject0 = env->AllocObject(jclazz);
//4.调用方法
    env->CallVoidMethod(instance, jmethodIDs);
}


JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIActivity_uninstaListetner(JNIEnv *env, jobject instance,
                                                                            jstring name_) {
    //返回3个值，>0 ：父进程id，等于0 子进程id，负数就是出错了
    int code = fork();
    LOGI("code:%d", code);
//    char *pakName = jstringToChar(env, name_);
//    LOGI("pakName:%d", pakName);
    if (code == 0) {
        //判断软件是否被卸载
        int flag = 1;
        while (flag) {
            sleep(1);
            LOGI("flag:%d", flag);
            FILE *file = fopen("/data/data/com.justcode.hxl.androidstudydemo", "r");
            if (file == NULL) {
                //应用对应的文件夹不存在，说明被卸载了
                execlp("am", "am", "start", "--user", "0", "-a", "android.intent.action.VIEW", "-d",
                       "https://www.baidu.com", (char *) NULL);
                LOGI("flag:%d", flag);
                LOGI("文件为null了");
                flag = 0;
            }
        }
    } else if (code > 0) {
        //父进程
    } else {
        //出错了
    }


}


