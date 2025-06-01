package com.tcpip147.lsql.editor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

public class LsqlFile {

	private List<LsqlQuery> queryList = new LinkedList<>();

	public void parse(IEditorInput input) {
		List<Token> tokens = tokenize(input);
	}

	private List<Token> tokenize(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			try (InputStream is = file.getContents()) {
				String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				List<Token> tokens = new ArrayList<>();
				
			} catch (CoreException | IOException e) {
				// TODO
			}
		}
		return null;
	}

	private class Token {
		private static int PROPERTY = 1;
		private static int STRING = 2;
		private static int SPACE = 3;
		private static int LINE_FEED = 4;

		private int id;
		private String value;
	}
}
