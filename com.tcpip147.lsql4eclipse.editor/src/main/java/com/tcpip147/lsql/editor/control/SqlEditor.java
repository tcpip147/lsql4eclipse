package com.tcpip147.lsql.editor.control;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.MultiPageEditorSite;

import com.tcpip147.lsql.editor.LsqlContext;
import com.tcpip147.lsql.editor.LsqlMultiPageEditorActionBarContributor;

public class SqlEditor extends TextEditor {

	private LsqlContext ctx;
	private SourceViewer sourceViewer;

	public SqlEditor(LsqlContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(new EditorSite((MultiPageEditorSite) site), input);
	}

	public void afterCreation() {
		SqlEditor $this = this;
		sourceViewer = (SourceViewer) getSourceViewer();
		sourceViewer.getTextWidget().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				LsqlMultiPageEditorActionBarContributor contributor = (LsqlMultiPageEditorActionBarContributor) getEditorSite()
						.getActionBarContributor();
				contributor.setActivePage($this);
				// a scope for activating toggle block selection
				IContextService contextService = (IContextService) ctx.getMultiPageEditor().getSite()
						.getService(IContextService.class);
				contextService.activateContext("org.eclipse.ui.textEditorScope");
			}

			@Override
			public void focusLost(FocusEvent e) {
				sourceViewer.getTextWidget().setSelection(0, 0);
			}
		});
	}

	@Override
	protected IOverviewRuler createOverviewRuler(ISharedTextColors sharedColors) {
		return new EmptyOverviewRuler();
	}

	private class EditorSite extends MultiPageEditorSite {

		public EditorSite(MultiPageEditorSite oldSite) {
			super(oldSite.getMultiPageEditor(), oldSite.getEditor());
		}

		@Override
		public IEditorActionBarContributor getActionBarContributor() {
			return new LsqlMultiPageEditorActionBarContributor(this);
		}
	}

	private class EmptyOverviewRuler implements IOverviewRuler {
		private Composite composite = null;
		private Composite headerComposite = null;

		@Override
		public void setModel(IAnnotationModel model) {
		}

		@Override
		public IAnnotationModel getModel() {
			return null;
		}

		@Override
		public void update() {
		}

		@Override
		public Control createControl(Composite parent, ITextViewer viewer) {
			composite = new Composite(parent, SWT.NONE);
			headerComposite = new Composite(parent, SWT.NONE);
			return composite;
		}

		@Override
		public Control getControl() {
			return composite;
		}

		@Override
		public int getLineOfLastMouseButtonActivity() {
			return 0;
		}

		@Override
		public int toDocumentLineNumber(int number) {
			return 0;
		}

		@Override
		public int getWidth() {
			return 0;
		}

		@Override
		public void addAnnotationType(Object type) {
		}

		@Override
		public void addHeaderAnnotationType(Object type) {
		}

		@Override
		public int getAnnotationHeight() {
			return 0;
		}

		@Override
		public Control getHeaderControl() {
			return headerComposite;
		}

		@Override
		public boolean hasAnnotation(int i) {
			return false;
		}

		@Override
		public void removeAnnotationType(Object type) {
		}

		@Override
		public void removeHeaderAnnotationType(Object type) {
		}

		@Override
		public void setAnnotationTypeColor(Object type, Color color) {
		}

		@Override
		public void setAnnotationTypeLayer(Object type, int layer) {
		}
	}
}
