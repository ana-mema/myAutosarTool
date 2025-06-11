package com.garraio.cordoba.application.ui.components.forms.fields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class BooleanField extends FormField<Boolean> {
	private Button checkbox;

	public BooleanField(String labelText, Boolean value) {
		super(labelText, value);
	}

	@Override
	protected Control createInputControl(Composite parent) {
		// Create checkbox button
		checkbox = new Button(parent, SWT.CHECK);

		checkbox.setSelection(this.value);

		return checkbox;
	}

	@Override
	public Boolean getValue() {
		if (checkbox != null && !checkbox.isDisposed()) {
			return checkbox.getSelection();
		}

		return null;
	}

	public void addChangeListener(Runnable callback) {
		if (checkbox != null && !checkbox.isDisposed()) {
			checkbox.addListener(SWT.Selection, event -> callback.run());
		}
	}

}
