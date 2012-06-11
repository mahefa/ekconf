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

package ekconf.ui;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ekconf.kconfig.Menu;
import ekconf.kconfig.PropertyType;
import ekconf.kconfig.Symbol;
import ekconf.kconfig.SymbolType;
import ekconf.kconfig.Tristate;

/**
 * A ColumnLabelProvider just for the Option column
 * @author Tiana Rakotovao
 */
public class OptionColumnLabelProvider extends ColumnLabelProvider {
	private MainWindow mainWindow;
	private ImageRegistry imageRegistry;
	private Display display;
	private boolean isSearchWindow;

	public OptionColumnLabelProvider(MainWindow mainWin, boolean isSearchWindow) {
		super();
		this.mainWindow = mainWin;
		this.display = mainWin.getDisplay();
		this.isSearchWindow = isSearchWindow;
		imageRegistry = MainWindow.getImageRegistry();
	}

	@Override
	public String getText(Object element) {
		Menu currentmenu = ((Menu) element);
		SymbolType type = null;
		Symbol sym = null;
		if (currentmenu.hasProperty()
				&& currentmenu.getPropertyType().equals(PropertyType.P_COMMENT)) {
			return "---" + currentmenu.getPrompt();
		}

		if (currentmenu.hasSymbol()) {
			sym = currentmenu.getSymbol();
			type = sym.getSymbolType();
			String name = currentmenu.toString();
			if (type.equals(SymbolType.S_STRING)) {
				return name + " : " + sym.getStringValue();
			} else if (type.equals(SymbolType.S_INT)
					|| type.equals(SymbolType.S_HEX)) {
				return "(" + sym.getStringValue() + ") " + name;
			} else if (!sym.hasValue() && currentmenu.isVisible()) {
				return name + " (NEW)";
			} 
			else if(isSearchWindow && !currentmenu.isVisible() && !currentmenu.hasPrompt()) {
				return name;
			}
		}
		return currentmenu.toString();
	}

	public Image getImage(Object element) {
		Menu current = (Menu) element;
		if (current.hasProperty()) {
			switch (current.getPropertyType()) {
			case P_MENU:
				if (current.hasSymbol())
					break;
				return null; 
			case P_COMMENT:
				return null; 
			}
		}
		if (!current.hasSymbol()) {
			return null;
		}
		Symbol sym = current.getSymbol();
		SymbolType type = sym.getSymbolType();
		switch (type) {
		case S_BOOLEAN:
		case S_TRISTATE:

			if (!sym.isChangeable()) {
				return null;
			}

			Tristate expr = sym.getTristateValue();
			switch (expr) {
			case yes:
				if (sym.isChoiceValue() && type.equals(SymbolType.S_BOOLEAN)) {
					return imageRegistry.get(MainWindow.CHOICE_YES);
				} else {
					return imageRegistry.get(MainWindow.SYMBOL_YES);
				}

			case mod:
				return imageRegistry.get(MainWindow.SYMBOL_MOD);

			case no: // no
				if (sym.isChoiceValue() && type.equals(SymbolType.S_BOOLEAN)) {
					return imageRegistry.get(MainWindow.CHOICE_NO);
				} else {
					return imageRegistry.get(MainWindow.SYMBOL_NO);
				}
			}
		case S_INT:
		case S_HEX:
		case S_STRING:
		default:
			return null;
		}
	}

	@Override
	public Color getForeground(Object element) {
		Menu currentmenu = ((Menu) element);
		
		if (!isSearchWindow
				&& mainWindow.getOptionMode() == MainWindow.ALL_OPTIONS
				&& !currentmenu.isVisible()) {
			return display.getSystemColor(SWT.COLOR_GRAY);
		}
		
		else if (isSearchWindow && !currentmenu.isVisible()) {
			return display.getSystemColor(SWT.COLOR_GRAY);
		}
		return null;
	}
}
