package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable;

public class CommonFormDirector implements IFormDirector {
	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		Identifiable autosarElement = (Identifiable) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (autosarElement == null) {
			return;
		}

		builder.clearForm()
		       .addFormTitle(element.getType().getDisplayName())
			   .addTextField(SHORT_NAME, autosarElement.getShortName())
			   .refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		Identifiable autosarElement = (Identifiable) AutosarElementRegistry.getElementById(element.getAutosarElementId());

		if (autosarElement == null) {
			return;
		}

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		autosarElement.setShortName(shortName);
	}

}
