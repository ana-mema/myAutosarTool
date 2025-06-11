package com.garraio.cordoba.application.ui.components.tables;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.garraio.cordoba.application.ui.components.tables.providers.EventTableColumnProvider;

import autosar40.swcomponent.swcinternalbehavior.rteevents.RTEEvent;

public class EventTable {
	private TableViewer tableViewer;
	private Composite container;
	private Label titleLabel;
	private Composite contentArea; // Area between title and table for custom controls
	private String tableId; // Unique identifier for the table, used for drag-and-drop operations

	public EventTable(Composite parent, String title) {
		container = createContainer(parent);

		titleLabel = createSectionLabel(container, title);

		contentArea = createContentArea(container);

		tableViewer = createTableViewer(container);

		tableId = UUID.randomUUID().toString();
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public Composite getContainer() {
		return container;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public Composite getContentArea() {
		return contentArea;
	}

	public String getTableId() {
		return tableId;
	}

	public int getRowCount() {
		return tableViewer.getTable().getItemCount();
	}

	public void setRows(List<RTEEvent> rows) {
		tableViewer.getTable().removeAll();

		if (rows != null) {
			tableViewer.setInput(rows);
		}

		tableViewer.refresh();
	}

	public void setupDragSource() {
		DragSource source = new DragSource(tableViewer.getControl(), DND.DROP_MOVE);
		source.setTransfer(TextTransfer.getInstance());

		source.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				IStructuredSelection selection = tableViewer.getStructuredSelection();
				if (!selection.isEmpty()) {
					String eventId = ((RTEEvent) selection.getFirstElement()).getUuid();
					event.data = tableId + ":" + eventId; // Include tableId in the data to identify the source table
				}
			}
		});
	}

	public void setupDropTarget(BiConsumer<String, String> dropHandler) {
		DropTarget target = new DropTarget(tableViewer.getControl(), DND.DROP_MOVE);
		target.setTransfer(TextTransfer.getInstance());

		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				String data = (String) event.data;
				if (data != null && dropHandler != null) {
					String[] dataParts = data.split(":");
					String sourceTableId = dataParts[0];
					String eventId = dataParts[1];
					dropHandler.accept(eventId, sourceTableId);
				}
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SELECT | DND.FEEDBACK_SCROLL;
				event.detail = DND.DROP_MOVE;
			}
		});
	}

	public int determineDropTargetIndex() {
		Table table = tableViewer.getTable();
		Point cursorPoint = table.toControl(table.getDisplay().getCursorLocation());
		TableItem item = table.getItem(cursorPoint);

		// If no item under cursor, append to end
		if (item == null) {
			return table.getItemCount();
		}

		// Calculate index based on cursor position within item
		int index = table.indexOf(item);
		return (cursorPoint.y > item.getBounds().y + item.getBounds().height / 2) ? index + 1 : index;
	}

	private Composite createContainer(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 5;
		layout.marginHeight = 20;
		layout.verticalSpacing = 5;
		container.setLayout(layout);

		return container;
	}

	private Label createSectionLabel(Composite container, String title) {
		Label label = new Label(container, SWT.NONE);
		label.setText(title);
		label.setData("org.eclipse.e4.ui.css.id", "subTitle");
		return label;
	}

	private TableViewer createTableViewer(Composite container) {
		TableViewer viewer = new TableViewer(container,
				SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);

		EventTableColumnProvider.createColumns(viewer);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		int tableHeight = 150;
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, tableHeight).applyTo(table);

		return viewer;
	}

	private Composite createContentArea(Composite parent) {
		contentArea = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(contentArea);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(contentArea);

		return contentArea;
	}

}
