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

#include "ekconf_internal_kconfig_NativeConfiguration.h"
#include <malloc.h>
#include <dlfcn.h>
#include <string.h>

#define EXCEPTION_CLASS "ekconf/kconfig/KconfigException"

void *handle = NULL;
char *errorMsg;
int loadlibekconfig(char *);
void ThrowByName(JNIEnv *env, const char *name, const char *msg);
char *GetStringNativeChars(JNIEnv *env, jstring jstr);

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativeInit
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativeInit
		(JNIEnv * env , jobject jconfigObj, 
		 jstring jpath, jstring libekconfigPath,
		 jstring configurationClassPath, jstring Configuration_pRootMenuVariableName,
		 jstring menuClassPath, jstring Menu_pMenuVariablename, 
		 jstring symbolClassPath, jstring Symbol_pSymbolVariableName)
{
	char * path = NULL;
	path = GetStringNativeChars(env, libekconfigPath);
	if (path == NULL){
		errorMsg = (char*)malloc(300*sizeof(char));
		errorMsg = (char*)"could not load file : ";
		strcat(errorMsg, path);
		ThrowByName(env , EXCEPTION_CLASS , errorMsg);
		free (errorMsg);
		free (path);
		return;
	}
	if (-1 == loadlibekconfig(path) ){
		ThrowByName(env , EXCEPTION_CLASS , errorMsg);
		return;
	}
	free(path);
	initConfiguration(env, jconfigObj , jpath,
			  configurationClassPath , Configuration_pRootMenuVariableName,
			  menuClassPath , Menu_pMenuVariablename,
			  symbolClassPath , Symbol_pSymbolVariableName);
}

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativedispose
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativeDispose
		(JNIEnv *env , jobject jconfigObj)
{
	if (handle != NULL ){
		if (dispose != NULL ) {
			dispose(env);
			dispose = NULL;
		}
		dlclose(handle);
		handle = NULL;
	}
}

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativeHasChanged
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativeHasChanged
		(JNIEnv *env , jobject jconfigObj)
{
	return hasChanged(env, jconfigObj);
}

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativeWriteConfiguration
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativeWriteConfiguration
		(JNIEnv *env, jobject jconfigObj , jstring fileName)
{
	return writeConfiguration(env , jconfigObj , fileName);
}

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativefindMenu
 * Signature: (Ljava/lang/String;)[J
 */
JNIEXPORT jlongArray JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativefindMenu
(JNIEnv *env, jobject jconfigObj, jstring pattern)
{
	return findMenu(env, jconfigObj , pattern);
}

/*
 * Class:     ekconf_internal_kconfig_NativeConfiguration
 * Method:    nativeLoadAlternateConfiguration
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeConfiguration_nativeLoadAlternateConfiguration
(JNIEnv * env, jobject jconfigObj, jstring configPath){
	return loadAlternateConfiguration(env, jconfigObj ,configPath);
}

int loadlibekconfig(char *libekconfigPath) {
	handle = NULL;
	handle = dlopen(libekconfigPath ,RTLD_LAZY);
	if (!handle) {
		 errorMsg = dlerror();
		 return -1;
	}
	dlerror();

	*(void **) (&initConfiguration) = dlsym( handle, "initConfiguration");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&dispose) = dlsym( handle, "dispose");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&hasChanged) = dlsym( handle, "hasChanged");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&writeConfiguration) = dlsym( handle, "writeConfiguration");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&findMenu) = dlsym( handle, "findMenu");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&loadAlternateConfiguration) = dlsym( handle, "loadAlternateConfiguration");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}

	*(void **) (&getpList) = dlsym( handle, "getpList");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getpNext) = dlsym( handle, "getpNext");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getpSym) = dlsym( handle, "getpSym");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&hasProperty) = dlsym( handle, "hasProperty");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getPrompt) = dlsym( handle, "getPrompt");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&isVisible) = dlsym( handle, "isVisible");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getHelp) = dlsym( handle, "getHelp");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getPropertyType) = dlsym( handle, "getPropertyType");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getpParent) = dlsym( handle, "getpParent");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}

	*(void **) (&getSymbolType) = dlsym( handle, "getSymbolType");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&isChoice) = dlsym( handle, "isChoice");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&isChoiceValue) = dlsym( handle, "isChoiceValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getName) = dlsym( handle, "getName");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&isChangeable) = dlsym( handle, "isChangeable");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getTypeName) = dlsym( handle, "getTypeName");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getTristateValue) = dlsym( handle, "getTristateValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&setTristateValue) = dlsym( handle, "setTristateValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&hasValue) = dlsym( handle, "hasValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&getStringValue) = dlsym( handle, "getStringValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&setStringValue) = dlsym( handle, "setStringValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&toggleTristateValue) = dlsym( handle, "toggleTristateValue");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
	*(void **) (&tristateWithinRange) = dlsym( handle, "tristateWithinRange");
	if ((errorMsg = dlerror()) != NULL ) {
		return -1;
	}
}

void ThrowByName(JNIEnv *env, const char *name, const char *msg)
{
	jclass cls = env->FindClass(name);
	if (cls != NULL) {
		env->ThrowNew(cls, msg);
	}
	env->DeleteLocalRef(cls);
}

char *GetStringNativeChars(JNIEnv *env, jstring jstr)
{
	jbyteArray bytes ;
	jthrowable exc;
	char *result = 0;
	jclass _cls_String;
	jmethodID MID_String_getBytes;

	_cls_String = env->FindClass("java/lang/String");
	if(_cls_String == NULL ){
			return NULL;
	}

	MID_String_getBytes = env->GetMethodID(_cls_String , "getBytes","()[B");
	if(MID_String_getBytes == NULL ){
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
			ThrowByName(env, "java/lang/OutOfMemoryError",
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
	env->DeleteLocalRef(_cls_String);
	env->DeleteLocalRef(bytes);
	return result;
}

