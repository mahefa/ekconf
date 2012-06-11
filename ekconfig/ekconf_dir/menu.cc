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

#include <iostream>
#include "menu.h"
using std::cout;

jlong getpList(JNIEnv *env ,jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	return (jlong) pt->list;
}

jlong getpNext(JNIEnv *env, jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu* ) menuF;
	return (jlong) pt->next;
}

jlong getpSym(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu* ) menuF;
	return (jlong) pt->sym;
}

jboolean hasProperty(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu* ) menuF;
	if (pt->prompt)
		return JNI_TRUE;
	else
		return JNI_FALSE;
}

jstring getPrompt(JNIEnv *env, jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	if (pt->prompt)
		return JNU_NewStringNative(env,menu_get_prompt(pt)) ;
	else
		return NULL;
}

jboolean isVisible(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	if (menu_is_visible(pt))
		return JNI_TRUE;
	return JNI_FALSE;
}

jstring getHelp(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	struct gstr help;
	jstring result;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	help = str_new();
	menu_get_ext_help(pt, &help);
	result = JNU_NewStringNative(env , str_get(&help));
	str_free(&help);
	return result;
}

jint getPropertyType(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	if(pt->prompt)
		return (jint) pt->prompt->type;
	else
		return (jint)-1;
}

jlong getpParent(JNIEnv *env , jobject jmenu)
{
	jlong menuF;
	struct menu *pt;
	menuF = env->GetLongField(jmenu , FID_Menu_pMenu);
	JNU_CheckAndThrowException(env);
	pt = (struct menu *) menuF;
	return (jlong) pt->parent;
}
