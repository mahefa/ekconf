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

package ekconf.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ekconf.EkconfActivator;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class EkconfPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static String PREF_KBUILD_ENV_VAR = "kbuild_environment_variables";
	public static String PREF_SHELL = "shell";
	public static String PREF_PATH = "path";
	public static String PREF_LIB_EKCONFIG = "libekconfig";
	public static String PREF_ALTERNATE_CONFIG_FILE = "alternate_configuration_file";
	public static String PREF_INIT_MODE = "init_mode";
	public static String INIT_MODE_ALL = "all";
	public static String INIT_MODE_NORMAL = "normal";

	public EkconfPreferences() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(EkconfActivator.getDefault().getPreferenceStore());
		setDescription("Ekconf Settings."
				+ "\n\nKbuild environment variables of the form VARIABLE=value."
				+ "\nExample : ARCH=arm  CROSS_COMPILE=arm-linux-gnueabi-");

	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		addField(new StringFieldEditor(PREF_KBUILD_ENV_VAR,
				"Kbuild variables : ", parent));

		addField(new StringFieldEditor(PREF_PATH,
				"Environment variable PATH : ", parent));

		addField(new FileFieldEditor(PREF_SHELL, "Shell : ", parent));

		addField(new FileFieldEditor(PREF_LIB_EKCONFIG,
				"libekconfig.so : ", parent));
		
		addField(new RadioGroupFieldEditor(PREF_INIT_MODE, "Initial mode :", 1,
				new String[][] { { "Normal", INIT_MODE_NORMAL },
						{ "Show all", INIT_MODE_ALL } }, parent));
	}

}
