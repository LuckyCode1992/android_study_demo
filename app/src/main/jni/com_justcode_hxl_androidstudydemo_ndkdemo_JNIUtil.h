/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil */

#ifndef _Included_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil
#define _Included_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil
 * Method:    sayHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_sayHello
        (JNIEnv *, jobject);

JNIEXPORT jint JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_sum(JNIEnv *env, jobject instance, jint x, jint y);

JNIEXPORT jstring JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_addstr(JNIEnv *env, jobject instance, jstring str_);

JNIEXPORT jintArray JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_increaseArrayEles(JNIEnv *env, jobject instance,
                                                                         jintArray intArray_);
JNIEXPORT jint JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_checkPwd(JNIEnv *env, jobject instance, jstring pwd_);

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSayHello(JNIEnv *env, jobject instance);

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_callBackSum(JNIEnv *env, jobject instance);

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIUtil_c2javashow(JNIEnv *env, jobject instance);

JNIEXPORT void JNICALL
Java_com_justcode_hxl_androidstudydemo_ndkdemo_JNIActivity_c2javashow2(JNIEnv *env, jobject instance);

#ifdef __cplusplus
}
#endif
#endif
