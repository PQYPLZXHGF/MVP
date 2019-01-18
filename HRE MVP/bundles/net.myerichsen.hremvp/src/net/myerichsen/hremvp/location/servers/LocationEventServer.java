package net.myerichsen.hremvp.location.servers;

import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.dbmodels.EventNames;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.LocationEvents;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationEvents}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 25. nov. 2018
 *
 */
public class LocationEventServer {
//	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param locationPid The persistent ID of the row
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception
	 */
	public List<List<String>> getEventList(int locationPid) throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		int eventPid;
		int namePid;

		final Events event = new Events();
		final EventNames name = new EventNames();
		final LocationEvents link = new LocationEvents();
		final List<LocationEvents> a = link.getFKLocationPid(locationPid);

		for (final LocationEvents eventLink : a) {
			stringList = new ArrayList<>();

			eventPid = eventLink.getEventPid();
			event.get(eventPid);
			stringList.add(Integer.toString(eventPid));

			namePid = event.getEventNamePid();
			name.get(namePid);
			stringList.add(name.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

}