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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import ekconf.editors.EkconfEditorPart;
import ekconf.kconfig.Configuration;
import ekconf.kconfig.Menu;
import ekconf.kconfig.Symbol;
import ekconf.kconfig.SymbolType;
import ekconf.kconfig.Tristate;

/**
 * 
 * @author Tiana Rakotovao
 * 
 */
public class MainWindow {

	public static final String OPTION = "Option";
	public static final String NAME = "Name";
	public static final String NO = "N";
	public static final String MOD = "M";
	public static final String YES = "Y";
	public static final String VALUE = "Value";

	public static final String SYMBOL_YES = "symbol_yes";
	public static final String SYMBOL_NO = "symbol_no";
	public static final String SYMBOL_MOD = "symbol_mod";
	public static final String CHOICE_YES = "choice_yes";
	public static final String CHOICE_NO = "choice_no";

	private static MainWindow activeWindow = null;

	public static final int NORMAL_OPTIONS = 0;
	public static final int ALL_OPTIONS = 1;

	private Composite parent;
	private Configuration configuration = null;
	private EkconfEditorPart ekconfEditorPart;

	public EkconfEditorPart getEkconfEditorPart() {
		return ekconfEditorPart;
	}

	public void setEkconfEditorPart(EkconfEditorPart ekconfEditorPart) {
		this.ekconfEditorPart = ekconfEditorPart;
	}

	private Display display;

	private TreeViewer menuTree;

	private SashForm bigSash;
	private static StyledText helpText;
	private Tree tree;
	private StyleRange styleRange;
	private static ImageRegistry imageRegistry = null;

	private int optionMode;

	public Display getDisplay() {
		return display;
	}

	public void dispose() {
		SearchWindow search = SearchWindow.getSearchWindow();
		if (search != null) {
			SearchWindow.getSehll().close();
		}
		menuTree.getTree().dispose();
		helpText.dispose();
		imageRegistry.dispose();
		bigSash.dispose();
		parent.dispose();
		activeWindow = null;
		parent = null;
		configuration.dispose();
		configuration = null;
		ekconfEditorPart = null;
		imageRegistry = null;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public int getOptionMode() {
		return optionMode;
	}

	public void setOptionMode(int optionMode) {
		this.optionMode = optionMode;
	}

	public TreeViewer getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(TreeViewer menuTree) {
		this.menuTree = menuTree;
	}

	public static MainWindow getActiveWindow() {
		return activeWindow;
	}

	public static void setActiveWindow(MainWindow activeWindow) {
		MainWindow.activeWindow = activeWindow;
	}

	public Shell getShell() {
		return parent.getShell();
	}

	public MainWindow(Composite _parent, Configuration _config, int optionMode,
			EkconfEditorPart _editor) {

		parent = _parent;
		configuration = _config;
		ekconfEditorPart = _editor;

		activeWindow = this;

		display = Display.getDefault();

		switch (optionMode) {
		case NORMAL_OPTIONS:
		case ALL_OPTIONS:
			break;
		default:
			optionMode = NORMAL_OPTIONS;
			break;
		}
		this.setOptionMode(optionMode);

		// Images
		MainWindow.imageRegistry = new ImageRegistry();
		ImageDescriptor imdesc = null;
		imdesc = ImageDescriptor.createFromFile(getClass(),
				"icons/xpm_symbol_yes.xpm");
		imageRegistry.put(SYMBOL_YES, imdesc);
		imdesc = ImageDescriptor.createFromFile(getClass(),
				"icons/xpm_symbol_no.xpm");
		imageRegistry.put(SYMBOL_NO, imdesc);
		imdesc = ImageDescriptor.createFromFile(getClass(),
				"icons/xpm_symbol_mod.xpm");
		imageRegistry.put(SYMBOL_MOD, imdesc);
		imdesc = ImageDescriptor.createFromFile(getClass(),
				"icons/xpm_choice_yes.xpm");
		imageRegistry.put(CHOICE_YES, imdesc);
		imdesc = ImageDescriptor.createFromFile(getClass(),
				"icons/xpm_choice_no.xpm");
		imageRegistry.put(CHOICE_NO, imdesc);

		parent.setLayout(new FillLayout(SWT.VERTICAL));

		bigSash = new SashForm(_parent, SWT.VERTICAL | SWT.BORDER);

		menuTree = new TreeViewer(bigSash, SWT.FULL_SELECTION | SWT.BORDER);
		tree = menuTree.getTree();
		tree.setHeaderVisible(true);
		menuTree.setContentProvider(new MenuTreeContentProvider());

		helpText = new StyledText(bigSash, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		helpText.setEditable(false);
		styleRange = new StyleRange();
		styleRange.fontStyle = SWT.BOLD;
		styleRange.start = 0;
		helpText.setMargins(10, 5, 10, 5);

		bigSash.setWeights(new int[] { 60, 40 });

		// /// filter

		ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				Menu menu = ((Menu) element);
				if (MainWindow.this.getOptionMode() == ALL_OPTIONS
						&& (menu.hasPrompt() || menu.hasSymbol())) {
					return true;
				} else
					return menu.isVisible();
			}

			@Override
			public boolean isFilterProperty(Object element, String property) {
				return true;
			}
		};
		ViewerFilter filterTab[] = new ViewerFilter[1];
		filterTab[0] = filter;
		menuTree.setFilters(filterTab);

		// // option column
		TreeViewerColumn column = new TreeViewerColumn(menuTree, SWT.LEFT);
		TreeColumn treecol = column.getColumn();
		treecol.setText(OPTION);
		treecol.setWidth(350);
		treecol.setResizable(true);
		column.setLabelProvider(new OptionColumnLabelProvider(this, false));

		// // value column
		column = new TreeViewerColumn(menuTree, SWT.LEFT);
		treecol = column.getColumn();
		treecol.setText(VALUE);
		treecol.setWidth(175);
		treecol.setResizable(true);
		column.setLabelProvider(new EkconfColumnLabelProvider(this,
				ColumnIndex.VALUE_COL));
		// edition
		menuTree.setColumnProperties(new String[] { OPTION, NAME, NO, MOD, YES,
				VALUE });
		column.setEditingSupport(new EkconfEditingSupport(this));

		// // name column
		column = new TreeViewerColumn(menuTree, SWT.LEFT);
		treecol = column.getColumn();
		treecol.setText(NAME);
		treecol.setWidth(200);
		treecol.setResizable(true);
		column.setLabelProvider(new EkconfColumnLabelProvider(this,
				ColumnIndex.NAME_COL));

		// // no column
		column = new TreeViewerColumn(menuTree, SWT.CENTER);
		treecol = column.getColumn();
		treecol.setText(NO);
		treecol.setWidth(25);
		treecol.setResizable(false);
		column.setLabelProvider(new EkconfColumnLabelProvider(this,
				ColumnIndex.NO_COL));

		// // Mod column
		column = new TreeViewerColumn(menuTree, SWT.CENTER);
		treecol = column.getColumn();
		treecol.setText(MOD);
		treecol.setWidth(25);
		treecol.setResizable(false);
		column.setLabelProvider(new EkconfColumnLabelProvider(this,
				ColumnIndex.MOD_COL));

		// // Yes column
		column = new TreeViewerColumn(menuTree, SWT.CENTER);
		treecol = column.getColumn();
		treecol.setText(YES);
		treecol.setWidth(25);
		treecol.setResizable(false);
		column.setLabelProvider(new EkconfColumnLabelProvider(this,
				ColumnIndex.YES_COL));

		// // selection
		menuTree.addSelectionChangedListener(new ISelectionChangedListener() {
			private Menu oldSelectedMenu = null;

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Menu menu = (Menu) ((TreeSelection) event.getSelection())
						.getFirstElement();
				if (menu == null || menu.equals(oldSelectedMenu))
					return; // help is already up to date
				MainWindow.this.updateHelp();
				oldSelectedMenu = menu;
			}
		});

		// mouse listener
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				changeValue();
				updateHelp();
			}
		});

		// keyListener
		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == ' ') {
					changeValue();
					updateHelp();
				}
			}
		});

		// input
		menuTree.setInput(configuration.getRootMenu());
	}

	public void changeValue() {
		TreeSelection selection = (TreeSelection) menuTree.getSelection();
		Menu menu = (Menu) selection.getFirstElement();
		TreeItem treeItem = tree.getSelection()[0];
		if (menu == null) {
			return;
		}
		if (!menu.isVisible()) {
			return;
		}
		Symbol sym = menu.getSymbol();
		if (null == sym) {
			if (menu.hasChildren())
				treeItem.setExpanded(!treeItem.getExpanded());
			return;
		}
		SymbolType type = sym.getSymbolType();
		switch (type) {
		case S_BOOLEAN:
		case S_TRISTATE:
			Tristate oldExpr = sym.getTristateValue();
			Tristate newExpr = sym.toggleTristateValue();
			if (menu.hasChildren()) {
				if (oldExpr.equals(newExpr)) {
					treeItem.setExpanded(!treeItem.getExpanded());
				} else if (oldExpr.equals(Tristate.no)) {
					treeItem.setExpanded(true);
				}
			}
			if (!newExpr.equals(oldExpr)) {
				menuTree.refresh();
				menuTree.refresh();

				SearchWindow searchWindow = SearchWindow.getSearchWindow();
				if (searchWindow != null) {
					searchWindow.getSearchTable().refresh();
				}

				if (ekconfEditorPart != null)
					ekconfEditorPart.fireDirty();
			}
			break;
		case S_INT:
		case S_HEX:
		case S_STRING:
			menuTree.editElement(menu, ColumnIndex.VALUE_COL.ordinal());
			break;
		}
	}

	public static ImageRegistry getImageRegistry() {
		return imageRegistry;
	}

	public static void setImageRegistry(ImageRegistry imageRegistry) {
		MainWindow.imageRegistry = imageRegistry;
	}

	public void updateHelp() {
		TreeSelection selection = (TreeSelection) menuTree.getSelection();
		if (selection != null) {
			String help = "";
			Menu menu = (Menu) selection.getFirstElement();
			if (menu != null) {
				if (menu.hasPrompt()) {
					help = menu.getPrompt();
				} else if (menu.hasSymbol()
						&& this.getOptionMode() == ALL_OPTIONS) {
					help = menu.getSymbol().getName();
				}
				styleRange.length = help.length();
				helpText.setText(help);
				helpText.setStyleRange(styleRange);
				if (menu.hasSymbol())
					helpText.append("\n\n" + menu.getHelp());
			}
		}
	}
}
