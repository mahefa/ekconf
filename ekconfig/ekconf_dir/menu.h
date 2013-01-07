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

#ifndef _MENU_H
#define _MENU_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

jlong getpList(JNIEnv *env ,jobject jmenu);
jlong getpNext(JNIEnv *env, jobject jmenu);
jlong getpSym(JNIEnv *env , jobject jmenu);
jboolean hasProperty(JNIEnv *env , jobject jmenu);
jstring getPrompt(JNIEnv *env , jobject jmenu);
jboolean isVisible(JNIEnv *env , jobject jmenu);
jstring getHelp(JNIEnv *env , jobject jmenu);
jint getPropertyType(JNIEnv *env , jobject jmenu);
jlong getpParent(JNIEnv *env , jobject jmenu);

#ifdef __cplusplus
}
#endif

#endif
