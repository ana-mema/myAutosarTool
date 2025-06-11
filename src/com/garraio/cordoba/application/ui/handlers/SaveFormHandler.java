
package com.garraio.cordoba.application.ui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import com.garraio.cordoba.application.ui.components.forms.FormManager;

public class SaveFormHandler {
	@Execute
	public void execute() {
		FormManager formManager = FormManager.getInstance();
		if (formManager != null) {
			formManager.saveForm();
		}
	}

	@CanExecute
	public boolean canExecute() {
		FormManager formManager = FormManager.getInstance();
		return formManager != null && formManager.isDirtyForm();
	}

}