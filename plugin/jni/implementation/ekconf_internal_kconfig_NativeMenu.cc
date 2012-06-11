/*
* Ekconf, an Eclipe plug-in for configuring the Linux kernel or Buildroot.
* 
* Copyright (C) 2012 Tiana Rakotovao <rakotovaomahefa@gmail.com>
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

#include "ekconf_internal_kconfig_NativeMenu.h"

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    getPList
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetpList
		(JNIEnv *env, jobject jmenu)
{
	return getpList(env , jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    getpNext
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetpNext
		(JNIEnv * env , jobject jmenu)
{
	return getpNext(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    getpSymbol
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetpSym
		(JNIEnv * env , jobject jmenu)
{
	return getpSym(env , jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    nativeHasProperty
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeHasProperty
		(JNIEnv * env , jobject jmenu)
{
	return hasProperty(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    getNativePrompt
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetPrompt
		(JNIEnv * env , jobject jmenu)
{
	return getPrompt(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    nativeIsVisible
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeIsVisible
		(JNIEnv * env , jobject jmenu)
{
	return isVisible(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    nativeGetHelp
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetHelp
		(JNIEnv * env , jobject jmenu)
{
	return getHelp(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    nativeGetPropertyType
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ekconf_internal_kconfig_NativeMenu_nativeGetPropertyType
		(JNIEnv * env , jobject jmenu)
{
	return getPropertyType(env, jmenu);
}

/*
 * Class:     ekconf_internal_kconfig_NativeMenu
 * Method:    getpParent
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ekconf_internal_kconfig_NativeMenu_getpParent
		(JNIEnv * env , jobject jmenu)
{
	return getpParent(env, jmenu);
}
