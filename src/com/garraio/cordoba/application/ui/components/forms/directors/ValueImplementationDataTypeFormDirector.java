package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.BASE_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import com.garraio.cordoba.application.core.adapter.datatypes.ValueImplementationDataTypeAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;

public class ValueImplementationDataTypeFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ImplementationDataType implType = (ImplementationDataType) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implType == null) {
			return;
		}

		ValueImplementationDataTypeAdapter valueAdapter = new ValueImplementationDataTypeAdapter(implType);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, valueAdapter.getShortName())
				.addReferenceField(BASE_TYPE, AutosarElementRegistry.getBaseTypes(), valueAdapter.getBaseType())
				.refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ImplementationDataType implType = (ImplementationDataType) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implType == null) {
			return;
		}

		ValueImplementationDataTypeAdapter valueAdapter = new ValueImplementationDataTypeAdapter(implType);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		SwBaseType referencedBaseType = builder.getFieldValue(BASE_TYPE).map(value -> (SwBaseType) value).orElse(null);

		valueAdapter.setShortName(shortName);
		valueAdapter.setBaseType(referencedBaseType);
	}
}
