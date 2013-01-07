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

package ekconf.kconfig;

import java.util.List;

/**
 * This class scans kbuild configuration files in directories
 * and generates the .config file.
 *
 * @author Tiana Rakotovao
 */
public abstract class Configuration {
	
	/* Singleton */
	static protected Configuration instance;
	
	private Menu rootMenu;
	
	/**
	 * Gets the single instance of Configuration.
	 *
	 * @return single instance of Configuration
	 */
	public static Configuration getInstance() {
		return instance;
	}

	public static void setInstance(Configuration instance) {
		Configuration.instance = instance;
	}
	
	protected Configuration(){}
	
	public abstract void init(String path) throws KconfigException;

	protected void setRootMenu(Menu rootMenu) {
		this.rootMenu = rootMenu;
	}

	public Menu getRootMenu() {
		return rootMenu;
	}
	
	public abstract boolean hasChanged();
	
	public abstract boolean writeConfiguration (String fileName);
	
	abstract public void dispose(); 
	
	abstract public List<Menu> findMenu(String pattern);
	
	abstract public boolean loadAlternateConfiguration(String configPath);
	
	abstract public boolean saveAlternateConfiguration(String configPath);

}