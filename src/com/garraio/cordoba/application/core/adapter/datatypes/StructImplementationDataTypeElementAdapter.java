package com.garraio.cordoba.application.core.adapter.datatypes;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.commonstructure.datadefproperties.SwDataDefProps;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataTypeElement;

public class StructImplementationDataTypeElementAdapter
		extends AbstractAutosarElementAdapter<ImplementationDataTypeElement> {

	public StructImplementationDataTypeElementAdapter(ImplementationDataTypeElement element) {
		super(element);
	}

	public ImplementationDataType getImplementationType() {
		SwDataDefProps props = element.getSwDataDefProps();
		if (props == null) {
			return null;
		}

		if (props.getSwDataDefPropsVariants().isEmpty()) {
			return null;
		}

		SwDataDefPropsConditional conditional = props.getSwDataDefPropsVariants().get(0);

		return conditional.getImplementationDataType();
	}

	public void setImplementationType(ImplementationDataType type) {
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
			conditional = props.getSwDataDefPropsVariants().get(0);
		}

		conditional.setImplementationDataType(type);
	}

}
