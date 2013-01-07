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

package ekconf.commands;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import ekconf.editors.EkconfEditorInput;
import ekconf.editors.EkconfEditorPart;
import ekconf.kconfig.Configuration;
import ekconf.kconfig.ConfigurationFactory;
import ekconf.kconfig.KconfigException;
import ekconf.ui.MainWindow;

/**
 * 
 * @author Tiana Rakotovao
 *
 */
public class RunHandler extends AbstractHandler {

	private String folderPath = "";
	private Configuration configuration;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page;

		try {
			ISelection select = null;
			select = window.getSelectionService().getSelection(
					"org.eclipse.jdt.ui.PackageExplorer");
			if (select == null | !(select instanceof IStructuredSelection)) {
				throw new Exception(EkconfEditorPart.HELP_RUN);
			}
			IStructuredSelection selection = (IStructuredSelection) select;
			Object selected = selection.getFirstElement();
			if (selected instanceof IFile) {
				throw new Exception(selected.toString() + "is a file. "
						+ EkconfEditorPart.HELP_RUN);
			} else if (selected instanceof IFolder) {
				folderPath = ((IFolder) selected).getLocation().toOSString();
			} else {
				// for an IProject
				String projectName = selected.toString();
				if (projectName.contains("\n")) {
					projectName = projectName.split("\n", 2)[0];
				} else if (projectName.contains(" ")) {
					projectName = projectName.split(" ", 2)[0];
				}
				IProject project = null;
				project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
				if (project == null) {
					throw new Exception(EkconfEditorPart.HELP_RUN);
				}
				folderPath = project.getLocation().toOSString();
			}
			if (EkconfEditorPart.hasConfiguration()
					&& MainWindow.getActiveWindow() != null) {
				EkconfEditorPart part = MainWindow.getActiveWindow()
						.getEkconfEditorPart();
				page = part.getSite().getPage();
				page.activate(part);
				if (MessageDialog.openConfirm(window.getShell(),
						"Ekconf Plug-in",
						"An instance of Ekconf Editor is already running. You should"
								+ " close it before opening a new one. "
								+ "Do you want to close it now ?")) {
					if (!page.closeEditor(part, true)) {
						return null;
					} else {
						try {
							Thread.sleep(300);
						} catch (Exception e){
						}
					}
				} else {
					return null;
				}
			}
			initConfiguration();
			page = window.getActivePage();
			page.openEditor(EkconfEditorInput.newInstance(configuration),
					EkconfEditorPart.ID, true, IWorkbenchPage.MATCH_ID);
			folderPath = null;
		} catch (Exception e) {
			MessageDialog.openInformation(window.getShell(), "Ekconf Plug-in",
					e.toString());
		}
		return null;
	}

	private void initConfiguration() throws KconfigException {
		try {
			new ProgressMonitorDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell()).run(true, false,
					new IRunnableWithProgress() {

						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							monitor.beginTask("Reading kconfig files.",
									IProgressMonitor.UNKNOWN);
							try {
								// folderPath should not contain any space
								if (folderPath.contains(" ")) {
									throw new KconfigException(
											"The path of the linux folder should not contain any space. Yours is "
													+ folderPath);
								}
								Configuration conf = ConfigurationFactory
										.getConfiguration(folderPath);
								configuration = conf;
							} catch (KconfigException e) {
								throw new InvocationTargetException(e, e
										.getMessage());
							} finally {
								monitor.done();
							}
						}
					});
		} catch (Exception e) {
			throw new KconfigException(e.getMessage());
		}
	}
}
