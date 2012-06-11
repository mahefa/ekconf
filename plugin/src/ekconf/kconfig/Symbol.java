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

package ekconf.kconfig;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public abstract class Symbol {
	
	SymbolType symbolType;

	public SymbolType getSymbolType() {
		return symbolType;
	}

	protected void setSymbolType(SymbolType symbolType) {
		this.symbolType = symbolType;
	}
	
 	public abstract boolean isChoice();
 	
 	public abstract boolean isChoiceValue();
	 
 	public abstract String getTypeName();
 	
	public abstract String getName();
	 
 	public abstract boolean isChangeable();
 	
	public abstract Tristate getTristateValue();
	 
 	public abstract boolean setTristateValue(Tristate value);
 	
	public abstract Tristate toggleTristateValue();
 	
	public abstract boolean hasValue();
	 
	public abstract String getStringValue();
	
	public abstract boolean setStringValue(String value);
	
	public abstract boolean tristateWithinRange(Tristate tristate);
}
