package com.garraio.cordoba.application.core.adapter.interfaces;

import java.util.List;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.commonstructure.datadefproperties.DatadefpropertiesPackage;
import autosar40.commonstructure.datadefproperties.SwCalibrationAccessEnum;
import autosar40.commonstructure.datadefproperties.SwDataDefProps;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.datadefproperties.SwImplPolicyEnum;
import autosar40.swcomponent.datatype.dataprototypes.ParameterDataPrototype;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;

public class ParameterPrototypeAdapter extends AbstractAutosarElementAdapter<ParameterDataPrototype> {
	private static final EEnum CALIBRATION_ACCESS_EENUM = DatadefpropertiesPackage.eINSTANCE
			.getSwCalibrationAccessEnum();
	private static final EEnum POLICY_EENUM = DatadefpropertiesPackage.eINSTANCE.getSwImplPolicyEnum();

	public ParameterPrototypeAdapter(ParameterDataPrototype prototype) {
		super(prototype);
	}

	public EEnumLiteral getCalibrationAccess() {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null || props.getSwDataDefPropsVariants().isEmpty()) {
			return null;
		}

		SwDataDefPropsConditional propsConditional = props.getSwDataDefPropsVariants().get(0);

		SwCalibrationAccessEnum calibrationAccess = propsConditional.getSwCalibrationAccess();

		return (calibrationAccess != null) ? CALIBRATION_ACCESS_EENUM.getEEnumLiteral(calibrationAccess.getName())
				: null;
	}

	public void setCalibrationAccess(EEnumLiteral literal) {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null) {
			props = AutosarElementFactory.createSwDataDefProps();
			element.setSwDataDefProps(props);
		}

		SwDataDefPropsConditional propsConditional;
		if (props.getSwDataDefPropsVariants().isEmpty()) {
			propsConditional = AutosarElementFactory.createSwDataDefPropsConditional();
			props.getSwDataDefPropsVariants().add(propsConditional);
		} else {
			propsConditional = props.getSwDataDefPropsVariants().get(0);
		}

		if (literal != null && literal.getInstance() instanceof SwCalibrationAccessEnum) {
			SwCalibrationAccessEnum calibrationAccess = (SwCalibrationAccessEnum) literal.getInstance();
			propsConditional.setSwCalibrationAccess(calibrationAccess);
		} else {
			propsConditional.setSwCalibrationAccess(null);

		}
	}

	public EEnumLiteral getPolicy() {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null || props.getSwDataDefPropsVariants().isEmpty()) {
			return null;
		}

		SwDataDefPropsConditional propsConditional = props.getSwDataDefPropsVariants().get(0);

		SwImplPolicyEnum policy = propsConditional.getSwImplPolicy();

		return (policy != null) ? POLICY_EENUM.getEEnumLiteral(policy.getName()) : null;
	}

	public void setPolicy(EEnumLiteral literal) {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null) {
			props = AutosarElementFactory.createSwDataDefProps();
			element.setSwDataDefProps(props);
		}

		SwDataDefPropsConditional propsConditional;
		if (props.getSwDataDefPropsVariants().isEmpty()) {
			propsConditional = AutosarElementFactory.createSwDataDefPropsConditional();
			props.getSwDataDefPropsVariants().add(propsConditional);
		} else {
			propsConditional = props.getSwDataDefPropsVariants().get(0);
		}

		if (literal != null && literal.getInstance() instanceof SwImplPolicyEnum) {
			SwImplPolicyEnum policy = (SwImplPolicyEnum) literal.getInstance();
			propsConditional.setSwImplPolicy(policy);
		} else {
			propsConditional.setSwImplPolicy(null);
		}

	}

	public AutosarDataType getParameterType() {
		return element.getType();
	}

	public void setParameterType(AutosarDataType parameterType) {
		element.setType(parameterType);
	}

	public List<EEnumLiteral> getCalibrationAccessLiterals() {
		return CALIBRATION_ACCESS_EENUM.getELiterals();
	}

	public List<EEnumLiteral> getPolicyLiterals() {
		return POLICY_EENUM.getELiterals();
	}

}
