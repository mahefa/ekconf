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
import org.eclipse.swt.widgets.Display;

import ekconf.ui.MainWindow;
import ekconf.ui.SearchWindow;

/**
 * 
 * @author Tiana Rakotovao
 *
 */

public class FindHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Display display = null;
		MainWindow mainWindow = MainWindow.getActiveWindow();
		if (mainWindow == null)
			return null;
		display = mainWindow.getDisplay();
		if(display == null)
			return null;
		SearchWindow searchWindow = SearchWindow.createSearchWindow(display);
		if(searchWindow.hasShell()){
			searchWindow.setFocus();
		}
		else{
			searchWindow.open();
		}
		return null;
	}

}
