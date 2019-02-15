package net.myerichsen.hremvp.person.wizards;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.wizard.Wizard;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.providers.PartnerProvider;

/**
 * Wizard to add an existing person as a Partner
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 15. feb. 2019
 *
 */
public class NewPersonPartnerWizard extends Wizard {
	private final static Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private IEclipseContext context;
	private IEventBroker eventBroker;

	private NewPersonPartnerWizardPage1 page1;
	private int personPid;

	/**
	 * Constructor
	 * 
	 * @param parentPid
	 * @param context   The Eclipse Context
	 *
	 */
	public NewPersonPartnerWizard(int personPid, IEclipseContext context) {
		setWindowTitle("Add Partner");
		setForcePreviousAndNextButtons(true);
		this.context = context;
		eventBroker = context.get(IEventBroker.class);
		this.personPid = personPid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (page1.getPartnerPid() != 0) {
			final PartnerProvider partnerProvider = new PartnerProvider();
			partnerProvider.setPartner1(personPid);
			int partnerPid = page1.getPartnerPid();
			partnerProvider.setPartner2(partnerPid);
			partnerProvider.setPrimaryPartner(true);
			partnerProvider.setRole(page1.getPartnerRole());
			partnerProvider.setFromDatePid(page1.getPartnerFromDatePid());
			partnerProvider.setToDatePid(page1.getPartnerToDatePid());

			try {
				partnerProvider.insert();
				LOGGER.info("Inserted partner pid " + page1.getPartnerPid());

				LOGGER.info("Inserted Partner pid " + partnerPid
						+ " for parent " + personPid);

				eventBroker.post("MESSAGE", "Inserted Partner pid " + partnerPid
						+ " for person " + personPid);
				eventBroker.post(
						net.myerichsen.hremvp.Constants.PERSON_PID_UPDATE_TOPIC,
						personPid);
				return true;
			} catch (SQLException | MvpException e) {
				LOGGER.severe(e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.
	 * IWizardPage)
	 */
	@Override
	public void addPages() {
		page1 = new NewPersonPartnerWizardPage1(context);
		addPage(page1);
	}

}