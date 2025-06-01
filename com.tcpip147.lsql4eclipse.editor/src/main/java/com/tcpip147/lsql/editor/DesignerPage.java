package com.tcpip147.lsql.editor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;

import com.tcpip147.lsql.editor.control.LineTextField;
import com.tcpip147.lsql.editor.control.ListEx;
import com.tcpip147.lsql.editor.control.SqlEditor;
import com.tcpip147.lsql.editor.util.SWTControls;

public class DesignerPage extends SashForm {

	private final LsqlContext ctx;
	private SqlEditor editor;

	private LineTextField tfId;
	private LineTextField tfDescription;
	private ToolItem btnNew;
	private ToolItem btnDelete;
	private ToolItem btnEdit;
	private ToolItem btnDuplicate;
	private ToolItem btnMoveUp;
	private ToolItem btnMoveDown;
	private ListEx ltQueries;

	private LsqlFile file;

	public DesignerPage(Composite parent, LsqlContext ctx) {
		super(parent, SWT.HORIZONTAL);
		this.ctx = ctx;
		ctx.setDesigner(this);
		setLayout(new FillLayout());
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLeftPanel();
		createRightPanel();

		file = new LsqlFile(ctx.getMultiPageEditor().getEditorInput());

		setWeights(new int[] { 220, 80 });
	}

	private void createLeftPanel() {
		Composite panel = new Composite(this, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		LsqlMultiPageEditor multiPageEditor = ctx.getMultiPageEditor();
		try {
			editor = new SqlEditor(ctx);
			editor.init(multiPageEditor.newSite(editor), new MockInput());
			editor.createPartControl(panel);
			editor.afterCreation();
		} catch (PartInitException e) {
			// TODO
		}
	}

	private void createRightPanel() {
		Composite panel = new Composite(this, SWT.NONE);
		panel.setLayout(new GridLayout(2, false));

		tfId = SWTControls.createLineTextFieldWithLabel(panel, editor, "Query ID ");
		tfDescription = SWTControls.createLineTextFieldWithLabel(panel, editor, "Description ");

		ToolBar toolbar = new ToolBar(panel, SWT.FLAT | SWT.HORIZONTAL);
		GridData gridData = new GridData(SWT.FILL, SWT.NONE, true, false);
		gridData.horizontalSpan = 2;
		toolbar.setLayoutData(gridData);

		btnNew = SWTControls.createButtonWithIcon(toolbar, "plus");
		btnDelete = SWTControls.createButtonWithIcon(toolbar, "minus");
		btnEdit = SWTControls.createButtonWithIcon(toolbar, "pencil");
		btnDuplicate = SWTControls.createButtonWithIcon(toolbar, "copy");
		btnMoveUp = SWTControls.createButtonWithIcon(toolbar, "arrow-up");
		btnMoveDown = SWTControls.createButtonWithIcon(toolbar, "arrow-down");

		ltQueries = SWTControls.createListWithLabel(panel, "Query List ");
	}

	private class MockInput implements IStorageEditorInput {

		@Override
		public boolean exists() {
			return true;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public IPersistableElement getPersistable() {
			return null;
		}

		@Override
		public String getToolTipText() {
			return null;
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			return null;
		}

		@Override
		public IStorage getStorage() throws CoreException {
			return new StringStorage();
		}
	}

	private class StringStorage implements IStorage {

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			return null;
		}

		@Override
		public InputStream getContents() throws CoreException {
			return new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
		}

		@Override
		public IPath getFullPath() {
			return null;
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public boolean isReadOnly() {
			return false;
		}
	}
}
