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

package ekconf.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import ekconf.EkconfActivator;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class EkconfPreferencesInitializer extends AbstractPreferenceInitializer {

	public EkconfPreferencesInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = EkconfActivator.getDefault()
				.getPreferenceStore();
		store.setDefault(EkconfPreferences.PREF_SHELL, "/bin/sh");
		store.setDefault(EkconfPreferences.PREF_PATH, "$PATH:$HOME/bin");
		store.setDefault(EkconfPreferences.PREF_INIT_MODE,
				EkconfPreferences.INIT_MODE_NORMAL);
	}

}
