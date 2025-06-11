package com.garraio.cordoba.application.ui.components.forms.directors;

import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.CALIBRATION_ACCESS;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.PARAMETER_TYPE;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.POLICY;
import static com.garraio.cordoba.application.ui.components.forms.FormFieldLabelConstants.SHORT_NAME;

import org.eclipse.emf.ecore.EEnumLiteral;

import com.garraio.cordoba.application.core.adapter.interfaces.ParameterPrototypeAdapter;
import com.garraio.cordoba.application.core.registry.AutosarElementRegistry;
import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;

public class ParameterPrototypeFormDirector implements IFormDirector {

	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		ParameterDataPrototype prototype = (ParameterDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (prototype == null) {
			return;
		}

		ParameterPrototypeAdapter prototypeAdapter = new ParameterPrototypeAdapter(prototype);

		builder.clearForm().addFormTitle(element.getType().getDisplayName())
				.addTextField(SHORT_NAME, prototypeAdapter.getShortName())
				.addReferenceField(CALIBRATION_ACCESS, prototypeAdapter.getCalibrationAccessLiterals(),
						prototypeAdapter.getCalibrationAccess())
				.addReferenceField(POLICY, prototypeAdapter.getPolicyLiterals(), prototypeAdapter.getPolicy())
				.addReferenceField(PARAMETER_TYPE, AutosarElementRegistry.getImplementationDataTypes(),
						prototypeAdapter.getParameterType())
				.refreshLayout();

	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		ParameterDataPrototype prototype = (ParameterDataPrototype) AutosarElementRegistry
				.getElementById(element.getAutosarElementId());

		if (prototype == null) {
			return;
		}

		ParameterPrototypeAdapter prototypeAdapter = new ParameterPrototypeAdapter(prototype);

		String shortName = builder.getFieldValue(SHORT_NAME).map(value -> (String) value).orElse("");
		EEnumLiteral calibrationAccess = builder.getFieldValue(CALIBRATION_ACCESS).map(value -> (EEnumLiteral) value)
				.orElse(null);
		EEnumLiteral policy = builder.getFieldValue(POLICY).map(value -> (EEnumLiteral) value).orElse(null);
		ImplementationDataType parameterType = builder.getFieldValue(PARAMETER_TYPE)
				.map(value -> (ImplementationDataType) value).orElse(null);

		prototypeAdapter.setShortName(shortName);
		prototypeAdapter.setCalibrationAccess(calibrationAccess);
		prototypeAdapter.setPolicy(policy);
		prototypeAdapter.setParameterType(parameterType);
	}

}
