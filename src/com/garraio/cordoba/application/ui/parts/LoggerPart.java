
package com.garraio.cordoba.application.ui.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class LoggerPart {
	private Text logArea;
	private static LoggerPart loggerinstance;
	private static String LOGGER_HEADER = "Cordoba App Logger\n===================\n\n";

	@Inject
	public LoggerPart() {
		loggerinstance = this;
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		logArea = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL  | SWT.READ_ONLY);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		logArea.setLayoutData(gridData);
		logArea.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		logArea.setText(LOGGER_HEADER);
	}

	public static LoggerPart getInstance() {
		return loggerinstance;
	}

	public static void log(String message) {
		if (loggerinstance != null && !loggerinstance.logArea.isDisposed()) {
			Display.getDefault().asyncExec(() -> {
				loggerinstance.logArea.append(message + "\n\n");
			});
		}
	}

	public static void clearLog() {
		if (loggerinstance != null && !loggerinstance.logArea.isDisposed()) {
			Display.getDefault().asyncExec(() -> {
				loggerinstance.logArea.setText(LOGGER_HEADER);
			});
		}
	}

}