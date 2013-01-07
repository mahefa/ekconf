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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import ekconf.kconfig.Configuration;

/**
 * Editor input.
 * @author Tiana Rakotovao
 */
public class EkconfEditorInput implements IEditorInput {

	private static EkconfEditorInput instance = null;

	private Configuration configuration;

	private EkconfEditorInput(Configuration conf) {
		configuration = conf;
	}

	public static EkconfEditorInput newInstance(Configuration conf) {
		if (null == instance) {
			instance = new EkconfEditorInput(conf);
		}
		return instance;
	}
	
	public Configuration getConfiguration(){
		return configuration;
	}
	
	/**
	 * Disposes the (single) current instance of EkconfEditorInput.
	 */
	public void disposeInstance() {
		if (configuration != null) {
			configuration.dispose();
			configuration = null;
		}
		instance = null;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return (instance != null);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Ekconf Editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Linux kernel configuration";
	}
}