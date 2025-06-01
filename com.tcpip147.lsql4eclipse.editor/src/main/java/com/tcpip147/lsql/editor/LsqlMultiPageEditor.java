package com.tcpip147.lsql.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.tcpip147.lsql.control.SqlEditor;

public class LsqlMultiPageEditor extends MultiPageEditorPart {

	private final LsqlContext ctx;
	private final IResourceChangeListener resourceListener;
	private DesignerPage designer;
	private TextEditor editor;
	private boolean isDirty;

	public LsqlMultiPageEditor() {
		ctx = new LsqlContext();
		ctx.setMultiPageEditor(this);
		resourceListener = new LsqlResourceChangeListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
	}

	@Override
	protected void createPages() {
		createDesignPage();
		createTextPage();
		addPageChangedListener(new IPageChangedListener() {
			@Override
			public void pageChanged(PageChangedEvent e) {
				if (getActivePage() == 0) {

				}
			}
		});
	}

	private void createDesignPage() {
		designer = new DesignerPage(getContainer(), ctx);
		addPage(0, designer);
		setPageText(0, "Design");
	}

	private void createTextPage() {
		try {
			editor = new TextEditor();
			ctx.setEditor(editor);
			IEditorInput input = getEditorInput();
			addPage(1, editor, input);
			setPageText(1, "Source");
		} catch (PartInitException e) {
			// TODO
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		saveDesignPage();
		getEditor(1).doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		saveDesignPage();
		IEditorPart editorPart = getEditor(1);
		editor.doSaveAs();
		setInput(editorPart.getEditorInput());
	}

	private void saveDesignPage() {
		if (getActivePage() == 0) {
			//
		}
	}

	@Override
	public boolean isDirty() {
		if (getActivePage() == 0) {
			return super.isDirty() || isDirty;
		} else {
			return super.isDirty();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
		super.dispose();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IFileEditorInput fileInput) {
			setPartName(fileInput.getName());
		} else if (input instanceof FileStoreEditorInput fileInput) {
			setPartName(fileInput.getName());
		}
		super.init(site, input);
	}

	private void superSetInput(FileEditorInput input) {
		if (getEditorInput() != null) {
			IFile file = ((FileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().removeResourceChangeListener(resourceListener);
		}
		setInput(input);
		if (getEditorInput() != null) {
			IFile file = ((FileEditorInput) getEditorInput()).getFile();
			file.getWorkspace().addResourceChangeListener(resourceListener);
			setPartName(file.getName());
		}
	}

	private void closeEditor(boolean save) {
		IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
		for (int i = 0; i < pages.length; i++) {
			IWorkbenchPage page = pages[i];
			page.closeEditor(page.findEditor(editor.getEditorInput()), save);
		}
	}

	public IEditorSite newSite(SqlEditor sqlEditor) {
		return super.createSite(sqlEditor);
	}

	private class LsqlResourceChangeListener implements IResourceChangeListener, IResourceDeltaVisitor {

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IEditorInput input = getEditorInput();
			IResource resource = null;
			if (input instanceof FileEditorInput) {
				resource = ((FileEditorInput) input).getFile();
			} else if (input instanceof FileStoreEditorInput) {
				resource = ((FileStoreEditorInput) input).getAdapter(IResource.class);
			}
			if (delta == null || !delta.getResource().equals(resource)) {
				return true;
			}
			if (delta.getKind() == IResourceDelta.REMOVED) {
				Display display = getSite().getShell().getDisplay();
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) == 0) {
					display.asyncExec(() -> {
						if (!isDirty()) {
							closeEditor(false);
						}
					});
				} else {
					IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getMovedToPath());
					display.asyncExec(() -> {
						superSetInput(new FileEditorInput(newFile));
					});
				}
			}
			return false;
		}

		@Override
		public void resourceChanged(IResourceChangeEvent evt) {
			try {
				IResourceDelta delta = evt.getDelta();
				if (delta != null) {
					delta.accept(this);
				}
			} catch (CoreException e) {
				// TODO
			}
		}
	}
}
