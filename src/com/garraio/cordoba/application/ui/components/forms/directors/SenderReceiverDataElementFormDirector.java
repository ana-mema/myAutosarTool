package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.DATA_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.interfaces.SenderReceiverDataElementAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;

public class SenderReceiverDataElementFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		VariableDataPrototype dataElement = (VariableDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (dataElement == null) {
			return;
		}

		SenderReceiverDataElementAdapter senderReceiverDataElementAdapter = new SenderReceiverDataElementAdapter(
				dataElement);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, senderReceiverDataElementAdapter.getShortName())
				.addReferenceField(DATA_TYPE, AutosarElementRegistry.getImplementationDataTypes(),
						senderReceiverDataElementAdapter.getImplementationTypeRef())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		VariableDataPrototype dataElement = (VariableDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (dataElement == null) {
			return;
		}

		SenderReceiverDataElementAdapter senderReceiverDataElementAdapter = new SenderReceiverDataElementAdapter(
				dataElement);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		ImplementationDataType referencedImplementationType = builder.getFieldValue(DATA_TYPE)
				.map(value -> (ImplementationDataType) value).orElse(null);

		senderReceiverDataElementAdapter.setShortName(shortName);
		senderReceiverDataElementAdapter.setImplementationTypeRef(referencedImplementationType);
	}
}
