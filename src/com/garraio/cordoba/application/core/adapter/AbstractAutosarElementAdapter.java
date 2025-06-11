package com.garraio.cordoba.application.core.adapter;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable;

public abstract class AbstractAutosarElementAdapter<T extends Identifiable> {
	protected T element;

	protected AbstractAutosarElementAdapter(T element) {
		this.element = element;
	}

	public T getElement() {
		return element;
	}

	public String getId() {
		return element.getUuid();
	}

	public String getShortName() {
		String name = element.getShortName();
		return name != null ? name : "";
	}

	public void setShortName(String shortName) {
		element.setShortName(shortName);
	}

	public String getCategory() {
		return element.getCategory();
	}

	public void setCategory(String category) {
		element.setCategory(category);
	}

}
