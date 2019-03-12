package net.myerichsen.hremvp.location.parts;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.listeners.NumericVerifyListener;
import net.myerichsen.hremvp.location.providers.LocationNamePartProvider;

/**
 * Display all data about a locatipon name part
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 12. mar. 2019
 *
 */
public class LocztionNamePartView {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	@Inject
	private IEventBroker eventBroker;

	private Text textId;
	private Text textNameId;
	private Text textName;
	private Text textPartNo;
	private Text textMapLabel;
	private Text textLabel;

	private final LocationNamePartProvider provider;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public LocztionNamePartView() throws Exception {
		provider = new LocationNamePartProvider();
	}

	/**
	 *
	 */
	protected void clear() {
		textId.setText("0");
		textNameId.setText("0");
		textPartNo.setText("0");
		textMapLabel.setText("Label");
		textLabel.setText("");
	}

	/**
	 *
	 */
	protected void close() {
		final List<MPartStack> stacks = modelService.findElements(application,
				null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2)
				.getSelectedElement();
		partService.hidePart(part, true);
	}

	/**
	 * Create contents of the view part
	 *
	 * @param parent The parent composite
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		final Label lblId = new Label(parent, SWT.NONE);
		lblId.setText("ID");

		textId = new Text(parent, SWT.BORDER);
		textId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textId.addVerifyListener(new NumericVerifyListener());
		new Label(parent, SWT.NONE);

		final Label lblName = new Label(parent, SWT.NONE);
		lblName.setLayoutData(
				new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblName.setText("Name ID");

		textNameId = new Text(parent, SWT.BORDER);
		textNameId.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textNameId.addVerifyListener(new NumericVerifyListener());

		textName = new Text(parent, SWT.BORDER);
		textName.setEditable(false);
		textName.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Label lblPartNo = new Label(parent, SWT.NONE);
		lblPartNo.setText("Part no.");

		textPartNo = new Text(parent, SWT.BORDER);
		textPartNo.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPartNo.addVerifyListener(new NumericVerifyListener());
		new Label(parent, SWT.NONE);

		textMapLabel = new Text(parent, SWT.NONE);
		textMapLabel.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textMapLabel.setEditable(false);
		textMapLabel.setText("Label");

		textLabel = new Text(parent, SWT.BORDER);
		textLabel.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button buttonSelect = new Button(composite, SWT.NONE);
		buttonSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				get();
			}
		});
		buttonSelect.setText("Select");

		final Button buttonInsert = new Button(composite, SWT.NONE);
		buttonInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insert();
			}
		});
		buttonInsert.setText("Insert");

		final Button buttonUpdate = new Button(composite, SWT.NONE);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		buttonUpdate.setText("Update");

		final Button buttonDelete = new Button(composite, SWT.NONE);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		buttonDelete.setText("Delete");

		final Button buttonClear = new Button(composite, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear();
			}
		});
		buttonClear.setText("Clear");

		final Button buttonClose = new Button(composite, SWT.NONE);
		buttonClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonClose.setText("Close");
		new Label(parent, SWT.NONE);
	}

	/**
	 *
	 */
	protected void delete() {
		try {
			provider.delete(Integer.parseInt(textId.getText()));
			eventBroker.post("MESSAGE",
					" Name Part " + textId.getText() + " has been deleted");
			clear();
		} catch (Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
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
	private void get() {
		get(Integer.parseInt(textId.getText()));
	}

	/**
	 * @param key
	 */
	private void get(int key) {
		try {
			provider.get(key);
			textId.setText(Integer.toString(provider.getLocationNamePartPid()));
			textNameId.setText(Integer.toString(provider.getLocationNamePid()));
			textName.setText(provider.getLocationName());
			textPartNo.setText(Integer.toString(provider.getPartNo()));
			textMapLabel.setText(provider.getMapLabel());
			textLabel.setText(provider.getLabel());
			eventBroker.post("MESSAGE",
					"Name Part " + textId.getText() + " has been fetched");
		} catch (final Exception e) {
			clear();
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected void insert() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLocationNamePartPid(Integer.parseInt(textId.getText()));
			provider.setLocationNamePid(Integer.parseInt(textNameId.getText()));
			provider.setPartNo(Integer.parseInt(textPartNo.getText()));
			provider.insert();
			eventBroker.post("MESSAGE",
					" Name Part " + textId.getText() + " has been inserted");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * The UI element has received the focus
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param locationNamePartPid
	 * @throws Exception
	 * @throws MvpException
	 */
	@Inject
	@Optional
	private void subscribeLocationNamePartUpdateTopic(
			@UIEventTopic(Constants.LOCATION_NAME_PART_PID_UPDATE_TOPIC) int locationNamePartPid)
			throws Exception {
		if (locationNamePartPid > 0) {
			get(locationNamePartPid);
		}
	}

	/**
	 *
	 */
	protected void update() {
		try {
			provider.setLabel(textLabel.getText());
			provider.setLocationNamePartPid(Integer.parseInt(textId.getText()));
			provider.setLocationNamePid(Integer.parseInt(textNameId.getText()));
			provider.setPartNo(Integer.parseInt(textPartNo.getText()));
			provider.update();
			eventBroker.post("MESSAGE", "Location Name Part " + textId.getText()
					+ " has been updated");
		} catch (final Exception e) {
			eventBroker.post("MESSAGE", e.getMessage());
			LOGGER.severe(e.getMessage());
		}
	}
}