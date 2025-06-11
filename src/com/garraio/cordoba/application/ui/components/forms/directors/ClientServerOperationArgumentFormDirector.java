package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.ARGUMENT_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.DIRECTION;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import org.eclipse.emf.ecore.EEnumLiteral;

import com.garraio.cordoba.application.core.adapter.interfaces.ClientServerOperationArgumentAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;

public class ClientServerOperationArgumentFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ArgumentDataPrototype argument = (ArgumentDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (argument == null) {
			return;
		}

		ClientServerOperationArgumentAdapter argumentAdapter = new ClientServerOperationArgumentAdapter(argument);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, argumentAdapter.getShortName())
				.addReferenceField(ARGUMENT_TYPE, AutosarElementRegistry.getImplementationDataTypes(),
						argumentAdapter.getArgumentType())
				.addReferenceField(DIRECTION, argumentAdapter.getArgumentDirectionLiterals(),
						argumentAdapter.getArgumentDirection())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ArgumentDataPrototype argument = (ArgumentDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (argument == null) {
			return;
		}

		ClientServerOperationArgumentAdapter argumentAdapter = new ClientServerOperationArgumentAdapter(argument);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		AutosarDataType argumentType = builder.getFieldValue(ARGUMENT_TYPE).map(value -> (AutosarDataType) value)
				.orElse(null);
		EEnumLiteral argumentDirection = builder.getFieldValue(DIRECTION).map(value -> (EEnumLiteral) value)
				.orElse(null);

		argumentAdapter.setShortName(shortName);
		argumentAdapter.setArgumentType(argumentType);
		argumentAdapter.setArgumentDirection(argumentDirection);
	}

}
