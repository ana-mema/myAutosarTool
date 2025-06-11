package com.garraio.cordoba.application.ui.components.forms.fields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class TextField extends FormField<String> {
	private Text textControl;

	public TextField(String label, String value) {
		super(label, value);
	}

	@Override
	protected Control createInputControl(Composite parent) {
		// Create text field with border
		textControl = new Text(parent, SWT.BORDER);

		// Set initial value
		if (value != null) {
			textControl.setText(value);
		}

		return textControl;
	}

	@Override
	public String getValue() {
		return textControl.getText();
	}

	public void addChangeListener(Runnable callback) {
		if (fieldControl != null && !fieldControl.isDisposed()) {
			fieldControl.addListener(SWT.Modify, event -> callback.run());
		}
	}
}
