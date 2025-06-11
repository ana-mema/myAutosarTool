package com.garraio.cordoba.application.ui.components.forms.fields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public abstract class FormField<T> {
	// Field data
	protected String labelText;
	protected T value;

	// UI components
	protected Composite outerComposite;
	protected Composite fieldContainer;
	protected Composite fieldComposite; // Main container (parent+label+controlContainer)
	protected Composite controlContainer; // Container for the actual fieldControl
	protected Control fieldControl; // The actual input control
	protected Label labelWidget; // The label widget

	// Common layout constants
	protected static final int LABEL_WIDTH = 180;
	protected static final int CONTROL_WIDTH = 250;
	protected static final int SPACING = 10;
	protected static final int MARGIN = 5;

	public FormField(String labelText, T value) {
		this.labelText = labelText;
		this.value = value;
	}

	public Control createControl(Composite parent) {
		// Create the overall structure
		buildFieldLayout(parent);

		// Create the specific control inside its container
		fieldControl = createInputControl(controlContainer);

		applyControlLayout(fieldControl);

		return fieldComposite;
	}

	/**
	 * Creates the standardized field layout structure with label and container
	 * 
	 * @param parent The parent composite
	 */
	protected void buildFieldLayout(Composite parent) {
		// Create main container with 2-column grid for label and field area
		fieldComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = MARGIN;
		layout.marginHeight = MARGIN;
		layout.horizontalSpacing = SPACING;
		fieldComposite.setLayout(layout);
		fieldComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// Create label with fixed width
		String displayText = labelText + ":";
		labelWidget = new Label(fieldComposite, SWT.NONE);
		labelWidget.setText(displayText);
		GridData labelData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelData.widthHint = LABEL_WIDTH;
		labelWidget.setLayoutData(labelData);

		// Create fixed-width container for the field
		controlContainer = new Composite(fieldComposite, SWT.NONE);
		GridLayout containerLayout = new GridLayout(1, false);
		containerLayout.marginWidth = 0;
		containerLayout.marginHeight = 0;
		controlContainer.setLayout(containerLayout);

		// Set fixed width for control container
		GridData containerData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		containerData.widthHint = CONTROL_WIDTH;
		controlContainer.setLayoutData(containerData);
	}

	/**
	 * Apply standard layout to the input control
	 */
	protected void applyControlLayout(Control control) {
		GridData fillData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		control.setLayoutData(fillData);
	}

	protected abstract Control createInputControl(Composite parent);

	public abstract T getValue();
}
