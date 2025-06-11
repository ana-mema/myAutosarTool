package com.garraio.cordoba.application.ui.components.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.garraio.cordoba.application.ui.components.forms.fields.BooleanField;
import com.garraio.cordoba.application.ui.components.forms.fields.FormField;
import com.garraio.cordoba.application.ui.components.forms.fields.ReferenceField;
import com.garraio.cordoba.application.ui.components.forms.fields.TextField;

public class FormBuilder {
	private Composite formContainer;
	private Map<String, FormField<?>> fields;
	private FormManager formManager;

	public FormBuilder(Composite parent, FormManager manager) {
		this.formContainer = new Composite(parent, SWT.NONE);
		this.formContainer.setLayout(new GridLayout(1, false));
		this.formContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.fields = new HashMap<>();
		this.formManager = manager;
	}

	public Optional<FormField<?>> getField(String fieldName) {
		return Optional.ofNullable(fields.get(fieldName));
	}

	@SuppressWarnings("unchecked")
	public <T> Optional<T> getFieldValue(String fieldName) {
		FormField<?> field = fields.get(fieldName);
		if (field != null) {
			try {
				return Optional.ofNullable((T) field.getValue());
			} catch (ClassCastException e) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	private void notifyFieldChanged() {
		if (formManager != null) {
			formManager.markDirtyForm();
		}
	}

	public FormBuilder addFormTitle(String titleText) {
		Label formTitle = new Label(formContainer, SWT.NONE);
		formTitle.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		formTitle.setData("org.eclipse.e4.ui.css.id", "formTitle");
		formTitle.setText(titleText);
		return this;
	}

	public FormBuilder addTextField(String label, String value) {
		TextField field = new TextField(label, value);
		field.createControl(formContainer);
		field.addChangeListener(() -> notifyFieldChanged());
		fields.put(label, field);
		return this;
	}

	public FormBuilder addBooleanField(String label, boolean value) {
		BooleanField field = new BooleanField(label, value);
		field.createControl(formContainer);
		field.addChangeListener(() -> notifyFieldChanged());
		fields.put(label, field);
		return this;
	}

	public FormBuilder addReferenceField(String label, List<? extends EObject> options, EObject value) {
		ReferenceField field = new ReferenceField(label, value, options);
		field.createControl(formContainer);
		field.setValue(value);
		field.addChangeListener(() -> notifyFieldChanged());
		fields.put(label, field);
		return this;
	}

	public FormBuilder addInfoMessage(String message) {
		Label label = new Label(formContainer, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		label.setText(message);
		return this;
	}

	public FormBuilder refreshLayout() {
		if (!formContainer.isDisposed()) {
			formContainer.layout(true, true);
		}
		return this;
	}

	public FormBuilder clearForm() {
		for (Control child : formContainer.getChildren()) {
			child.dispose();
		}

		fields.clear();
		return this;
	}

	public Composite getFormContainer() {
		return formContainer;
	}
}