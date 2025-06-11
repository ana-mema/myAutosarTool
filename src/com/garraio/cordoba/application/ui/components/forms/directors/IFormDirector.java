package com.garraio.cordoba.application.ui.components.forms.directors;

import com.garraio.cordoba.application.ui.components.forms.FormBuilder;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public interface IFormDirector {
	void buildForm(FormBuilder builder, TreeElement element);

	void saveForm(FormBuilder builder, TreeElement element);
}
