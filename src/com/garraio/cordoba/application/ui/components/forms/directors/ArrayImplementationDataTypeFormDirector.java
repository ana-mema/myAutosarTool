package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.ARRAY_SIZE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.ELEMENT_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.IS_FIXED_SIZE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.garraio.cordoba.application.core.adapter.datatypes.ArrayImplementationDataTypeAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;

public class ArrayImplementationDataTypeFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ImplementationDataType implType = (ImplementationDataType) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implType == null) {
			return;
		}

		ArrayImplementationDataTypeAdapter arrayAdapter = new ArrayImplementationDataTypeAdapter(implType);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, arrayAdapter.getShortName())
				.addTextField(ARRAY_SIZE, arrayAdapter.getArraySize())
				.addReferenceField(ELEMENT_TYPE, getCompatibleImplementationTypes(),
						arrayAdapter.getImplementationType())
				.addBooleanField(IS_FIXED_SIZE, arrayAdapter.isFixedSize()).refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ImplementationDataType implType = (ImplementationDataType) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implType == null) {
			return;
		}

		ArrayImplementationDataTypeAdapter arrayAdapter = new ArrayImplementationDataTypeAdapter(implType);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		String arraySize = builder.getFieldValue(ARRAY_SIZE).map(value -> (String) value).orElse("");
		boolean isFixedSize = builder.getFieldValue(IS_FIXED_SIZE).map(value -> (Boolean) value).orElse(true);
		ImplementationDataType referencedImplementationType = builder.getFieldValue(ELEMENT_TYPE)
				.map(value -> (ImplementationDataType) value).orElse(null);

		arrayAdapter.setShortName(shortName);
		arrayAdapter.setArraySize(arraySize);
		arrayAdapter.setFixedSize(isFixedSize);
		arrayAdapter.setImplementationType(referencedImplementationType);
	}

	private List<EObject> getCompatibleImplementationTypes() {
		List<EObject> options = new ArrayList<>();
		for (ImplementationDataType type : AutosarElementRegistry.getImplementationDataTypes()) {
			if (type.getCategory().equals("VALUE") || type.getCategory().equals("STRUCTURE")) {
				options.add(type);
			}
		}
		return options;
	}

}
