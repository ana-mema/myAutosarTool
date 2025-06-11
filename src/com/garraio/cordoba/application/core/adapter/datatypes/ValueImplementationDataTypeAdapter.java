package com.garraio.cordoba.application.core.adapter.datatypes;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.datadefproperties.SwDataDefProps;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;

public class ValueImplementationDataTypeAdapter extends AbstractAutosarElementAdapter<ImplementationDataType> {

	public ValueImplementationDataTypeAdapter(ImplementationDataType implType) {
		super(implType);
	}

	public SwBaseType getBaseType() {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props != null && !props.getSwDataDefPropsVariants().isEmpty()) {
			// Assuming only one conditional variant for VALUE types
			return props.getSwDataDefPropsVariants().get(0).getBaseType();
		}
		return null;
	}

	public void setBaseType(SwBaseType baseType) {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null) {
			props = AutosarElementFactory.createSwDataDefProps();
			element.setSwDataDefProps(props);
		}

		SwDataDefPropsConditional conditional;
		if (props.getSwDataDefPropsVariants().isEmpty()) {
			conditional = AutosarElementFactory.createSwDataDefPropsConditional();
			props.getSwDataDefPropsVariants().add(conditional);
		} else {
			// Assuming only one conditional variant for VALUE types
			conditional = props.getSwDataDefPropsVariants().get(0);
		}

		conditional.setBaseType(baseType);
	}
}
