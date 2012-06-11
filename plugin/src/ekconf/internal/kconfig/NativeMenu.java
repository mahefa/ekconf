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

package ekconf.internal.kconfig;

import java.util.ArrayList;
import java.util.List;

import ekconf.kconfig.KconfigException;
import ekconf.kconfig.Menu;
import ekconf.kconfig.MenuFactory;
import ekconf.kconfig.PropertyType;

/**
 * The Class NativeMenu.
 * A subclass of the class Menu, using native
 * methods.
 * 
 * @author Tiana Rakotovao
 */
public class NativeMenu extends Menu {
	
	private long pMenu;
	
	private NativeMenu(long pMenu , Menu parent) throws KconfigException
	{
		super();
		if ( 0 == pMenu)
			throw new KconfigException("The corresponding struct menu * is NULL");
		
		this.pMenu = pMenu;
		
		this.setParent(parent);
		
		this.setPrompt(nativeGetPrompt());
		
		this.setPropertyType(PropertyType.valueOf(this.nativeGetPropertyType()));
		
		long sym = nativeGetpSym();
		if (0 != sym)
			setSymbol(NativeSymbol.newInstance(sym));
		
		long list = nativeGetpList();
		if (0 != list){
			List<Menu> children = new ArrayList<Menu>();
			this.setChildren(children);
			NativeMenu menu = (NativeMenu)MenuFactory.newMenu(list, this);
			children.add(menu);
			long next = menu.nativeGetpNext();
			while (0 != next) {
				menu = (NativeMenu)MenuFactory.newMenu(next, this);
				children.add(menu);
				next = ((NativeMenu)menu).nativeGetpNext();
			}
			
		}
		else {
			this.setChildren(null);
		}
	}

	public static NativeMenu newInstance(long pMenu , Menu parent) throws KconfigException
	{
		return new NativeMenu(pMenu,parent);
	}
	
	public long getpMenu() {
		return pMenu;
	}
	
	@Override
	public boolean hasSibling() {
		if (0 == nativeGetpNext())
			return false;
		return true;
	}
	
	@Override
	public boolean hasProperty() {
		return nativeHasProperty();
	}
	
	@Override
	public boolean isVisible() {
		return nativeIsVisible();
	}
	
	@Override
	public String getHelp(){
		return nativeGetHelp();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof NativeMenu))
			return false;
		NativeMenu nm = (NativeMenu)obj;
		if (this.pMenu == nm.getpMenu())
			return true;
		return false;
	}
	
	native private long nativeGetpList();
	
	native public long nativeGetpNext();
	
	native private long nativeGetpSym();
	
	private native boolean nativeHasProperty();
	
	private native String nativeGetPrompt();
	
	private native boolean nativeIsVisible();
	
	private native String nativeGetHelp();
	
	private native int nativeGetPropertyType();
	
	public native int getpParent();

}
