package com.tcpip147.lsql.editor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

public class LsqlFile {
	
	private List<LsqlQuery> queryList = new LinkedList<>();
	
	

	public String getContent(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			try (InputStream is = file.getContents()) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			} catch (CoreException | IOException e) {
				// TODO
			}
		}
		return null;
	}
}
