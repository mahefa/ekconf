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

#ifndef _MENU_H
#define _MENU_H

#include <jni.h>

jlong (*getpList)(JNIEnv *env ,jobject jmenu) = NULL;
jlong (*getpNext)(JNIEnv *env, jobject jmenu) = NULL;
jlong (*getpSym)(JNIEnv *env , jobject jmenu) = NULL;
jboolean (*hasProperty)(JNIEnv *env , jobject jmenu) = NULL;
jstring (*getPrompt)(JNIEnv *env , jobject jmenu) = NULL;
jboolean (*isVisible)(JNIEnv *env , jobject jmenu) = NULL;
jstring (*getHelp)(JNIEnv *env , jobject jmenu) = NULL;
jint (*getPropertyType)(JNIEnv *env , jobject jmenu) = NULL;
jlong (*getpParent)(JNIEnv *env , jobject jmenu) = NULL;

#endif
