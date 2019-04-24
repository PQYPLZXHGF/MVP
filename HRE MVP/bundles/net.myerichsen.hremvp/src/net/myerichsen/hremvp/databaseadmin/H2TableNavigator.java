package net.myerichsen.hremvp.databaseadmin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import net.myerichsen.hremvp.Constants;

/**
 * Create a view part with a table. Create a column for each columns in the
 * catalog for the given table. Populate the table with data from H2.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 24. apr. 2019
 *
 */

@SuppressWarnings("restriction")
public class H2TableNavigator {
	private TableViewer tableViewer;
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;
	private Table table;
	private Composite parent;
	private String tableName;

	/**
	 * Constructor
	 *
	 */
	public H2TableNavigator() {
	}

	/**
	 *
	 */
	private void clearTable() {
		H2TableProvider provider;
		try {
			provider = new H2TableProvider(tableName);
			provider.deleteAll();

			eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, "Dummy");
			eventBroker.post("MESSAGE",
					"All rows have been deleted from " + tableName);
			updateGui();
		} catch (final Exception e1) {
			eventBroker.post("MESSAGE", e1.getMessage());
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
		}
	}

	/**
	 * Create contents of the view part.
	 *
	 * @param parentC Shell
	 */
	@PostConstruct
	public void createControls(Composite parentC) {
		parent = parentC;

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openTableEditor();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Composite compositeButtons = new Composite(parent, SWT.NONE);
		compositeButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnImport = new Button(compositeButtons, SWT.NONE);
		btnImport.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				importTable(btnImport);
			}
		});
		btnImport.setText("Import Table...");

		final Button btnExport = new Button(compositeButtons, SWT.NONE);
		btnExport.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				exportTable(btnImport);
			}
		});
		btnExport.setText("Export Table...");

		final Button btnEmptyTable = new Button(compositeButtons, SWT.NONE);
		btnEmptyTable.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				clearTable();
			}
		});
		btnEmptyTable.setText("Empty Table");

		final Button btnClose = new Button(compositeButtons, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final List<MPartStack> stacks = modelService.findElements(
						application, null, MPartStack.class, null);
				final MPart part = (MPart) stacks.get(stacks.size() - 2)
						.getSelectedElement();
				partService.hidePart(part, true);
			}

		});
		btnClose.setText("Close");

		updateGui();
	}

	/**
	 * @param fileName
	 * @param tableName
	 */
	private void exportCsv(String fileName) {
		try {
			final H2TableProvider provider = new H2TableProvider(tableName);
			final List<H2TableModel> modelList = provider.getModelList();
			final List<List<Object>> rows = provider.selectAll();

			final SimpleResultSet rs = new SimpleResultSet();

			for (int i = 0; i < provider.getCount(); i++) {
				switch (modelList.get(i).getNumericType()) {
				case Constants.DOUBLE:
				case Constants.VARBINARY:
				case Constants.SMALLINT:
				case Constants.INTEGER:
				case Constants.BIGINT:
					rs.addColumn(modelList.get(i).getName(),
							modelList.get(i).getNumericType(),
							modelList.get(i).getPrecision(),
							modelList.get(i).getScale());
					break;
				case Constants.VARCHAR:
					rs.addColumn(modelList.get(i).getName(),
							modelList.get(i).getNumericType(), 0,
							modelList.get(i).getScale());
					break;
				default:
					rs.addColumn(modelList.get(i).getName(),
							modelList.get(i).getNumericType(), 0, 0);
					break;
				}
			}

			Object[] oa;

			for (int i = 0; i < rows.size(); i++) {
				oa = new Object[provider.getCount()];
				for (int j = 0; j < oa.length; j++) {
					LOGGER.log(Level.INFO,
							"Column {0}, column {1}, type {2}, value {3}",
							new Object[] { i, j, modelList.get(j).getType(),
									rows.get(i).get(j) });
//					if (modelList.get(j).getType().equals("CLOB")) {
					oa[j] = rows.get(i).get(j);
//					} else {
//						oa[j] = rows.get(i).get(j);
//					}
				}

				rs.addRow(oa);
			}

			final Csv csvFile = new Csv();
			csvFile.setFieldSeparatorWrite(",");

			// FIXME java.lang.ClassCastException: java.lang.String cannot be
			// cast to java.sql.Timestamp
			csvFile.write(fileName, rs, "UTF-8");
			eventBroker.post("MESSAGE",
					"Table " + tableName + " has been exported to " + fileName);
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * @param btnImport
	 */
	private void exportTable(final Button btnImport) {
		final FileDialog dialog = new FileDialog(btnImport.getShell(),
				SWT.SAVE);
		final String[] extensions = { "*.csv", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String fileName = dialog.getFilterPath() + "\\" + shortName;

		exportCsv(fileName);
	}

	/**
	 * @param btnImport
	 */
	private void importTable(final Button btnImport) {
		final FileDialog dialog = new FileDialog(btnImport.getShell(),
				SWT.OPEN);
		final String[] extensions = { "*.csv", "*.*" };
		dialog.setFilterExtensions(extensions);
		dialog.open();

		final String shortName = dialog.getFileName();
		final String fileName = dialog.getFilterPath() + "\\" + shortName;

		int rowCount = 0;
		try {
			final H2TableProvider provider = new H2TableProvider(tableName);
			rowCount = provider.importCsv(fileName);
			eventBroker.post("MESSAGE",
					rowCount + " rows has been imported from " + fileName);
			eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, "Dummy");
			updateGui();
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
			eventBroker.post("MESSAGE", e1.getMessage());
		}
	}

	/**
	 *
	 */
	private void openTableEditor() {
		String recordNum = "0";

		// Open an editor
		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.tableeditoropen", null);
		handlerService.executeHandler(command);
		LOGGER.log(Level.INFO, "Navigator opened editor");

		eventBroker.post(Constants.TABLENAME_UPDATE_TOPIC, tableName);
		LOGGER.log(Level.INFO, "Navigator posted tablename {0}", tableName);

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			recordNum = selectedRow.getText(0);
		}

		eventBroker.post(Constants.RECORDNUM_UPDATE_TOPIC, recordNum);
		LOGGER.log(Level.INFO, "Navigator posted record number {0}", recordNum);
		eventBroker.post("MESSAGE", tableName + " editor has been opened");
	}

	/**
	 * @param tableName
	 */
	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic(Constants.TABLENAME_UPDATE_TOPIC) String tableName2) {
		tableName = tableName2;
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2)
				.getSelectedElement();
		part.setLabel(tableName2);

		updateGui();
	}

	/**
	 *
	 */
	private void updateGui() {
		if ((tableName == null) || (tableName.equals(""))) {
			return;
		}

		try {
			final H2TableProvider provider = new H2TableProvider(tableName);

			final int count = provider.getCount();
			parent.setLayout(new GridLayout());

			if (table.getColumnCount() == 0) {
				final TableViewerColumn[] tvc = new TableViewerColumn[count];
				final TableColumn[] tc = new TableColumn[count];

				for (int i = 0; i < count; i++) {
					tvc[i] = new TableViewerColumn(tableViewer, SWT.NONE);
					tc[i] = tvc[i].getColumn();
					tc[i].setWidth(100);
					tc[i].setText(provider.getModelList().get(i).getName());
				}
			}

			List<List<Object>> rowList;

			rowList = provider.selectAll();

			table.removeAll();

			for (int i = 0; i < rowList.size(); i++) {
				final TableItem item = new TableItem(table, SWT.NONE);
				final List<Object> row = rowList.get(i);
				for (int j = 0; j < row.size(); j++) {
					if (row.get(j) != null) {
						item.setText(j, (String) row.get(j));
					} else {
						item.setText(j, "");
					}
				}
			}
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
}