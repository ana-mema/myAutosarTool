package com.garraio.cordoba.application.ui.components.menus;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

import com.garraio.cordoba.application.ui.components.menus.providers.AtomicSWCMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.BaseTypesMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ClientServerInterfaceMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ClientServerOperationMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.CompositionSWCMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.DefaultMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ITreeElementMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ImplementationTypesMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.InterfacesMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.InternalBehaviorMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ParameterInterfaceMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.ProjectRootMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.SenderReceiverInterfaceMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.SoftwareComponentsMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.SoftwareCompositionsMenuProvider;
import com.garraio.cordoba.application.ui.components.menus.providers.StructureTypeMenuProvider;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;
import com.garraio.cordoba.application.ui.components.trees.model.TreeElementTypeEnum;

public class TreeElementMenuManager {
	private static final ITreeElementMenuProvider DEFAULT_PROVIDER = new DefaultMenuProvider();
	private TreeViewer viewer;
	private Map<TreeElementTypeEnum, ITreeElementMenuProvider> menuProviders = new HashMap<>();

	public TreeElementMenuManager(TreeViewer viewer) {
		this.viewer = viewer;
		registerMenuProviders();
	}

	/**
	 * Creates a dynamic context menu which is populated based on the selected
	 * element type
	 */
	public void createContextMenu() {
		MenuManager contextMenuManager = new MenuManager();
		Menu contextMenu = contextMenuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(contextMenu);

		contextMenuManager.addMenuListener(manager -> {
			manager.removeAll();

			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			if (selection.isEmpty()) {
				return;
			}

			TreeElement selectedElement = (TreeElement) selection.getFirstElement();
			populateContextMenu(manager, selectedElement);
		});
	}

	private void registerMenuProviders() {
		AtomicSWCMenuProvider atomicSWCMenuProvider = new AtomicSWCMenuProvider();

		menuProviders.put(TreeElementTypeEnum.PROJECT_ROOT, new ProjectRootMenuProvider());
		menuProviders.put(TreeElementTypeEnum.BASE_DATA_TYPES, new BaseTypesMenuProvider());
		menuProviders.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPES, new ImplementationTypesMenuProvider());
		menuProviders.put(TreeElementTypeEnum.IMPLEMENTATION_DATA_TYPE_STRUCTURE, new StructureTypeMenuProvider());
		menuProviders.put(TreeElementTypeEnum.INTERFACES, new InterfacesMenuProvider());
		menuProviders.put(TreeElementTypeEnum.SENDER_RECEIVER_INTERFACE, new SenderReceiverInterfaceMenuProvider());
		menuProviders.put(TreeElementTypeEnum.CLIENT_SERVER_INTERFACE, new ClientServerInterfaceMenuProvider());
		menuProviders.put(TreeElementTypeEnum.CLIENT_SERVER_OPERATION, new ClientServerOperationMenuProvider());
		menuProviders.put(TreeElementTypeEnum.PARAMETER_INTERFACE, new ParameterInterfaceMenuProvider());
		menuProviders.put(TreeElementTypeEnum.SOFTWARE_COMPONENTS, new SoftwareComponentsMenuProvider());
		menuProviders.put(TreeElementTypeEnum.APPLICATION_SOFTWARE_COMPONENT, atomicSWCMenuProvider);
		menuProviders.put(TreeElementTypeEnum.SERVICE_SOFTWARE_COMPONENT, atomicSWCMenuProvider);
		menuProviders.put(TreeElementTypeEnum.PARAMETER_SOFTWARE_COMPONENT, atomicSWCMenuProvider);
		menuProviders.put(TreeElementTypeEnum.COMPLEX_SOFTWARE_COMPONENT, atomicSWCMenuProvider);
		menuProviders.put(TreeElementTypeEnum.INTERNAL_BEHAVIOR, new InternalBehaviorMenuProvider());
		menuProviders.put(TreeElementTypeEnum.SOFTWARE_COMPOSITIONS, new SoftwareCompositionsMenuProvider());
		menuProviders.put(TreeElementTypeEnum.COMPOSITION_SOFTWARE_COMPONENT, new CompositionSWCMenuProvider());
	}

	private void populateContextMenu(IMenuManager menuManager, TreeElement element) {
		ITreeElementMenuProvider provider = getMenuProvider(element);
		provider.fillContextMenu(menuManager, element);
	}

	private ITreeElementMenuProvider getMenuProvider(TreeElement element) {
		TreeElementTypeEnum type = (element == null) ? null : element.getType();
		return menuProviders.getOrDefault(type, DEFAULT_PROVIDER);
	}

}