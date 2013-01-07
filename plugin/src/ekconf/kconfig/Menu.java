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
 * A menu is an entry in a kbuild configuration file.
 * 
 * @author Tiana Rakotovao
 */
public abstract class Menu {
	
	private Menu parent = null;
	private List<Menu> children = null;
	private Symbol symbol = null;
	private String prompt = null;
	private PropertyType propertyType = null;

	protected Menu(){}
	
	public Menu getParent() {
		return parent;
	}
	
	protected void setParent(Menu parent) {
		this.parent = parent;
	}
	
	protected void setChildren(List<Menu> children)
	{
		this.children = children;
	}
	
	public List<Menu> getChildren(){
		return children;
	}

	public boolean hasChildren()
	{
		if (null == this.children)
			return false;
		return true;
	}
	
	public abstract boolean hasSibling();
	
	public boolean hasSymbol() {
		if (null == this.symbol)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		if (this.prompt != null)
			return this.prompt;
		else {
			if (this.hasSymbol())
				return this.getSymbol().getName();
		}
		return null;
	}

	public String getPrompt() {
		return prompt;
	}

	protected void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public boolean hasPrompt() {
		return (null != this.prompt);
	}
	
	public Symbol getSymbol(){
		return symbol;
	}
	
	protected void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
	public abstract boolean hasProperty();
	
	public PropertyType getPropertyType() {
		return this.propertyType;
	}
	
	protected void setPropertyType(PropertyType prop)
	{
		this.propertyType = prop;
	}
	
	public abstract boolean isVisible();
	
	public abstract String getHelp();

}
