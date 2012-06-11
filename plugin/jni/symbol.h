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

#ifndef _SYMBOL_H
#define _SYMBOL_H

#include <jni.h>

jint (*getSymbolType)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*isChoice)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*isChoiceValue)(JNIEnv *env , jobject jsymbol) = NULL;
jstring (*getName)(JNIEnv *env , jobject jsymbol) = NULL;
jstring (*getTypeName)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*isChangeable)(JNIEnv *env , jobject jsymbol) = NULL;
jint (*getTristateValue)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*setTristateValue)(JNIEnv *env , jobject jsymbol, jint value) = NULL;
jboolean (*hasValue)(JNIEnv *env , jobject jsymbol) = NULL;
jstring (*getStringValue)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*setStringValue)(JNIEnv *env , jobject jsymbol, jstring value) = NULL;
jint (*toggleTristateValue)(JNIEnv *env , jobject jsymbol) = NULL;
jboolean (*tristateWithinRange)(JNIEnv *env , jobject jsymbol, jint int_tristate) = NULL;

#endif
