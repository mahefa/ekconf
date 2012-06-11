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

#include "symbol.h"

jint getSymbolType(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	return (jint) sym->type;
}

jboolean isChoice(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if ( sym_is_choice(sym) )
		return JNI_TRUE;
	return JNI_FALSE;
}

jboolean isChoiceValue(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if (sym_is_choice_value(sym))
		return JNI_TRUE;
	return JNI_FALSE;
}

jstring getName(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if (sym->name)
		return JNU_NewStringNative(env, sym->name);
	return NULL;
}

jstring getTypeName(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	return JNU_NewStringNative(env, sym_type_name(sym->type));
}

jboolean isChangeable(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if (sym_is_changable(sym))
		return JNI_TRUE;
	return JNI_FALSE;
}

jint getTristateValue(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	return (jint) sym_get_tristate_value(sym);
}

jboolean setTristateValue(JNIEnv *env , jobject jsymbol, jint value)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if (sym_set_tristate_value(sym , (tristate)value)) 
		return JNI_TRUE;
	return JNI_FALSE;
}

jint toggleTristateValue(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	return (jint) sym_toggle_tristate_value(sym);
}

jboolean hasValue(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if (sym_has_value(sym))
		return JNI_TRUE;
	return JNI_FALSE;
}

jstring getStringValue(JNIEnv *env , jobject jsymbol)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	return JNU_NewStringNative( env, sym_get_string_value(sym) );
}

jboolean setStringValue(JNIEnv *env , jobject jsymbol, jstring value)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if ( sym_set_string_value(sym , JNU_GetStringNativeChars(env,value)) )
		return JNI_TRUE;
	return JNI_FALSE;
}

jboolean tristateWithinRange(JNIEnv *env , jobject jsymbol, jint int_tristate)
{
	jlong p_symbolF = env->GetLongField(jsymbol , FID_Symbol_pSymbol);
	JNU_CheckAndThrowException(env);
	struct symbol * sym = (struct symbol *) p_symbolF;
	if( sym_tristate_within_range(sym,(tristate) int_tristate ))
		return JNI_TRUE;
	return JNI_FALSE;
}
