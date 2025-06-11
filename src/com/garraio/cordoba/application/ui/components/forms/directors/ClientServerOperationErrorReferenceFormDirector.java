package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.POSSIBLE_ERROR_REF;

import java.util.List;

import com.garraio.cordoba.application.core.adapter.interfaces.ClientServerOperationAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ClientServerInterface;
import autosar40.swcomponent.portinterface.ClientServerOperation;

public class ClientServerOperationErrorReferenceFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		TreeElement parent = element.getParent();

		if (parent == null) {
			return;
		}

		ClientServerOperation csOperation = (ClientServerOperation) AutosarElementRegistry
				.getElementById(parent.getAutosarElementId());

		if (csOperation == null) {
			return;
		}

		ClientServerOperationAdapter csOperationAdapter = new ClientServerOperationAdapter(csOperation);

		builder.clearForm().addFormTitle(element.getType().getDisplayName()).addReferenceField(POSSIBLE_ERROR_REF,
				getPossibleErrors(csOperationAdapter), csOperationAdapter.getPossibleErrorReference()).refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		TreeElement parent = element.getParent();

		if (parent == null) {
			return;
		}

		ClientServerOperation csOperation = (ClientServerOperation) AutosarElementRegistry
				.getElementById(parent.getAutosarElementId());

		if (csOperation == null) {
			return;
		}

		ClientServerOperationAdapter csOperationAdapter = new ClientServerOperationAdapter(csOperation);

		ApplicationError selectedError = builder.getFieldValue(POSSIBLE_ERROR_REF)
				.map(value -> (ApplicationError) value).orElse(null);

		csOperationAdapter.setPossibleErrorReference(selectedError);

	}

	private List<ApplicationError> getPossibleErrors(ClientServerOperationAdapter csOperationAdapter) {
		ClientServerInterface csInterface = csOperationAdapter.getParentInterface();

		if (csInterface == null) {
			return List.of();
		}

		return csInterface.getPossibleErrors();
	}

}
