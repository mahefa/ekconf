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

package ekconf.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import ekconf.EkconfActivator;
import ekconf.kconfig.Configuration;
import ekconf.preferences.EkconfPreferences;
import ekconf.ui.MainWindow;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class EkconfEditorPart extends EditorPart {

	public static final String ID = "ekconf.editor1";

	final public static String HELP_RUN = "Select the linux root folder in package explorer view, then go to Ekconf->Run menu.";

	private static Configuration configuration = null;

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (!configuration.writeConfiguration(null)) {
			throw new RuntimeException("Unable to save configuration!");
		}
		this.fireDirty();
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		if (!(input instanceof EkconfEditorInput)) {
			throw new PartInitException(HELP_RUN);
		}
		this.setInput(input);
		configuration = ((EkconfEditorInput)input).getConfiguration();		
	}

	@Override
	public boolean isDirty() {
		if (configuration != null)
			return configuration.hasChanged();
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		int mode = MainWindow.NORMAL_OPTIONS;
		String prefInitMode = EkconfActivator.getDefault().getPreferenceStore()
				.getString(EkconfPreferences.PREF_INIT_MODE);
		if (prefInitMode.equals(EkconfPreferences.INIT_MODE_ALL)) {
			mode = MainWindow.ALL_OPTIONS;
		}
		new MainWindow(parent, configuration, mode, this);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		MainWindow mainWindow = MainWindow.getActiveWindow();
		if (mainWindow != null)
			mainWindow.dispose();
		((EkconfEditorInput) this.getEditorInput()).disposeInstance();
		configuration = null;
	}

	public static boolean hasConfiguration() {
		return (configuration != null);
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public void fireDirty() {
		this.firePropertyChange(PROP_DIRTY);
	}
}