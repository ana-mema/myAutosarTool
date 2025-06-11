package com.garraio.cordoba.application.ui.components.forms.directors;

import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class DefaultFormDirector implements IFormDirector {
	@Override
	public void buildForm(FormBuilder builder, TreeElement element) {
		builder.clearForm()
		       .addInfoMessage("No specific form available for this element type.")
			   .refreshLayout();
	}

	@Override
	public void saveForm(FormBuilder builder, TreeElement element) {
		// No action needed for default director
		// This is a placeholder for the default behavior when no specific director is found
	}

}
