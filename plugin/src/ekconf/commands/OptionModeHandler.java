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

package ekconf.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ekconf.EkconfActivator;
import ekconf.preferences.EkconfPreferences;
import ekconf.ui.MainWindow;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class OptionModeHandler extends AbstractHandler {

	// the following values are defined in plugin.xml
	public static final String NORMAL_OPTIONS_STRING = "normal";
	public static final String ALL_OPTIONS_STRING = "all";

	static private String parameterId = "ekconf.optionModeParameter";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String param = event.getParameter(parameterId);
		MainWindow mainWindow = MainWindow.getActiveWindow();
		if (mainWindow == null) {
			return null;
		}
		int mode = 0 ;
		String pref = "";
		if (param.equals(ALL_OPTIONS_STRING)
				&& mainWindow.getOptionMode() != MainWindow.ALL_OPTIONS) {
			mode = MainWindow.ALL_OPTIONS;
			pref = EkconfPreferences.INIT_MODE_ALL;
		} else if (param.equals(NORMAL_OPTIONS_STRING)
				&& mainWindow.getOptionMode() != MainWindow.NORMAL_OPTIONS) {
			mode = MainWindow.NORMAL_OPTIONS;
			pref = EkconfPreferences.INIT_MODE_NORMAL;
		}
		mainWindow.setOptionMode(mode);
		mainWindow.getMenuTree().refresh(false);
		EkconfActivator.getDefault().getPreferenceStore()
				.setValue(EkconfPreferences.PREF_INIT_MODE, pref);
		return null;
	}
}
