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

#ifndef _CONFIG_H
#define _CONFIG_H

#include <jni.h>

void (*initConfiguration)(JNIEnv * env, jobject jconfigurationObj ,  
 		jstring jpath,
		jstring configurationClassPath, 
 		jstring Configuration_pRootMenuVariableName,
 		jstring menuClassPath, 	
 		jstring Menu_pMenuVariablename,
 		jstring symbolClassPath , 
 		jstring Symbol_pSymbolVariableName) = NULL;
void (*dispose)(JNIEnv *env) = NULL;
jboolean (*hasChanged)(JNIEnv *env, jobject jconfigObj) = NULL;
jboolean (*writeConfiguration)(JNIEnv *env ,jobject jconfigObj ,jstring fileName) = NULL;
jlongArray (*findMenu)(JNIEnv *env, jobject jconfigObj, jstring pattern) = NULL;
jboolean (*loadAlternateConfiguration)(JNIEnv *env ,jobject jconfigObj, jstring configPath) = NULL;

#endif
