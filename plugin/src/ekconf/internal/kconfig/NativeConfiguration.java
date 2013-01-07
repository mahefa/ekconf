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

package ekconf.internal.kconfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import ekconf.EkconfActivator;
import ekconf.kconfig.Configuration;
import ekconf.kconfig.KconfigException;
import ekconf.kconfig.Menu;
import ekconf.kconfig.MenuFactory;
import ekconf.preferences.EkconfPreferences;

/**
 * The Class NativeConfiguration. A subclass of the class Configuration, using
 * native methods.
 * 
 * @author Tiana Rakotovao
 */
public class NativeConfiguration extends Configuration {

	private long pRootMenu;
	private Process process;

	private static String patchMakefile = "\n"
			+ "ekconfEnvFile=ekconfEnv \n"
			+ "PHONY+= ekconfig \n"
			+ "ifdef KBUILD_KCONFIG \n"
			+ "    Kconfig := \\$(KBUILD_KCONFIG) \n"
			+ "else \n"
			+ "    Kconfig := arch/\\$(SRCARCH)/Kconfig \n"
			+ "endif \n"
			+ "ifeq (\\$(COMMON_CONFIG_ENV), ) \n"
			+ "    EKCONF_CONFIG_IN=\\$(Kconfig) \n"
			+ "else \n"
			+ "    EKCONF_CONFIG_IN=\\$(CONFIG_CONFIG_IN) \n"
			+ "endif \n"
			+ "ekconfig : \n"
			+ "\t@echo \\$(EKCONF_CONFIG_IN) > \\$(ekconfEnvFile) \n"
			+ "\t@env | wc -L >> \\$(ekconfEnvFile) \n"
			+ "\t@env | wc -l >> \\$(ekconfEnvFile) \n"
			+ "ifneq (\\$(COMMON_CONFIG_ENV), ) \n"
			+ "\t@\\$(foreach var,\\$(COMMON_CONFIG_ENV), echo  \\$(var) >> \\$(ekconfEnvFile) ;) \n"
			+ "endif \n" 
			+ "\t@env >> \\$(ekconfEnvFile) \n";

	private NativeConfiguration() {
		super();
	}

	public static NativeConfiguration newInstance() {
		return new NativeConfiguration();
	}

	@Override
	public void init(String folderPath) throws KconfigException {
		if (null == folderPath) {
			throw new IllegalArgumentException("The name of the configuration"
					+ " file and its path should be non null");
		}
		String kbuildEnvironment = null;
		String shellShPath = null;
		IPreferenceStore store = null;
		String libekconfigPath = null;
		String pathEnvironment = null;

		EkconfActivator activator = EkconfActivator.getDefault();
		if (activator != null) {
			store = activator.getPreferenceStore();
			kbuildEnvironment = store.getString(
					EkconfPreferences.PREF_KBUILD_ENV_VAR).trim();
			if (kbuildEnvironment == null) {
				kbuildEnvironment = "";
			}
			shellShPath = store.getString(EkconfPreferences.PREF_SHELL).trim();
			if ((shellShPath == null || shellShPath.equals("") || !new File(
					shellShPath).exists())) {
				throw new KconfigException("Shell : "+ shellShPath+" not found."
						+ " Go to Window->Preferences->Ekconf to set it.");
			}
			libekconfigPath = store.getString(EkconfPreferences.PREF_LIB_EKCONFIG).trim();
			if (libekconfigPath == null || libekconfigPath.equals("") || 
					! new File(libekconfigPath).exists()) {
				throw new KconfigException("Could not find libekconfig.so. Go to Window->Preferences" +
						"->Ekconf to set it.");
			}
			pathEnvironment = store.getString(EkconfPreferences.PREF_PATH).trim();
			if (pathEnvironment == null || pathEnvironment.equals("")) {
				pathEnvironment = "$PATH";
			}
		} else {
			// just for junit test
			shellShPath = "/bin/sh";
			libekconfigPath = "lib/libekconfig.so";
			pathEnvironment = "$PATH";
			kbuildEnvironment = "";
		}

		String script =
				"export PATH="+pathEnvironment+" ;"
				+ "cd " + folderPath + " ;"
				+ "cp Makefile MakefileEkconf2012 ; "
				+ "if [ $? -ne 0 ] ; then sleep 1; exit 1 ; fi; "
				+ "echo \""+patchMakefile + "\" >> MakefileEkconf2012 ;" 
				+ "make -f MakefileEkconf2012 ekconfig " + kbuildEnvironment + " ; "
				+ "if [ $? -ne 0 ] ;then sleep 1; rm -f MakefileEkconf2012; exit 1;fi ;"
				+ "rm -f MakefileEkconf2012";
		String cmd[] = { shellShPath, "-c", script };
		try {
			process = Runtime.getRuntime().exec(cmd);
			
			if (0 != process.waitFor()) {
				process.destroy();
				throw new KconfigException(
						"Make sure that the selected directory is the root"
								+ " directory of the linux kernel or buildroot. If so, check if GNU make is "
								+ "installed in your system and available in the PATH.");
			}
			process.destroy();
			nativeInit(folderPath, libekconfigPath,
					"ekconf/internal/kconfig/NativeConfiguration", "pRootMenu",
					"ekconf/internal/kconfig/NativeMenu", "pMenu",
					"ekconf/internal/kconfig/NativeSymbol", "pSymbol");
			setRootMenu(MenuFactory.newMenu(pRootMenu, null));
		} catch (Exception e) {
			dispose();
			throw new KconfigException(e.getMessage());
		}
	}

	@Override
	public void dispose() {
		nativeDispose();
		setInstance(null);
	}

	private long getpRootMenu() {
		return pRootMenu;
	}

	public boolean hasChanged() {
		return nativeHasChanged();
	}

	public boolean writeConfiguration(String fileName) {
		return nativeWriteConfiguration(fileName);
	}

	@Override
	public List<Menu> findMenu(String pattern) {
		if (pattern == null) {
			return null;
		}
		long[] pointerTab = null;
		pointerTab = nativefindMenu(pattern);
		if (pointerTab == null) {
			return null;
		}
		List<Menu> found = new ArrayList<Menu>();
		List<Long> pointerList = new ArrayList<Long>();
		for (int i = 0; i < pointerTab.length; i++) {
			pointerList.add(new Long(pointerTab[i]));
		}
		addMenuToList(pointerList, found, this.getRootMenu());
		return found;
	}

	private void addMenuToList(List<Long> pointerList, List<Menu> found,
			Menu menu) {
		if (!pointerList.isEmpty()) {
			for (int i = 0; i < pointerList.size(); i++) {
				Long pt = (Long) pointerList.get(i);
				if (pt.longValue() == ((NativeMenu) menu).getpMenu()) {
					// menu found
					found.add(menu);
					pointerList.remove(pt);
					break;
				}
			}
			if (menu.hasChildren()) {
				for (Menu m : menu.getChildren())
					addMenuToList(pointerList, found, m);
			}
		}
	}

	@Override
	public boolean loadAlternateConfiguration(String configPath) {
		return nativeLoadAlternateConfiguration(configPath);
	}

	@Override
	public boolean saveAlternateConfiguration(String configPath) {
		return false;
	}

	native private void nativeInit(String path, String libekconfigPath,
			String configurationClassPath,
			String configurationpRootMenuVariableName, String menuClassPath,
			String menupMenuVariableName, String symbolClassPath,
			String symbolpSymbolVariableName) throws KconfigException;

	private native boolean nativeHasChanged();

	native private void nativeDispose();

	native private boolean nativeWriteConfiguration(String fileName);

	native private long[] nativefindMenu(String pattern);

	native private boolean nativeLoadAlternateConfiguration(String configPath);
	
	/* load the shared library */
	static {
		System.loadLibrary("jnikconfig");
	}

}
