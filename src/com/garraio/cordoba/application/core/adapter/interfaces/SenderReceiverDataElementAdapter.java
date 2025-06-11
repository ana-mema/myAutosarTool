package com.garraio.cordoba.application.core.adapter.interfaces;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;

public class SenderReceiverDataElementAdapter extends AbstractAutosarElementAdapter<VariableDataPrototype> {

	public SenderReceiverDataElementAdapter(VariableDataPrototype dataElement) {
		super(dataElement);
	}

	public ImplementationDataType getImplementationTypeRef() {
		return (ImplementationDataType) element.getType();
	}

	public void setImplementationTypeRef(ImplementationDataType dataType) {
		element.setType(dataType);
	}

}
