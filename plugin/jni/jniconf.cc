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


#include "config.h"
#include "menu.h"
#include "symbol.h"
/*
 * JNI functions implementation
 */
#include "implementation/ekconf_internal_kconfig_NativeConfiguration.cc"
#include "implementation/ekconf_internal_kconfig_NativeMenu.cc"
#include "implementation/ekconf_internal_kconfig_NativeSymbol.cc"

//////////////////////////// TEST ///////////////////////////////
//
// #include <iostream>
// #include <stdlib.h>
// using std::cout;
// static const char *progname;
// int main(int ac, char** av)
// {
// 
// 	const char *name;
// // <RAK>
// 	cout<<"ARCH = "<< getenv("ARCH") <<"\n";
// 	cout<<"KERNELVERSION = "<< getenv("KERNELVERSION") <<"\n";
// // </RAK>
// 	bindtextdomain(PACKAGE, LOCALEDIR);
// 	textdomain(PACKAGE);
// 
// #ifndef LKC_DIRECT_LINK
// 	kconfig_load();
// #endif
// 
// 	progname = av[0];
// // <RAK>
// //	configApp = new QApplication(ac, av);
// // </RAK>
// 		name = av[1];
// 	conf_parse(name);
// 	fixup_rootmenu(&rootmenu);
// 	conf_read(NULL);
// 
// 	return 0;
// }
