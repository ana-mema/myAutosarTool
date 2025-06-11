package com.garraio.cordoba.application.core.adapter.interfaces;

import java.util.List;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;

import autosar40.genericstructure.generaltemplateclasses.primitivetypes.ArgumentDirectionEnum;
import autosar40.genericstructure.generaltemplateclasses.primitivetypes.PrimitivetypesPackage;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;

public class ClientServerOperationArgumentAdapter extends AbstractAutosarElementAdapter<ArgumentDataPrototype> {
	private static final EEnum DIRECTION_EENUM = PrimitivetypesPackage.eINSTANCE.getArgumentDirectionEnum();

	public ClientServerOperationArgumentAdapter(ArgumentDataPrototype element) {
		super(element);
	}

	public AutosarDataType getArgumentType() {
		return element.getType();
	}

	public void setArgumentType(AutosarDataType type) {
		element.setType(type);
	}

	public EEnumLiteral getArgumentDirection() {
		ArgumentDirectionEnum direction = element.getDirection();
		return (direction != null) ? DIRECTION_EENUM.getEEnumLiteral(direction.getName()) : null;
	}

	public void setArgumentDirection(EEnumLiteral literal) {
		if (literal != null && literal.getInstance() instanceof ArgumentDirectionEnum) {
			ArgumentDirectionEnum direction = (ArgumentDirectionEnum) literal.getInstance();
			element.setDirection(direction);
		} else {
			element.setDirection(null);
		}
	}

	public List<EEnumLiteral> getArgumentDirectionLiterals() {
		return DIRECTION_EENUM.getELiterals();
	}
}
