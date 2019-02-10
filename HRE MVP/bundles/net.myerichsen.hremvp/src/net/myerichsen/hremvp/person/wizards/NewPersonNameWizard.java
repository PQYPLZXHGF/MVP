package net.myerichsen.hremvp.person.wizards;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.person.providers.PersonNamePartProvider;
import net.myerichsen.hremvp.person.providers.PersonNameProvider;

/**
 * Wizard to add a new person name
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 7. feb. 2019
 *
 */
public class NewPersonNameWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final IEclipseContext context;
	private final IEventBroker eventBroker;

	private NewPersonNameWizardPage1 page1;
	private NewPersonNameWizardPage2 page2;

	private int personPid;
	private String personName;
	private int personNameStylePid;
//	private int personNamePid;
	private int languagePid;

	/**
	 * Constructor
	 * 
	 * @param personPid
	 * @param context   The Eclipse Context
	 *
	 */
	public NewPersonNameWizard(int personPid, IEclipseContext context) {
		setWindowTitle("New Person Name");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		this.personPid = personPid;
	}

	/**
	 *
	 */
	public void addPage2() {
		page2 = new NewPersonNameWizardPage2(context);
		addPage(page2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonNameWizardPage1(context);
		addPage(page1);
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the page1
	 */
	public NewPersonNameWizardPage1 getPage1() {
		return page1;
	}

	/**
	 * @return the page2
	 */
	public NewPersonNameWizardPage2 getPage2() {
		return page2;
	}

	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @return the personNameStylePid
	 */
	public int getPersonNameStylePid() {
		return personNameStylePid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * /* (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		context.get(IEventBroker.class);

		try {
			// Page 1
			// Create a new name
			final PersonNameProvider personNameProvider = new PersonNameProvider();
			personNameProvider.setPersonPid(personPid);
			personNameProvider.setNameStylePid(page1.getPersonNameStylePid());
			personNameProvider.setFromDatePid(page1.getFromDatePid());
			personNameProvider.setToDatePid(page1.getToDatePid());
			personNameProvider.setPrimaryName(true);
			final int namePid = personNameProvider.insert();
			LOGGER.info("Inserted name " + namePid + " for person " + personPid);

			// Page 2
			// Name parts
			PersonNamePartProvider personNamePartProvider;
			final List<String> nameParts = page2.getNameParts();
			String string;
			int namePartPid;

			// Create each name part
			for (int i = 0; i < nameParts.size(); i++) {
				string = nameParts.get(i);

				if (string != null) {
					personNamePartProvider = new PersonNamePartProvider();
					personNamePartProvider.setNamePid(namePid);
					personNamePartProvider.setLabel(string);
					personNamePartProvider.setPartNo(i);
					namePartPid = personNamePartProvider.insert();
					LOGGER.info("Inserted name part " + namePartPid + " for person " + personPid);
				}
			}

//			eventBroker.post("MESSAGE",
//					personProvider.getPrimaryName() + " inserted in the database as no. " + personPid);
			eventBroker.post(net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC, personPid);
			eventBroker.post(net.myerichsen.hremvp.Constants.NAME_PID_UPDATE_TOPIC, namePid);
			return true;
		} catch (

		final Exception e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * @param personNameStylePid the personNameStylePid to set
	 */
	public void setPersonNameStylePid(int personNameStylePid) {
		this.personNameStylePid = personNameStylePid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}
}