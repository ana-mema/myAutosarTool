package com.garraio.cordoba.application.ui.components.trees.providers;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.garraio.cordoba.application.ui.components.trees.model.TreeElement;

public class TreeElementLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private ResourceManager resourceManager;
	private Bundle bundle;

	public TreeElementLabelProvider() {
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources());
		this.bundle = FrameworkUtil.getBundle(getClass());
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof TreeElement) {
			TreeElement treeElement = (TreeElement) element;
			StyledString styledString = new StyledString(treeElement.getName());
			return styledString;
		}
		return new StyledString();
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof TreeElement)) {
			return null;
		}

		TreeElement treeElement = (TreeElement) element;
		String type = treeElement.getType().getDisplayName();
		URL url = FileLocator.find(bundle, new Path("icons/matlab/" + type + ".png"), null);

		if (url != null) {
			ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
			return resourceManager.createImage(descriptor);
		}

		return null;
	}

	@Override
	public void dispose() {
		if (resourceManager != null) {
			resourceManager.dispose();
			resourceManager = null;
		}

		super.dispose();
	}
}
