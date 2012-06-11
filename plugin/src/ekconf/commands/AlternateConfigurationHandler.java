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

package ekconf.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import ekconf.EkconfActivator;
import ekconf.editors.EkconfEditorPart;
import ekconf.kconfig.Configuration;
import ekconf.preferences.EkconfPreferences;
import ekconf.ui.MainWindow;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class AlternateConfigurationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		MainWindow mainWindow = MainWindow.getActiveWindow();
		if (mainWindow == null) {
			return null;
		}

		FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell());
		fileDialog.setText("Select an alternative configuration file");
		String alternate = EkconfActivator.getDefault().getPreferenceStore()
				.getString(EkconfPreferences.PREF_ALTERNATE_CONFIG_FILE);
		if (alternate != null && !alternate.equals("")){
			fileDialog.setFileName(alternate);
		}
		String selected = fileDialog.open();

		Configuration configuration = EkconfEditorPart.getConfiguration();
		if (configuration.loadAlternateConfiguration(selected)) {
			mainWindow.getMenuTree().refresh();
			EkconfActivator
					.getDefault()
					.getPreferenceStore()
					.setValue(EkconfPreferences.PREF_ALTERNATE_CONFIG_FILE,
							selected);
			mainWindow.getEkconfEditorPart().fireDirty();
		}

		return null;
	}
}
