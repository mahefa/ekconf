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

#ifndef _JNICONF_H
#define _JNICONF_H

#define EXCEPTION_CLASS "ekconf/kconfig/KconfigException"
#define NATIVE_ENV_ERROR_MSG "env"
#define NATIVE_CHDIR_ERROR_MSG "chdir"

#include <jni.h>

jclass 	configurationClass=NULL;
jobject	configurationObject=NULL;
jclass	stringClass=NULL;
jclass 	menuClass=NULL;
jclass 	symbolClass=NULL;

jfieldID FID_Configuration_pRootMenu;
jfieldID FID_Menu_pMenu;
jfieldID FID_Symbol_pSymbol;

jmethodID MID_String_getBytes;
jmethodID MID_String_constructorFromBytes;

#endif
