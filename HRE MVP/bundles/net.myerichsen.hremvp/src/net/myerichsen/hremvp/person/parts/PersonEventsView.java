package net.myerichsen.hremvp.person.parts;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.NavigatorFilter;
import net.myerichsen.hremvp.person.providers.PersonProvider;
import net.myerichsen.hremvp.person.wizards.NewPersonEventWizard;
import net.myerichsen.hremvp.providers.HREColumnLabelProvider;

/**
 * Display all events for a single person
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 18. feb. 2019
 */
@SuppressWarnings("restriction")
public class PersonEventsView {
	private static final String MESSAGE = "MESSAGE";

	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Inject
	private IEventBroker eventBroker;
	@Inject
	private ECommandService commandService;
	@Inject
	private EHandlerService handlerService;

	private TableViewer tableViewer;
	private PersonProvider provider;
	private NavigatorFilter navigatorFilter;
	private int personPid = 0;

	/**
	 * Constructor
	 *
	 */
	public PersonEventsView() {
		try {
			provider = new PersonProvider();
			navigatorFilter = new NavigatorFilter();
		} catch (final Exception e) {
			eventBroker.post(MESSAGE, e.getMessage());
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent  The parent composite
	 * @param context
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addFilter(navigatorFilter);

		final Table table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.
			 * swt.events.MouseEvent)
			 */
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openEventView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		tableViewerColumnId.setLabelProvider(new HREColumnLabelProvider(0));

		final TableViewerColumn tableViewerColumnLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnEvent = tableViewerColumnLabel.getColumn();
		tblclmnEvent.setWidth(100);
		tblclmnEvent.setText("Event");
		tableViewerColumnLabel.setLabelProvider(new HREColumnLabelProvider(1));

		final TableViewerColumn tableViewerColumnRole = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnRole = tableViewerColumnRole.getColumn();
		tblclmnRole.setWidth(100);
		tblclmnRole.setText("Role");
		tableViewerColumnRole.setLabelProvider(new HREColumnLabelProvider(2));

		final TableViewerColumn tableViewerColumnFromDate = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnFromDate = tableViewerColumnFromDate
				.getColumn();
		tblclmnFromDate.setWidth(100);
		tblclmnFromDate.setText("From Date");
		tableViewerColumnFromDate
				.setLabelProvider(new HREColumnLabelProvider(3));

		final TableViewerColumn tableViewerColumnToDate = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnToDate = tableViewerColumnToDate.getColumn();
		tblclmnToDate.setWidth(100);
		tblclmnToDate.setText("To Date");
		tableViewerColumnToDate.setLabelProvider(new HREColumnLabelProvider(4));

		final Label lblFilter = new Label(parent, SWT.NONE);
		lblFilter.setText("Event Filter");

		final Text textFilter = new Text(parent, SWT.BORDER);
		textFilter.addKeyListener(new KeyAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.
			 * events.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				navigatorFilter.setSearchText(textFilter.getText());
				LOGGER.log(Level.FINE,
						"Filter string: " + textFilter.getText());
				tableViewer.refresh();
			}
		});

		textFilter.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Menu menu = new Menu(table);
		table.setMenu(menu);

		final MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final WizardDialog dialog = new WizardDialog(parent.getShell(),
						new NewPersonEventWizard(personPid, context));
				dialog.open();
			}
		});
		mntmNewItem.setText("Add an event...");

		final MenuItem mntmRemoveSelectedEvent = new MenuItem(menu, SWT.NONE);
		mntmRemoveSelectedEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeEvent(parent.getShell());
			}
		});
		mntmRemoveSelectedEvent.setText("Remove selected event...");

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		try {
			tableViewer.setInput(provider.getPersonEventList(0));
		} catch (final Exception e1) {
			LOGGER.log(Level.SEVERE, e1.toString(), e1);
		}
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	protected void openEventView() {
		int eventPid = 0;

		final ParameterizedCommand command = commandService.createCommand(
				"net.myerichsen.hremvp.command.openeventview", null);
		handlerService.executeHandler(command);

		final TableItem[] selectedRows = tableViewer.getTable().getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			eventPid = Integer.parseInt(selectedRow.getText(0));
		}

		LOGGER.log(Level.INFO, "Setting personEvent pid: {0}", eventPid);
		eventBroker.post(Constants.EVENT_PID_UPDATE_TOPIC, eventPid);
	}

	/**
	 * @param shell
	 */
	protected void removeEvent(Shell shell) {
		final TableItem[] selection = tableViewer.getTable().getSelection();

		int eventPid = 0;
		String primaryName = null;
		if (selection.length > 0) {
			final TableItem item = selection[0];
			eventPid = Integer.parseInt(item.getText(0));
			primaryName = item.getText(1);
		}

		// Last chance to regret
		final MessageDialog dialog = new MessageDialog(shell,
				"Remove Event " + primaryName, null,
				"Are you sure that you will remove event " + eventPid + ", "
						+ primaryName + "?",
				MessageDialog.CONFIRM, 0, new String[] { "OK", "Cancel" });

		if (dialog.open() == Window.CANCEL) {
			eventBroker.post(MESSAGE,
					"Removal of Event " + primaryName + " has been canceled");
			return;
		}

		try {
			final PersonProvider pp = new PersonProvider();
			pp.removeEvent(eventPid);
			eventBroker.post(MESSAGE,
					"Event " + primaryName + " has been removed");
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param personPid
	 */
	@Inject
	@Optional
	private void subscribePersonPidUpdateTopic(
			@UIEventTopic(Constants.PERSON_PID_UPDATE_TOPIC) int personPid) {
		LOGGER.log(Level.FINE, "Received person id {0}", personPid);
		this.personPid = personPid;
		try {
			tableViewer.setInput(provider.getPersonEventList(personPid));
			tableViewer.refresh();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

}
