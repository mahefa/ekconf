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

/*
 * include this file inside the implementation of the JNI interface of the
 * Configuration class
 */
#include <vector>
#include <iostream>
#include <string.h>
#include <stdlib.h>
#include <malloc.h>
#include <unistd.h>
#include <dlfcn.h>

#include "config.h"

using std::cout;
using std::endl;


#define EKCONF_TMP_FILENAME		"ekconfEnv"

static char ** environment;
static int envVarNb;
static char* kconifFileName=NULL;
static char* alternateConfFile = NULL;

void clean_flag_of_files(struct menu* menu){
	if (menu == NULL)
		return;
	if(menu->list)
		clean_flag_of_files(menu->list);
	if(menu->next)
		clean_flag_of_files(menu->next);
	if(menu->file && ( menu->file->flags != 0 ) )
		menu->file->flags = 0;
}

void initConfigurationIDs(JNIEnv *env, 	jobject configObject,
		jstring configurationClassPath,	jstring configuration_pRootMenuVariableName,
		jstring menuClassPath , 	jstring Menu_pMenuVariablename , 
		jstring symbolClassPath, 	jstring Symbol_pSymbolVariableName)
{
	jclass _cls_String = env->FindClass("java/lang/String");
	if(_cls_String == NULL ){
			return;
	}
	stringClass = (jclass)env->NewGlobalRef(_cls_String);
	if(stringClass == NULL){
			return;
	}

	MID_String_getBytes = env->GetMethodID(stringClass , "getBytes","()[B");
	if(MID_String_getBytes == NULL ){
			return;
	}
	MID_String_constructorFromBytes = env->GetMethodID(stringClass,"<init>","([B)V");
	if(MID_String_constructorFromBytes == NULL ){
			return;
	}
	env->DeleteLocalRef(_cls_String);

	jclass _configurationClass = env->FindClass(JNU_GetStringNativeChars(env,configurationClassPath));
	configurationClass = (jclass)env->NewGlobalRef(_configurationClass);
	if(configurationClass == NULL )
			return;

	FID_Configuration_pRootMenu = env->GetFieldID(_configurationClass,
		JNU_GetStringNativeChars(env,configuration_pRootMenuVariableName),"J");
	if( FID_Configuration_pRootMenu == NULL )
			return;
	env->DeleteLocalRef(_configurationClass);

	configurationObject = env->NewGlobalRef(configObject);

	jclass _menuClass = env->FindClass(JNU_GetStringNativeChars(env,menuClassPath));
	if(_menuClass == NULL)
			return;
	menuClass = (jclass)env->NewGlobalRef(_menuClass);
	if(menuClass == NULL)
			return;
	env->DeleteLocalRef(_menuClass);

	FID_Menu_pMenu = env->GetFieldID(menuClass,
		JNU_GetStringNativeChars(env,Menu_pMenuVariablename),"J");
	if(FID_Menu_pMenu == NULL )
			return;

	jclass _symbolClass = env->FindClass(
			JNU_GetStringNativeChars(env,symbolClassPath));
	if(_symbolClass == NULL )
			return;
	symbolClass = (jclass) env->NewGlobalRef(_symbolClass);
	if(symbolClass==NULL)
			return;

	FID_Symbol_pSymbol = env->GetFieldID(symbolClass, 
		JNU_GetStringNativeChars(env, Symbol_pSymbolVariableName) , "J");
	if(FID_Symbol_pSymbol == NULL )
			return;
	env->DeleteLocalRef(_symbolClass);
}


void fixup_rootmenu(struct menu *menu)
{
	struct menu *child;
	static int menu_cnt = 0;

	menu->flags |= MENU_ROOT;
	for (child = menu->list; child; child = child->next) {
		if (child->prompt && child->prompt->type == P_MENU) {
			menu_cnt++;
			fixup_rootmenu(child);
			menu_cnt--;
		} else if (!menu_cnt)
			fixup_rootmenu(child);
	}
}

int setEnvironment(JNIEnv *env)
{
	FILE * file=NULL;
	char* line;
	int i, j, lineLength;
	file = fopen(EKCONF_TMP_FILENAME, "r");
	if (file == NULL){
		return -1;
	}
	
	line = NULL;
	line = (char *)malloc(200*sizeof(char));
	if (NULL == line){
		return -1;
	}

	fgets(line, 150, file);
	line[strlen(line)-1]='\0';
	kconifFileName = (char *)malloc(sizeof(char)*(strlen(line)+1));
	strcpy (kconifFileName, line);

	fgets(line , 10 ,file);
	line[strlen(line)-1]='\0';
	lineLength = atoi(line)+5;

	fgets(line , 10 ,file);
	line[strlen(line)-1]='\0';
	envVarNb = atoi(line)+10;
	free(line);

	line = NULL;
	line = (char*) malloc(lineLength*sizeof(char));
	if (NULL == line){
		return -1;
	}
	environment = NULL;
	environment = (char**)malloc(sizeof(char*)*envVarNb);
	if (NULL == environment){
		return -1;
	}
	for (i=0 ; i<envVarNb ; i++) {
		if (NULL == fgets(line,lineLength ,file)){
			break;
		}
	 	line[strlen(line)-1]='\0';
		environment[i] = NULL;
	 	environment[i] = (char*) malloc ((strlen(line)+1)*sizeof(char));
		if (NULL == environment[i]){
			return -1;
		}
	 	strcpy(environment[i], line);
	 	putenv(environment[i]);
	}
	envVarNb = i;
	free(line);
	fclose(file);
	unlink(EKCONF_TMP_FILENAME);
	return 0;
}

int parseKconfig (JNIEnv * env , jstring jpath)
{
	char * path = NULL;
	char *cmd;
	jclass classConfiguration;
	path = JNU_GetStringNativeChars(env,jpath);
	if ( path != NULL ){
	     if( chdir (path) == -1 ){
		     perror( "chdir()") ;
		     JNU_ThrowByName(env , EXCEPTION_CLASS , NATIVE_CHDIR_ERROR_MSG);
		     return -1;
	     }
	     free(path);
	}

	if (-1 == setEnvironment(env)) {
		JNU_ThrowByName(env , EXCEPTION_CLASS , NATIVE_ENV_ERROR_MSG);
		return -1;
	}

	bindtextdomain(PACKAGE, LOCALEDIR);
	textdomain(PACKAGE);

#ifndef LKC_DIRECT_LINK
	kconfig_load();
#endif

	conf_parse(kconifFileName);
	fixup_rootmenu(&rootmenu);
	conf_read(NULL);
	//zconfdump(stdout);

	return 0;
}

void initConfiguration(JNIEnv * env, jobject jconfigurationObj , 
		jstring jpath, jstring configurationClassPath, 
		jstring Configuration_pRootMenuVariableName,
		jstring menuClassPath, 	
		jstring Menu_pMenuVariablename,
		jstring symbolClassPath , 
		jstring Symbol_pSymbolVariableName) {

	initConfigurationIDs(env, jconfigurationObj, 
		configurationClassPath , Configuration_pRootMenuVariableName,
		menuClassPath , Menu_pMenuVariablename,
		symbolClassPath , Symbol_pSymbolVariableName);

	if (-1 == parseKconfig(env , jpath )){
		dispose(env);
		return;
	}
 	env->SetLongField(jconfigurationObj ,
			FID_Configuration_pRootMenu,(jlong)&rootmenu); 
}

void dispose(JNIEnv *env)
{
	int i;
	if(configurationObject!=NULL) {
		env->DeleteGlobalRef(configurationObject);
		configurationObject=NULL;
	}
	if(configurationClass!=NULL){
		env->DeleteGlobalRef(configurationClass);
		configurationClass=NULL;
	}
	if(stringClass!=NULL) {
		env->DeleteGlobalRef(stringClass);
		stringClass=NULL;
	}
	if(menuClass!=NULL){
		env->DeleteGlobalRef(menuClass);
		menuClass=NULL;
	}
	if(symbolClass!=NULL) {
		env->DeleteGlobalRef(symbolClass);
		symbolClass=NULL;
	}
	if (alternateConfFile != NULL){
		free(alternateConfFile);
		alternateConfFile = NULL;
	}
	clean_flag_of_files(&rootmenu);
	if (environment != NULL) {
		for (i=0; i<envVarNb; i++){
			unsetenv(strtok(environment[i],"="));
		}
		for (i=0 ; i<envVarNb ; i++){
			free(environment[i]);
		}
		free(environment);
		environment = NULL;
	}
}

jboolean hasChanged(JNIEnv *env, jobject jconfigObj) {
	if (conf_get_changed())
		return JNI_TRUE;
	return JNI_FALSE;
}

jboolean writeConfiguration(JNIEnv *env ,jobject jconfigObj ,jstring fileName) {
	char * file = NULL;
	if (fileName == NULL) {
		if (alternateConfFile == NULL) {
			file = (char*)conf_get_configname();
		} else {
			file = alternateConfFile;
		}
		if(0 == conf_write(file))
			return JNI_TRUE;
		return JNI_FALSE;
	}
	file = JNU_GetStringNativeChars(env, fileName);
	if (file!= NULL ) {
		if (0 == conf_write(file)) {
			free(file);
			return JNI_TRUE;
		}
	}
	return JNI_FALSE;
}

jlongArray findMenu(JNIEnv *env, jobject jconfigObj, jstring pattern) {
	struct symbol **p;
	struct symbol **result;
	struct property *prop;
	int size;
	jlong *tab;
	jlongArray array=NULL;

	using std::vector;
	vector<jlong> vect;

	result = sym_re_search(JNU_GetStringNativeChars(env,pattern));
	if (!result)
		return NULL;
	for (p = result; *p; p++) {
		for_all_prompts((*p), prop)
			vect.push_back((jlong)prop->menu);
	}
	size=vect.size();
	if(size==0)
		return NULL;
	tab = new jlong[size];
	for(int i=0;i<size;i++)
		tab[i]=vect[i];
	array = env->NewLongArray(size);
	if(array == NULL){
		return NULL;
	}
	env->SetLongArrayRegion(array,0,size,tab);
	delete tab;
	return array;
}

jboolean loadAlternateConfiguration(JNIEnv *env , jobject jconfigObj , jstring configPath) {
	if (alternateConfFile != NULL){
		free(alternateConfFile);
	}
	alternateConfFile = JNU_GetStringNativeChars(env, configPath);
	if (NULL != alternateConfFile && 0 == conf_read(alternateConfFile)){
		sym_set_change_count(1);
		return JNI_TRUE;
	}
	alternateConfFile = NULL;
	return JNI_FALSE;
}
