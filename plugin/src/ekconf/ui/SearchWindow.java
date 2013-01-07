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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ekconf.kconfig.Configuration;
import ekconf.kconfig.Menu;

/**
 * 
 * @author Tiana Rakotovao
 *
 */

public class SearchWindow {

	static private TreeViewer menuTree;
	static private TableViewer searchTable;
	private Table table;
	static private Shell shell = null;
	private Display display;
	private Text text;
	private Button button;
	private Configuration config;

	static private SearchWindow searchWindow;
	static final private String FIND = "Find: ";
	static final private String SEARCH = "Search";
	static final private String TITLE = "Search Config";

	private SashForm bigSash;
	private StyleRange styleRange;
	private StyledText helpText;

	private Deque<Menu> deque;
	private TreePath path;

	private MainWindow mainWindow;

	static public SearchWindow createSearchWindow(Display ds) {
		if (SearchWindow.searchWindow == null) {
			SearchWindow.searchWindow = new SearchWindow(ds);
		}
		return searchWindow;
	}
	
	static public SearchWindow getSearchWindow() {
		return searchWindow;
	}

	private SearchWindow(Display ds) {
		searchWindow = this;
		display = ds;
		mainWindow = MainWindow.getActiveWindow();
		menuTree = mainWindow.getMenuTree();
		config = mainWindow.getConfiguration();
		createContents();
	}

	private void createContents() {
		Shell parent = mainWindow.getShell();
		if (parent == null)
			shell = new Shell(display, SWT.SHELL_TRIM);
		else
			shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setSize(700, 400);
		shell.setText(TITLE);
		
		shell.addDisposeListener(new DisposeListener(){
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				SearchWindow.searchWindow = null;
			}
		});

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);

		Label label = new Label(shell, SWT.NONE);
		label.setText(FIND);

		text = new Text(shell, SWT.NONE | SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (config == null)
					return;
				if (e.character == SWT.CR) {
					helpText.setText("");
					SearchWindow.this.setSearchResult(config.findMenu(text
							.getText()));
				}
			}
		});

		button = new Button(shell, SWT.NONE);
		button.setText(SEARCH);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (config == null)
					return;
				helpText.setText("");
				SearchWindow.this.setSearchResult(config.findMenu(text
						.getText()));
			}
		});

		SashForm bigSash = new SashForm(shell, SWT.VERTICAL | SWT.BORDER);
		bigSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		searchTable = new TableViewer(bigSash, SWT.BORDER | SWT.FULL_SELECTION);
		table = searchTable.getTable();
		searchTable.getTable().setHeaderVisible(true);
		searchTable.setContentProvider(new SearchContentProvider());

		TableViewerColumn column = new TableViewerColumn(searchTable, SWT.LEFT);
		TableColumn treecol = column.getColumn();
		treecol.setText(MainWindow.OPTION);
		treecol.setResizable(true);
		treecol.setWidth(650);
		column.setLabelProvider(new OptionColumnLabelProvider(mainWindow, true));

		
		ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				Menu menu = ((Menu) element);
				if ((menu.hasPrompt() || menu.hasSymbol())) {
					return true;
				}
				return false;
			}

			@Override
			public boolean isFilterProperty(Object element, String property) {
				return true;
			}
		};
		ViewerFilter filterTab[] = new ViewerFilter[1];
		filterTab[0] = filter;
		searchTable.setFilters(filterTab);

		deque = new ArrayDeque<Menu>();
		searchTable
				.addSelectionChangedListener(new ISelectionChangedListener() {
					private Menu oldSelectedMenu = null;

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Menu menu = (Menu) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						if (menu == null || menu.equals(oldSelectedMenu))
							return; // help is already up to date
						oldSelectedMenu = menu;
						SearchWindow.this.updateHelp();
						if (menuTree == null)
							return;
						while (true) {
							deque.push(menu);
							if (menu.equals(config.getRootMenu()))
								break;
							menu = menu.getParent();
						}
						if (deque.isEmpty()) {
							menuTree.setSelection(null);
							return;
						}
						path = new TreePath(deque.toArray());
						menuTree.setSelection(new TreeSelection(path));
						deque.removeAll(deque);
					}
				});

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				SearchWindow.this.mainWindow.changeValue();
				SearchWindow.this.mainWindow.updateHelp();
				SearchWindow.searchTable.refresh();
				if (menuTree != null)
					menuTree.refresh(true);
			}

		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == ' ') {
					SearchWindow.this.mainWindow.changeValue();
					SearchWindow.this.mainWindow.updateHelp();
					SearchWindow.searchTable.refresh();
					if (menuTree != null)
						menuTree.refresh(true);
				}
			}
		});

		helpText = new StyledText(bigSash, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		helpText.setEditable(false);
		styleRange = new StyleRange();
		styleRange.fontStyle = SWT.BOLD;
		styleRange.start = 0;
		helpText.setMargins(10, 5, 10, 5);

	}

	public void updateHelp() {
		StructuredSelection selection = (StructuredSelection) searchTable
				.getSelection();
		String help = "";
		if (selection != null) {
			Menu menu = (Menu) selection.getFirstElement();
			if (menu != null) {
				if (menu.hasPrompt())
					help = menu.getPrompt();
				else if (menu.hasSymbol())
					help = menu.getSymbol().getName();
				styleRange.length = help.length();
				helpText.setText(help);
				helpText.setStyleRange(styleRange);
				if (menu.hasSymbol())
					helpText.append("\n\n" + menu.getHelp());
			}
		}
	}

	public void setMenuTree(TreeViewer tree) {
		menuTree = tree;
	}

	public void setSearchResult(List<Menu> res) {
		searchTable.setInput(res);
	}

	public boolean hasShell() {
		return (shell == null);
	}

	public void setFocus() {
		shell.setFocus();
	}

	public void open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		helpText.dispose();
		searchTable.getTable().dispose();
		text.dispose();
		button.dispose();
		bigSash.dispose();
		shell.dispose();
		searchWindow = null;
		shell = null;
		searchTable = null;
	}

	static public Shell getSehll() {
		return shell;
	}

	public TableViewer getSearchTable() {
		return searchTable;
	}
}
