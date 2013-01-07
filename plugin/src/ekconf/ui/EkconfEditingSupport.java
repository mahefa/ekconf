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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import ekconf.editors.EkconfEditorPart;
import ekconf.kconfig.Menu;
import ekconf.kconfig.Symbol;
import ekconf.kconfig.SymbolType;

/**
 * @author Tiana Rakotovao
 */
public class EkconfEditingSupport extends EditingSupport {

	private TreeViewer viewer;
	private CellEditor editor;
	private EkconfEditorPart editorPart;
	private MainWindow window;

	public EkconfEditingSupport(MainWindow mainW) {
		super( mainW.getMenuTree());
		viewer = mainW.getMenuTree();
		this.editor = new TextCellEditor((Composite) viewer.getControl());
		this.editorPart = mainW.getEkconfEditorPart();
		this.window = MainWindow.getActiveWindow();
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		Menu menu = (Menu) element;
		if (menu.hasSymbol() && menu.getSymbol().isChangeable()) {
			SymbolType type = menu.getSymbol().getSymbolType();
			switch (type) {
			case S_INT:
			case S_HEX:
			case S_STRING:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	protected Object getValue(Object element) {
		Menu menu = (Menu) element;
		return menu.getSymbol().getStringValue();
	}

	@Override
	protected void setValue(Object element, Object value) {
		Menu menu = (Menu) element;
		Symbol sym = menu.getSymbol();
		if (value instanceof String) {
			if (sym.setStringValue((String)value) ){
				viewer.update(menu, new String[]{MainWindow.OPTION,MainWindow.VALUE});
				if(editorPart != null )
					editorPart.fireDirty();
				window.getMenuTree().refresh(menu);
				window.updateHelp();
			}
		}
	}

}
