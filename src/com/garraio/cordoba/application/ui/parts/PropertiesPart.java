
package com.garraio.cordoba.application.ui.parts;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.garraio.cordoba.application.ui.components.forms.FormManager;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

public class PropertiesPart {
	private Composite parent;
	private FormManager formManager;

	@Inject
	private MPart part;

	@PostConstruct
	public void postConstruct(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));
		formManager = new FormManager(parent, this);
	}

	@Inject
	@Optional
	public void onSelectionChanged(@Named(IServiceConstants.ACTIVE_SELECTION) TreeElement selectedElement) {
		if (parent == null || parent.isDisposed() || selectedElement == null) {
			return;
		}

		formManager.buildForm(selectedElement);
	}

	public void markDirty() {
		part.setDirty(true);
	}

	public boolean isDirty() {
		return part.isDirty();
	}

	public void resetDirty() {
		part.setDirty(false);
	}
}