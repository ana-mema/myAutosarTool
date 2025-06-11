package com.garraio.cordoba.application.ui.components.menus.providers;

import org.eclipse.jface.action.IMenuManager;

import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public interface ITreeElementMenuProvider {
    void fillContextMenu(IMenuManager menuManager, TreeElement element);

}
