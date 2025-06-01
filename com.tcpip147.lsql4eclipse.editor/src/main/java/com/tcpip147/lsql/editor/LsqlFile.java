package com.tcpip147.lsql.editor;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import com.tcpip147.lsql.editor.grammar.LsqlListener;
import com.tcpip147.lsql.editor.grammar.gen.LsqlLexer;
import com.tcpip147.lsql.editor.grammar.gen.LsqlParser;

public class LsqlFile {

	public LsqlFile(IEditorInput input) {
		parse(input);
	}

	public void parse(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			try (InputStream is = file.getContents()) {
				CharStream cs = CharStreams.fromStream(is);
				LsqlLexer lexer = new LsqlLexer(cs);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				LsqlParser parser = new LsqlParser(tokens);
				ParseTree tree = parser.statements();
				LsqlListener listener = new LsqlListener();
				ParseTreeWalker walker = new ParseTreeWalker();
				walker.walk(listener, tree);
				for (LsqlQuery query : listener.getQueryList()) {
					System.out.println("Query: " + query.id);
				}
			} catch (CoreException | IOException e) {
				// TODO
			}
		}
	}
}
