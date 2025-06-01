package com.tcpip147.lsql.control;

import java.util.List;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.tcpip147.lsql.util.KeyStrokeAccelators;

public class LineTextField extends SourceViewer {

	private Document document;

	public LineTextField(Composite parent, SqlEditor editor) {
		super(parent, null, SWT.BORDER | SWT.SINGLE);
		getControl().setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		document = new Document("");
		setDocument(document);
		getTextWidget().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				editor.getEditorSite().getActionBars().clearGlobalActionHandlers();
			}

			@Override
			public void focusLost(FocusEvent e) {
				getTextWidget().setSelection(0, 0);
			}
		});
		addUndoManager();
	}

	private void addUndoManager() {
		TextViewerUndoManager undoManager = new TextViewerUndoManager(30);
		undoManager.connect(this);
		List<Integer> undoKeys = KeyStrokeAccelators.get("org.eclipse.ui.edit.undo");
		List<Integer> redoKeys = KeyStrokeAccelators.get("org.eclipse.ui.edit.redo");
		getTextWidget().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(KeyStrokeAccelators.match(e, undoKeys));
				if (KeyStrokeAccelators.match(e, undoKeys)) {
					undoManager.undo();
				} else if (KeyStrokeAccelators.match(e, redoKeys)) {
					undoManager.redo();
				}
				super.keyPressed(e);
			}
		});
	}
}