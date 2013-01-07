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

package ekconf.internal.kconfig;

import ekconf.kconfig.KconfigException;
import ekconf.kconfig.Symbol;
import ekconf.kconfig.SymbolType;
import ekconf.kconfig.Tristate;

/**
 * The Class NativeSymbol.
 * @author Tiana Rakotovao
 */
public class NativeSymbol extends Symbol{
	
	private long pSymbol;
	
	private NativeSymbol(long pSym) throws KconfigException
	{
		if (0 == pSym)
			throw new KconfigException(
					"The corresponding struct symbol * is NULL");
		this.pSymbol = pSym;
		this.setSymbolType(SymbolType.valueOf(this.nativeGetSymbolType()));
	}
	
	static public NativeSymbol newInstance(long pSym) throws KconfigException
	{
		return new NativeSymbol(pSym);
	}
	
	public long getpSymbol()
	{
		return this.pSymbol;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof NativeSymbol))
			return false;
		NativeSymbol ns = (NativeSymbol)obj;
		if (this.pSymbol == ns.getpSymbol())
			return true;
		return false;
	}
	
	@Override
	public boolean isChoice() {
		return nativeIsChoice();
	}
	
	@Override
	public boolean isChoiceValue() {
		return nativeIsChoiceValue();
	}
	
	@Override
	public String getName() {
		return nativeGetName();
	}
	
	@Override
	public String getTypeName() {
		return nativeGetTypeName();
	}
	
	@Override
	public boolean isChangeable() {
		return nativeIsChangeable();
	}
	
	@Override
	public Tristate getTristateValue() {
		int t = nativeGetTristateValue();
		for (Tristate tr : Tristate.values()){
			if (t == tr.ordinal())
				return tr;
		}
		return null;
	}
	
	@Override
	public boolean setTristateValue(Tristate value) {
		return nativeSetTristateValue(value.ordinal());
	}
	
	@Override
	public Tristate toggleTristateValue() {
		int newVal = nativeToggleTristateValue();
		switch (newVal) {
		case 0 :
			return Tristate.no;
		case 1 :
			return Tristate.mod;
		case 2 :
			return Tristate.yes;
		}
		return null;
	}
	
	@Override
	public boolean hasValue() {
		return nativeHasValue();
	}
	
	@Override
	public String getStringValue() {
		return nativeGetStringValue();
	}
	
	@Override
	public boolean setStringValue(String value) {
		return nativeSetStringValue(value);
	}
	
	@Override
	public boolean tristateWithinRange(Tristate tristate) {
		if (this.nativeTristateWithinRange(tristate.ordinal()))
			return true;
		return false;		
	}
	
	private native int nativeGetSymbolType();
	
	private native boolean nativeIsChoice();
	
	private native boolean nativeIsChoiceValue();
	
	private native String nativeGetName();
	
	private native String nativeGetTypeName();
	
	private native boolean nativeIsChangeable();
	
	private native int nativeGetTristateValue();
	
	private native boolean nativeSetTristateValue(int value);
	
	private native int nativeToggleTristateValue();
	
	private native boolean nativeHasValue();
	
	private native String nativeGetStringValue();
	
	private native boolean nativeSetStringValue(String value);
	
	private native boolean nativeTristateWithinRange(int tristate);
}