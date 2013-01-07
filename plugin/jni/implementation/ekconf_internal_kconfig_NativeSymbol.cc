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

#include "ekconf_internal_kconfig_NativeSymbol.h"

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeGetSymbolType
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeGetSymbolType
		(JNIEnv *env , jobject jsymbol)
{
	return getSymbolType(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeIsChoice
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeIsChoice
		(JNIEnv *env , jobject jsymbol)
{
	return isChoice(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeIsChoiceValue
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeIsChoiceValue
		(JNIEnv *env , jobject jsymbol)
{
	return isChoiceValue(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeGetName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeGetName
		(JNIEnv *env , jobject jsymbol)
{
	return getName(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeGetTypeName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeGetTypeName
		(JNIEnv *env , jobject jsymbol)
{
	return getTypeName(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeIsChangeable
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeIsChangeable
		(JNIEnv *env , jobject jsymbol)
{
	return isChangeable(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeGetTristateValue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeGetTristateValue
		(JNIEnv *env , jobject jsymbol)
{
	return getTristateValue(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeSetTristateValue
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeSetTristateValue
		(JNIEnv *env , jobject jsymbol, jint value)
{
	return setTristateValue(env , jsymbol, value);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeToggleTristateValue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeToggleTristateValue
		(JNIEnv *env , jobject jsymbol)
{
	return toggleTristateValue(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeHasValue
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeHasValue
		(JNIEnv *env , jobject jsymbol)
{
	return hasValue(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeGetStringValue
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeGetStringValue
		(JNIEnv *env , jobject jsymbol)
{
	return getStringValue(env , jsymbol);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeSetStringValue
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeSetStringValue
		(JNIEnv *env , jobject jsymbol, jstring value)
{
	return setStringValue(env , jsymbol, value);
}

/*
 * Class:     ekconf_internal_kconfig_NativeSymbol
 * Method:    nativeTristateWithinRange
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_ekconf_internal_kconfig_NativeSymbol_nativeTristateWithinRange
		(JNIEnv *env , jobject jsymbol, jint int_tristate)
{
	return tristateWithinRange(env , jsymbol, int_tristate);
}
