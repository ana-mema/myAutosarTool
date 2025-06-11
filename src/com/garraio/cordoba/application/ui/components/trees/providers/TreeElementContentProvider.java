package com.garraio.cordoba.application.ui.components.trees.providers;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class TreeElementContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			return ((List<?>) inputElement).toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof TreeElement) {
			return ((TreeElement) parentElement).getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof TreeElement) {
			return ((TreeElement) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof TreeElement) {
			return ((TreeElement) element).hasChildren();
		}
		return false;
	}
}
