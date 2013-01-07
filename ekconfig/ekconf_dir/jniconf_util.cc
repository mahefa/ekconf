/*
* Ekconf, an Eclipe plug-in for configuring the Linux kernel or Buildroot.
* 
* Copyright (C) 2012 Tiana Rakotovao Andriamahefa <rkmahefa@gmail.com>
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 52 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

#include <iostream>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>

#include "jniconf_util.h"

using std::cout;
using std::endl;


void JNU_ThrowByName(JNIEnv *env, const char *name, const char *msg)
{
	jclass cls = env->FindClass(name);
	if (cls != NULL) {
		env->ThrowNew(cls, msg);
	}
	env->DeleteLocalRef(cls);
}


char *JNU_GetStringNativeChars(JNIEnv *env, jstring jstr)
{
	jbyteArray bytes ;
	jthrowable exc;
	char *result = 0;

	if (jstr == NULL){
		return NULL;
	}
	if (env->EnsureLocalCapacity(2) < 0) {
		return 0;
	}
	bytes = (jbyteArray) env->CallObjectMethod(jstr, MID_String_getBytes);
	exc = env->ExceptionOccurred();
	if (!exc) {
		jint len = env->GetArrayLength(bytes);
		result = (char *)malloc(len + 1);
		if (result == 0) {
			JNU_ThrowByName(env, "java/lang/OutOfMemoryError",
					0);
			env->DeleteLocalRef(bytes);
			return 0;
		}
		env->GetByteArrayRegion(bytes, 0, len,
					(jbyte *)result);
		result[len] = 0;
	} else {
		env->DeleteLocalRef(exc);
	}
	env->DeleteLocalRef(bytes);
	return result;
}

void JNU_CheckAndThrowException(JNIEnv *env){
	jthrowable exc = env->ExceptionOccurred();
	if (!exc)
		env->Throw(exc);
}

jstring JNU_NewStringNative(JNIEnv *env, const char *str)
{
	jstring result;
	jbyteArray bytes = 0;
	int len;
	if(str==NULL){
		return NULL;
	}
	if (env->EnsureLocalCapacity( 2) < 0) {
		return NULL;
	}
	len = strlen(str);
	bytes = env->NewByteArray(len);
	if (bytes != NULL) {
		env->SetByteArrayRegion( bytes, 0, len,
					 (jbyte *)str);
		result = (jstring)env->NewObject(stringClass , MID_String_constructorFromBytes , bytes);
		JNU_CheckAndThrowException(env);
		env->DeleteLocalRef(bytes);
		return result;
	}
	return NULL;
}
