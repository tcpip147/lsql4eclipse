package com.tcpip147.lsql.editor;

import org.eclipse.ui.editors.text.TextEditor;

public class LsqlContext {

	private LsqlMultiPageEditor multiPageEditor;
	private DesignerPage designer;
	private TextEditor editor;

	public LsqlMultiPageEditor getMultiPageEditor() {
		return multiPageEditor;
	}

	public void setMultiPageEditor(LsqlMultiPageEditor multiPageEditor) {
		this.multiPageEditor = multiPageEditor;
	}

	public DesignerPage getDesigner() {
		return designer;
	}

	public void setDesigner(DesignerPage designer) {
		this.designer = designer;
	}

	public TextEditor getEditor() {
		return editor;
	}

	public void setEditor(TextEditor editor) {
		this.editor = editor;
	}
}
