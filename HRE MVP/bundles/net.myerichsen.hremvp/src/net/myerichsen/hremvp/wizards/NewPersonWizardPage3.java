package net.myerichsen.hremvp.wizards;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.dbmodels.NameMaps;
import net.myerichsen.hremvp.person.providers.PersonNameMapProvider;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 14. jan. 2019
 *
 */
public class NewPersonWizardPage3 extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@SuppressWarnings("unused")
	private final IEclipseContext context;

	private int personNameStylePid;
	private PersonNameMapProvider provider;
	private List<Text> textFieldList;

	/**
	 * 
	 * Constructor
	 *
	 * @param context
	 */
	public NewPersonWizardPage3(IEclipseContext context) {
		super("wizardPage");
		setTitle("Person Name Parts");
		setDescription("Enter each part of the name");
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.
	 * Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		try {
			NewPersonWizard wizard = (NewPersonWizard) getWizard();
			personNameStylePid = wizard.getPersonNameStylePid();

			provider = new PersonNameMapProvider();
			List<NameMaps> mapList = provider.getFKNameStylePid(personNameStylePid);
			textFieldList = new ArrayList<Text>();

			for (int i = 0; i < mapList.size(); i++) {
				Label lblNewLabel = new Label(container, SWT.NONE);
//				lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblNewLabel.setText(mapList.get(i).getLabel());

				Text text = new Text(container, SWT.BORDER);
				text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				textFieldList.add(text);
			}
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

}
