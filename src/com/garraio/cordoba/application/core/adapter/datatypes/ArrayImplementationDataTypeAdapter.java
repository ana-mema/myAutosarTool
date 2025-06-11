package com.garraio.cordoba.application.core.adapter.datatypes;

import org.eclipse.emf.common.util.EList;

import com.garraio.cordoba.application.core.adapter.AbstractAutosarElementAdapter;
import com.garraio.cordoba.application.core.factory.AutosarElementFactory;

import autosar40.commonstructure.datadefproperties.SwDataDefProps;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.implementationdatatypes.ArraySizeSemanticsEnum;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataTypeElement;
import autosar40.genericstructure.varianthandling.attributevaluevariationpoints.PositiveIntegerValueVariationPoint;

public class ArrayImplementationDataTypeAdapter extends AbstractAutosarElementAdapter<ImplementationDataType> {

	public ArrayImplementationDataTypeAdapter(ImplementationDataType implType) {
		super(implType);
	}

	public String getArraySize() {
		EList<ImplementationDataTypeElement> subElements = element.getSubElements();
		if (subElements == null || subElements.isEmpty()) {
			return "";
		}

		ImplementationDataTypeElement arraySizeElement = subElements.get(0);
		if (arraySizeElement == null) {
			return "";
		}

		PositiveIntegerValueVariationPoint sizePoint = arraySizeElement.getArraySize();
		if (sizePoint == null) {
			return "";
		}

		String sizeText = sizePoint.getMixedText();
		return sizeText != null ? sizeText : "";
	}

	public void setArraySize(String sizeValue) {
		ImplementationDataTypeElement arraySizeElement;

		if (element.getSubElements().isEmpty()) {
			arraySizeElement = AutosarElementFactory.createImplementationTypeElement(null);
			element.getSubElements().add(arraySizeElement);
		} else {
			arraySizeElement = element.getSubElements().get(0);
		}

		PositiveIntegerValueVariationPoint sizePoint = AutosarElementFactory
				.createPositiveIntegerValueVariationPoint(sizeValue);
		arraySizeElement.setArraySize(sizePoint);
	}

	public boolean isFixedSize() {
		EList<ImplementationDataTypeElement> subElements = element.getSubElements();
		if (subElements == null || subElements.isEmpty()) {
			return false;
		}

		ImplementationDataTypeElement subElement = subElements.get(0);
		if (subElement == null) {
			return false;
		}

		return ArraySizeSemanticsEnum.FIXED_SIZE == subElement.getArraySizeSemantics();
	}

	public void setFixedSize(boolean isFixed) {
		ImplementationDataTypeElement subElement;
		if (element.getSubElements().isEmpty()) {
			subElement = AutosarElementFactory.createImplementationTypeElement(null);
			element.getSubElements().add(subElement);
		} else {
			subElement = element.getSubElements().get(0);
		}

		ArraySizeSemanticsEnum semantics = isFixed ? ArraySizeSemanticsEnum.FIXED_SIZE
				: ArraySizeSemanticsEnum.VARIABLE_SIZE;

		subElement.setArraySizeSemantics(semantics);
	}

	public ImplementationDataType getImplementationType() {
		EList<ImplementationDataTypeElement> subElements = element.getSubElements();
		if (subElements == null || subElements.isEmpty()) {
			return null;
		}

		ImplementationDataTypeElement subElement = subElements.get(0);
		if (subElement == null) {
			return null;
		}

		SwDataDefProps props = subElement.getSwDataDefProps();
		if (props == null || props.getSwDataDefPropsVariants().isEmpty()) {
			return null;
		}

		SwDataDefPropsConditional conditional = props.getSwDataDefPropsVariants().get(0);
		return conditional.getImplementationDataType();
	}

	public void setImplementationType(ImplementationDataType type) {
		ImplementationDataTypeElement subElement;
		if (element.getSubElements().isEmpty()) {
			subElement = AutosarElementFactory.createImplementationTypeElement(null);
			element.getSubElements().add(subElement);
		} else {
			subElement = element.getSubElements().get(0);
		}

		SwDataDefProps props = subElement.getSwDataDefProps();
		if (props == null) {
			props = AutosarElementFactory.createSwDataDefProps();
			subElement.setSwDataDefProps(props);
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
