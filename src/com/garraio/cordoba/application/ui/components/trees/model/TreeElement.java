package com.garraio.cordoba.application.ui.components.trees.model;

import java.util.ArrayList;
import java.util.List;

public class TreeElement {
	private String name;
	private TreeElementTypeEnum type;
	private List<TreeElement> children;
	private TreeElement parent;
	private String autosarElementId;

	public TreeElement(String name) {
		this.name = name;
		this.children = new ArrayList<>();
	}

	public TreeElement(String name, TreeElementTypeEnum type) {
		this(name);
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeElementTypeEnum getType() {
		return type;
	}

	public void setType(TreeElementTypeEnum type) {
		this.type = type;
	}

	public List<TreeElement> getChildren() {
		return children;
	}

	public void setChildren(List<TreeElement> children) {
		this.children = children;
	}

	public TreeElement getParent() {
		return parent;
	}

	public void setParent(TreeElement parent) {
		this.parent = parent;
	}

	public String getAutosarElementId() {
		return autosarElementId;
	}

	public void setAutosarElementId(String autosarElementId) {
		this.autosarElementId = autosarElementId;
	}

	public void addChild(TreeElement child) {
		this.children.add(child);
		child.parent = this;
	}

	public void removeChild(TreeElement child) {
		this.children.remove(child);
		child.parent = null;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean hasChildWithName(String name) {
		for (TreeElement child : children) {
			if (child.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
