package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.ELEMENT_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.garraio.cordoba.application.core.adapter.datatypes.StructImplementationDataTypeElementAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataTypeElement;

public class StructElementFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ImplementationDataTypeElement implTypeElmnt = (ImplementationDataTypeElement) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implTypeElmnt == null) {
			return;
		}

		StructImplementationDataTypeElementAdapter structAdapter = new StructImplementationDataTypeElementAdapter(
				implTypeElmnt);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, structAdapter.getShortName()).addReferenceField(ELEMENT_TYPE,
						getCompatibleImplementationTypes(), structAdapter.getImplementationType())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ImplementationDataTypeElement implTypeElmnt = (ImplementationDataTypeElement) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (implTypeElmnt == null) {
			return;
		}

		StructImplementationDataTypeElementAdapter structAdapter = new StructImplementationDataTypeElementAdapter(
				implTypeElmnt);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		ImplementationDataType referencedImplementationType = builder.getFieldValue(ELEMENT_TYPE)
				.map(value -> (ImplementationDataType) value).orElse(null);

		implTypeElmnt.setShortName(shortName);
		structAdapter.setImplementationType(referencedImplementationType);
	}

	private List<EObject> getCompatibleImplementationTypes() {
		List<EObject> options = new ArrayList<>();
		for (ImplementationDataType type : AutosarElementRegistry.getImplementationDataTypes()) {
			if (type.getCategory().equals("VALUE") || type.getCategory().equals("ARRAY")) {
				options.add(type);
			}
		}
		return options;
	}
}
