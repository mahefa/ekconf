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

package ekconf.ui;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import ekconf.kconfig.Menu;
import ekconf.kconfig.Symbol;
import ekconf.kconfig.SymbolType;
import ekconf.kconfig.Tristate;

/**
 * A ColumnLabelProvider for all columns expect the Option column
 * @author Tiana Rakotovao 
 */
public class EkconfColumnLabelProvider extends ColumnLabelProvider {

	private MainWindow mainWindow;
	private ColumnIndex index;

	public EkconfColumnLabelProvider(MainWindow mainWin, ColumnIndex idx) {
		mainWindow = mainWin;
		index = idx;
	}

	@Override
	public String getText(Object element) {
		Menu current = (Menu) element;
		Symbol sym = null;
		SymbolType type = null;
		Tristate expr = null;
		if (!current.hasSymbol())
			return null;

		if (index.equals(ColumnIndex.NAME_COL))
			return current.getSymbol().getName();

		sym = current.getSymbol();
		type = sym.getSymbolType();
		switch (type) {
		case S_BOOLEAN:
		case S_TRISTATE:
			if (!sym.isChangeable()) {
				return null;
			}
			expr = sym.getTristateValue();
			switch (index) {
			case NO_COL:
				if (expr.equals(Tristate.no))
					return MainWindow.NO;
				return (sym.tristateWithinRange(Tristate.no) ? "_" : null);
			case MOD_COL:
				if (expr.equals(Tristate.mod))
					return MainWindow.MOD;
				return (sym.tristateWithinRange(Tristate.mod) ? "_" : null);
			case YES_COL:
				if (expr.equals(Tristate.yes))
					return MainWindow.YES;
				return (sym.tristateWithinRange(Tristate.yes) ? "_" : null);
			case VALUE_COL:
				switch (expr) {
				case no:
					return MainWindow.NO;
				case mod:
					return MainWindow.MOD;
				default:
					return MainWindow.YES;
				}
			}
		case S_INT:
		case S_HEX:
		case S_STRING:
			if (index.equals(ColumnIndex.VALUE_COL))
				return sym.getStringValue();
		}

		return null;
	}

	@Override
	public Color getForeground(Object element) {
		Menu currentmenu = ((Menu) element);
		if (mainWindow.getOptionMode() == MainWindow.ALL_OPTIONS
				&& !currentmenu.isVisible()) {
			return mainWindow.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		}
		return null;
	}
}
