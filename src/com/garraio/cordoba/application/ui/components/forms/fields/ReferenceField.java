package com.garraio.cordoba.application.ui.components.forms.fields;

import java.util.List;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable;

public class ReferenceField extends FormField<EObject> {
	private Combo comboControl;
	private List<? extends EObject> options;

	public ReferenceField(String label, EObject value, List<? extends EObject> options) {
		super(label, value);
		this.options = options;
	}

	@Override
	protected Control createInputControl(Composite parent) {
		// Create combo box
		comboControl = new Combo(parent, SWT.READ_ONLY);

		// Populate options
		populateComboOptions();

		return comboControl;
	}

	@Override
	public EObject getValue() {
		int index = comboControl.getSelectionIndex();
		if (index != -1) {
			return (EObject) comboControl.getData(comboControl.getItem(index));
		}
		return null;
	}

	public void setValue(EObject value) {
		if (comboControl == null || comboControl.isDisposed() || value == null) {
			return;
		}

		for (int i = 0; i < comboControl.getItemCount(); i++) {
			String item = comboControl.getItem(i);
			EObject data = (EObject) comboControl.getData(item);

			if (data != null && data.equals(value)) {
				comboControl.select(i);
				return;
			}
		}

		comboControl.deselectAll();
	}

	public List<? extends EObject> getOptions() {
		return options;
	}

	public void setOptions(List<? extends EObject> newOptions) {
		this.options = newOptions;

		if (comboControl != null && !comboControl.isDisposed()) {
			populateComboOptions();
		}

	}

	public void addChangeListener(Runnable callback) {
		if (comboControl != null && !comboControl.isDisposed()) {
			comboControl.addListener(SWT.Modify, event -> callback.run());
		}
	}

	private void populateComboOptions() {
		comboControl.removeAll();

		for (EObject option : options) {
			String displayText = getDisplayText(option);

			addOption(displayText, option);
		}
	}

	private String getDisplayText(EObject option) {
		if (option instanceof Identifiable) {
			return ((Identifiable) option).getShortName();
		} else if (option instanceof EEnumLiteral) {
			return ((EEnumLiteral) option).getName();
		} else if (option instanceof EObject) {
			return option.toString();
		}
		return null;
	}

	private void addOption(String displayText, EObject option) {
		if (displayText == null || option == null) {
			return;
		}

		comboControl.add(displayText);
		comboControl.setData(displayText, option);
	}
}