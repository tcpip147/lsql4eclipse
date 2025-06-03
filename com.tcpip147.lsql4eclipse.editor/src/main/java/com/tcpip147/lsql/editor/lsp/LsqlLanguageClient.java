package com.tcpip147.lsql.editor.lsp;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

public class LsqlLanguageClient implements LanguageClient {

	private LanguageServer server;

	@Override
	public void logMessage(MessageParams message) {
		System.out.println("Log: " + message.getMessage());
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			if (diagnostic.getSeverity() != null) {
				if (diagnostic.getSeverity() == DiagnosticSeverity.Error) {
					System.err.println(diagnostic.getMessage());
				}
			}
		}
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		System.out.println("Server Message: " + messageParams.getMessage());
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		System.out.println(requestParams);
		return null;
	}

	@Override
	public void telemetryEvent(Object object) {
		System.out.println("Telemetry Event: " + object);
	}

	public void connect(LanguageServer server) {
		this.server = server;
	}

}
