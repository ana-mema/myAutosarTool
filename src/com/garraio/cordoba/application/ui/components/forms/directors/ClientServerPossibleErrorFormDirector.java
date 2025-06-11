package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.ERROR_CODE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.interfaces.ClientServerPossibleErrorAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.portinterface.ApplicationError;

public class ClientServerPossibleErrorFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ApplicationError error = (ApplicationError) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (error == null) {
			return;
		}

		ClientServerPossibleErrorAdapter errorAdapter = new ClientServerPossibleErrorAdapter(error);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, errorAdapter.getShortName())
				.addTextField(ERROR_CODE, errorAdapter.getErrorCode()).refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ApplicationError error = (ApplicationError) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (error == null) {
			return;
		}

		ClientServerPossibleErrorAdapter errorAdapter = new ClientServerPossibleErrorAdapter(error);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		String errorCode = builder.getFieldValue(ERROR_CODE).map(value -> (String) value).orElse("");

		errorAdapter.setShortName(shortName);
		errorAdapter.setErrorCode(errorCode);
	}

}
