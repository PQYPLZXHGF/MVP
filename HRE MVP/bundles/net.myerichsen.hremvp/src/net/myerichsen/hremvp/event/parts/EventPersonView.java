package net.myerichsen.hremvp.event.parts;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.location.providers.LocationPersonProvider;

/**
 * Display all persons for a single location
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. jan. 2019
 */
public class EventPersonView {
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
	private Table table;

	private final LocationPersonProvider provider;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public EventPersonView() throws Exception {
		provider = new LocationPersonProvider();
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		TableViewer tableViewer = new TableViewer(parent,
				SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openPersonView();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		final TableViewerColumn tableViewerColumnEventId = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnId = tableViewerColumnEventId.getColumn();
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");

		final TableViewerColumn tableViewerColumnEventLabel = new TableViewerColumn(
				tableViewer, SWT.NONE);
		final TableColumn tblclmnPerson = tableViewerColumnEventLabel
				.getColumn();
		tblclmnPerson.setWidth(300);
		tblclmnPerson.setText("Person");
	}

	/**
	 * The object is not needed anymore, but not yet destroyed
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			String[] sa;
			TableItem item;

			table.removeAll();

			final List<String> nameList = provider.getPersonList(key);

			for (int i = 0; i < nameList.size(); i++) {
				sa = nameList.get(i).split(",");
				item = new TableItem(table, SWT.NONE);
				item.setText(0, sa[0]);
				item.setText(1, sa[1]);
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

	}

	/**
	 *
	 */
	protected void openPersonView() {
		final String contributionURI = "bundleclass://net.myerichsen.hremvp/net.myerichsen.hremvp.person.parts.PersonView";

		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		MPart part = MBasicFactory.INSTANCE.createPart();

		boolean found = false;

		for (final MPartStack mPartStack : stacks) {
			final List<MStackElement> a = mPartStack.getChildren();

			for (int i = 0; i < a.size(); i++) {
				part = (MPart) a.get(i);
				if (part.getContributionURI().equals(contributionURI)) {
					partService.showPart(part, PartState.ACTIVATE);
					found = true;
					break;
				}
			}
		}

		if (!found) {
			part.setLabel("Person View");
			part.setCloseable(true);
			part.setVisible(true);
			part.setContributionURI(contributionURI);
			stacks.get(stacks.size() - 2).getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}

		String personPid = "0";

		final TableItem[] selectedRows = table.getSelection();

		if (selectedRows.length > 0) {
			final TableItem selectedRow = selectedRows[0];
			personPid = selectedRow.getText(0);
		}

		LOGGER.log(Level.INFO, "Setting person pid: {0}", personPid);
		eventBroker.post(Constants.PERSON_PID_UPDATE_TOPIC,
				Integer.parseInt(personPid));

	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param key
	 * @throws Exception
	 */
	@Inject
	@Optional
	private void subscribeKeyUpdateTopic(
			@UIEventTopic(Constants.LOCATION_PID_UPDATE_TOPIC) int key) {
		get(key);
	}

}
