package net.myerichsen.hremvp.project.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.HreTypeLabelEditingSupport;
import net.myerichsen.hremvp.project.providers.DictionaryProvider;
import net.myerichsen.hremvp.project.providers.EventRoleProvider;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display a Event Role with all language labels
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 23. mar. 2019
 *
 */

public class EventRoleView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;

	private Text textEventRolePid;
	private Text textLabelPid;
	private Text textAbbreviation;
	private TableViewer tableViewer;
	private final EventRoleProvider provider;
	private DictionaryProvider dp;
	private int eventRolePid = 0;
	private int labelPid = 0;
	private Text textEventTypePid;

	/**
	 * Constructor
	 *
	 */
	public EventRoleView() {
		provider = new EventRoleProvider();
	}

	/**
	 *
	 */
	private void addEditingSupport() {
		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				tableViewer, new FocusCellOwnerDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(
				tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL)
						|| (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION)
						|| ((event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED)
								&& (event.keyCode == SWT.CR))
						|| (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
			}
		};

		TableViewerEditor.create(tableViewer, focusCellManager,
				editorActivationStrategy,
				ColumnViewerEditor.TABBING_HORIZONTAL
						| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL
						| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getTable().addTraverseListener(new TraverseListener() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.
			 * swt.events.TraverseEvent)
			 */
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == SWT.TAB) {
					LOGGER.fine("Traversed " + e.keyCode);

					final int itemCount = tableViewer.getTable().getItemCount();
					final int selectionIndex = tableViewer.getTable()
							.getSelectionIndex();
					if (selectionIndex < (itemCount - 1)) {
						e.doit = false;

					} else {
						e.doit = true;
					}

				}
			}

		});
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(4, false));

		final Label lblEventRoleId = new Label(parent, SWT.NONE);
		lblEventRoleId.setText("Event Role id");

		textEventRolePid = new Text(parent, SWT.BORDER);
		textEventRolePid.setEditable(false);
		textEventRolePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblLabelPid = new Label(parent, SWT.NONE);
		lblLabelPid.setText("Label id");

		textLabelPid = new Text(parent, SWT.BORDER);
		textLabelPid.setEditable(false);
		textLabelPid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblEventTypeId = new Label(parent, SWT.NONE);
		lblEventTypeId.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEventTypeId.setText("Event Type id");

		textEventTypePid = new Text(parent, SWT.BORDER);
		textEventTypePid.setEditable(false);
		textEventTypePid.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		final Label lblAbbreviation = new Label(parent, SWT.NONE);
		lblAbbreviation.setText("Abbreviation");

		textAbbreviation = new Text(parent, SWT.BORDER);
		textAbbreviation.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(80);
		tblclmnId.setText("Event Role Id");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabelId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnILabelId = tableViewerColumnLabelId
				.getColumn();
		tblclmnILabelId.setWidth(80);
		tblclmnILabelId.setText("Dictionary label Id");
		tableViewerColumnLabelId
				.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnIsoCode = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnIsoCode = tableViewerColumnIsoCode.getColumn();
		tblclmnIsoCode.setWidth(80);
		tblclmnIsoCode.setText("ISO Code");
		tableViewerColumnIsoCode
				.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnLabel = tableViewerColumnLabel.getColumn();
		tblclmnLabel.setWidth(394);
		tblclmnLabel.setText("Event label");
		tableViewerColumnLabel.setEditingSupport(
				new HreTypeLabelEditingSupport(tableViewer, 3));
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(3));

		addEditingSupport();

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));

		final Button btnUpdate = new Button(composite, SWT.NONE);
		btnUpdate.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.
			 * events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				updateEventRole();
			}
		});
		btnUpdate.setText("Update");

		try {
			provider.get();
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(provider.getStringList(labelPid));
		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
			e1.printStackTrace();
		}

	}

	/**
	 *
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param ls A list of event role pid, event type pid, dictionary pid and
	 *           abbreviation
	 *
	 */
	@Inject
	@Optional
	private void subscribeLabelPidUpdateTopic(
			@UIEventTopic(Constants.LABEL_PID_UPDATE_TOPIC) List<String> ls) {

		try {
			provider.get();
			final String eventRolePidString = ls.get(0);
			textEventRolePid.setText(eventRolePidString);
			final String eventTypePidString = ls.get(1);
			textEventTypePid.setText(eventTypePidString);
			eventRolePid = Integer.parseInt(eventRolePidString);
			textLabelPid.setText(ls.get(2));
			textAbbreviation.setText(ls.get(3));

			provider.get(eventRolePid);
			labelPid = provider.getLabelPid();
			tableViewer.setInput(provider.getStringList(labelPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void updateEventRole() {
		if (textAbbreviation.getText().isEmpty()) {
			eventBroker.post("MESSAGE", "Abbreviation must not be empty");
			textAbbreviation.setFocus();
			return;
		}

		try {
			final List<List<String>> eventRoleList = provider
					.getStringList(labelPid);

			eventRolePid = Integer.parseInt(eventRoleList.get(0).get(0));
			labelPid = Integer.parseInt(eventRoleList.get(0).get(1));

			provider.get(eventRolePid);
			provider.setAbbreviation(textAbbreviation.getText());
			provider.update();
			LOGGER.info("Event pid " + eventRolePid + " has been updated");

			dp = new DictionaryProvider();
			final List<List<String>> stringList = dp.getStringList(labelPid);

			final List<List<String>> input = (List<List<String>>) tableViewer
					.getInput();

			for (int i = 0; i < input.size(); i++) {
				for (final List<String> existingElement : stringList) {
					LOGGER.info(input.get(i).get(2) + ", " + input.get(i).get(3)
							+ " - " + existingElement.get(0) + ", "
							+ existingElement.get(1) + ", "
							+ existingElement.get(2));

					if (input.get(i).get(2).equals(existingElement.get(0))) {
						if ((input.get(i).get(3)
								.equals(existingElement.get(1)) == false)) {
							dp = new DictionaryProvider();
							dp.setDictionaryPid(
									Integer.parseInt(existingElement.get(2)));
							dp.setIsoCode(input.get(i).get(2));
							dp.setLabel(input.get(i).get(3));
							dp.setLabelPid(provider.getLabelPid());
							dp.setLabelType("EVENTROLE");
							dp.update();
							LOGGER.info("Updated dictionary element "
									+ input.get(i).get(0) + ", "
									+ input.get(i).get(1) + ", "
									+ input.get(i).get(2) + ", "
									+ input.get(i).get(3));
						}
						break;
					}
				}
			}
			eventBroker.post("MESSAGE",
					"Event Role " + eventRolePid + " has been updated");
		} catch (final Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}