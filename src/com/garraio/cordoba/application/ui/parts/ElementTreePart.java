package com.garraio.cordoba.application.ui.parts;

import java.util.Arrays;

import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.garraio.cordoba.application.ui.components.menus.TreeElementMenuManager;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;
import com.garraio.cordoba.application.ui.components.trees.providers.TreeElementContentProvider;
import com.garraio.cordoba.application.ui.components.trees.providers.TreeElementLabelProvider;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class ElementTreePart {
	private static TreeViewer viewer;
	private final ESelectionService selectionService;
	private TreeElementMenuManager menuManager;

	@Inject
	public ElementTreePart(ESelectionService selectionService) {
		this.selectionService = selectionService;
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new TreeElementContentProvider());
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new TreeElementLabelProvider()));

		TreeElement rootElement = new TreeElement("Project Root", TreeElementTypeEnum.PROJECT_ROOT);
		viewer.setInput(Arrays.asList(rootElement));

		viewer.addSelectionChangedListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			selectionService.setSelection(selection.isEmpty() ? null : selection.getFirstElement());
		});

		menuManager = new TreeElementMenuManager(viewer);

		menuManager.createContextMenu();
	}

	public static void refreshTreeAfterChange(TreeElement parent, TreeElement elementToSelect) {
		if (viewer != null) {
			viewer.refresh(parent);

			// Expand the parent if it's not already expanded
			if (!viewer.getExpandedState(parent)) {
				viewer.expandToLevel(parent, 1);
			}

			// Select the specified element
			if (elementToSelect != null) {
				viewer.setSelection(new StructuredSelection(elementToSelect), true);
			}
		}
	}
}