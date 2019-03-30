package net.myerichsen.hremvp.person.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.dialogs.DateDialog;
import net.myerichsen.hremvp.dialogs.DateNavigatorDialog;
import net.myerichsen.hremvp.project.providers.PersonNameStyleProvider;
import net.myerichsen.hremvp.providers.HDateProvider;
import net.myerichsen.hremvp.providers.HREComboLabelProvider;

/**
 * Person name wizard page
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 30. mar. 2019
 *
 */
public class NewPersonWizardPage2 extends WizardPage {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private NewPersonWizard wizard;

	private Text textFromDate;
	private Text textToDate;

	private List<List<String>> stringList;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonWizardPage2(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person name style and dates");
		setDescription(
				"Set the name style for the new person and optionally dates limiting the validity of the name");
		this.context = context;
	}

	/**
	 *
	 */
	private void browseFromDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textFromDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textFromDate.setText(hdp.getDate().toString());
				wizard = (NewPersonWizard) getWizard();
				wizard.setFromDatePid(hdatePid);
				setErrorMessage(null);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				setErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	private void browseToDates() {
		final DateNavigatorDialog dialog = new DateNavigatorDialog(
				textToDate.getShell(), context);
		if (dialog.open() == Window.OK) {
			try {
				final int hdatePid = dialog.getHdatePid();
				final HDateProvider hdp = new HDateProvider();
				hdp.get(hdatePid);
				textToDate.setText(hdp.getDate().toString());
				wizard = (NewPersonWizard) getWizard();
				wizard.setToDatePid(hdatePid);
				setErrorMessage(null);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				setErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	private void clearFromDate() {
		textFromDate.setText("");
		wizard = (NewPersonWizard) getWizard();
		wizard.setFromDatePid(0);
		setErrorMessage(null);
	}

	/**
	 *
	 */
	private void clearToDate() {
		textToDate.setText("");
		wizard = (NewPersonWizard) getWizard();
		wizard.setToDatePid(0);
		setErrorMessage(null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets. Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		final Composite compositeNameStyle = new Composite(container,
				SWT.BORDER);
		compositeNameStyle.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeNameStyle.setLayout(new GridLayout(2, false));

		final Label lblPersonNameStyle = new Label(compositeNameStyle,
				SWT.NONE);
		lblPersonNameStyle.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPersonNameStyle.setText("Person Name Style");

		final ComboViewer comboViewerNameStyle = new ComboViewer(
				compositeNameStyle, SWT.NONE);
		final Combo comboNameStyle = comboViewerNameStyle.getCombo();

		comboNameStyle.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionIndex = comboNameStyle.getSelectionIndex();
				wizard = (NewPersonWizard) getWizard();
				wizard.setPersonNameStylePid(Integer
						.parseInt(stringList.get(selectionIndex).get(0)));
				setPageComplete(true);
				wizard.addBackPages();
				wizard.getContainer().updateButtons();
			}
		});
		comboViewerNameStyle
				.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerNameStyle.setLabelProvider(new HREComboLabelProvider(2));

		comboNameStyle.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		try {
			stringList = new PersonNameStyleProvider().getStringList();
			comboViewerNameStyle.setInput(stringList);
		} catch (final Exception e1) {
			LOGGER.severe(e1.getMessage());
		}

		// FIXME int defaultStyle = store.getInt("DEFAULTPERSONNAMESTYLE");

		final Composite compositeFrom = new Composite(container, SWT.BORDER);
		compositeFrom.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeFrom.setLayout(new GridLayout(2, false));

		final Label lblFromDate = new Label(compositeFrom, SWT.NONE);
		lblFromDate.setText("From Date");

		textFromDate = new Text(compositeFrom, SWT.BORDER);
		textFromDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textFromDate.setEditable(false);

		final Composite compositeButtonsFrom = new Composite(compositeFrom,
				SWT.NONE);
		compositeButtonsFrom.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeButtonsFrom.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewFrom = new Button(compositeButtonsFrom, SWT.NONE);
		btnNewFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewFromDate();
			}
		});
		btnNewFrom.setText("New");

		final Button btnBrowseFrom = new Button(compositeButtonsFrom, SWT.NONE);
		btnBrowseFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseFromDates();
			}
		});
		btnBrowseFrom.setText("Browse");

		final Button btnClearFrom = new Button(compositeButtonsFrom, SWT.NONE);
		btnClearFrom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearFromDate();
			}
		});
		btnClearFrom.setText("Clear");

		final Composite compositeTo = new Composite(container, SWT.BORDER);
		compositeTo.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		compositeTo.setLayout(new GridLayout(2, false));

		final Label lblToDate = new Label(compositeTo, SWT.NONE);
		lblToDate.setText("To Date");

		textToDate = new Text(compositeTo, SWT.BORDER);
		textToDate.setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textToDate.setEditable(false);

		final Composite compositeButtonsTo = new Composite(compositeTo,
				SWT.NONE);
		compositeButtonsTo.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		compositeButtonsTo.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnNewTo = new Button(compositeButtonsTo, SWT.NONE);
		btnNewTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getNewToDate();
			}
		});
		btnNewTo.setText("New");

		final Button btnBrowseTo = new Button(compositeButtonsTo, SWT.NONE);
		btnBrowseTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				browseToDates();
			}
		});
		btnBrowseTo.setText("Browse");

		final Button btnClearTo = new Button(compositeButtonsTo, SWT.NONE);
		btnClearTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearToDate();
			}
		});
		btnClearTo.setText("Clear");

		setPageComplete(false);
	}

	/**
	 *
	 */
	private void getNewFromDate() {
		final DateDialog dialog = new DateDialog(textFromDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				wizard = (NewPersonWizard) getWizard();
				wizard.setFromDatePid(hdp.insert());
				textFromDate.setText(dialog.getDate().toString());
				setErrorMessage(null);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				setErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 *
	 */
	private void getNewToDate() {
		final DateDialog dialog = new DateDialog(textToDate.getShell(),
				context);
		if (dialog.open() == Window.OK) {
			try {
				final HDateProvider hdp = new HDateProvider();
				hdp.setDate(dialog.getDate());
				hdp.setSortDate(dialog.getSortDate());
				hdp.setOriginalText(dialog.getOriginal());
				hdp.setSurety(dialog.getSurety());
				wizard = (NewPersonWizard) getWizard();
				wizard.setToDatePid(hdp.insert());
				setErrorMessage(null);
			} catch (final Exception e) {
				LOGGER.severe(e.getMessage());
				setErrorMessage(e.getMessage());
			}
		}
	}
}
