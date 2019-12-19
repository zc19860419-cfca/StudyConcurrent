#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "com_zc_study_HelloJNI.h"
#include "com_zc_study_Multi1JNI.h"

int i = 0;

JNIEXPORT jstring JNICALL Java_com_zc_study_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
    char dest_str[128] = {0};
    snprintf(dest_str, sizeof(dest_str),"HelloJNI Hello World!-%d\n",i++);
    return (*env)->NewStringUTF(env, dest_str);
}

JNIEXPORT jstring JNICALL Java_com_zc_study_Multi1JNI_sayHello(JNIEnv *env, jobject thisObj) {
    char dest_str[128] = {0};
    snprintf(dest_str, sizeof(dest_str),"Multi1JNI Hello World!-%d\n",i++);
    return (*env)->NewStringUTF(env, dest_str);
}