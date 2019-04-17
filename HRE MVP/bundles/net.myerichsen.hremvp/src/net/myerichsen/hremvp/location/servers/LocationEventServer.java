package net.myerichsen.hremvp.location.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.LocationEvents;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationEvents}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 16. apr. 2019
 *
 */
public class LocationEventServer implements IHREServer {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private int LocationEventsPid;
	private int EventPid;
	private int LocationPid;
	private boolean PrimaryEvent;
	private boolean PrimaryLocation;

	private final LocationEvents locationEvent;

	/**
	 * Constructor
	 *
	 */
	public LocationEventServer() {
		super();
		locationEvent = new LocationEvents();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @param locationPid
	 * @throws Exception
	 */
	public void deleteAllEventLinksForLocation(int locationPid)
			throws Exception {
		final List<LocationEvents> fkLocationPid = locationEvent
				.getFKLocationPid(locationPid);

		for (final LocationEvents event : fkLocationPid) {
			locationEvent.delete(event.getLocationEventsPid());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @param locationPid The persistent ID of the row
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception
	 */
	public List<List<String>> getEventList(int locationPid) throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;

		int eventPid;
//		int namePid;

		final Events event = new Events();
		final LocationEvents link = new LocationEvents();
		final List<LocationEvents> a = link.getFKLocationPid(locationPid);

		for (final LocationEvents eventLink : a) {
			stringList = new ArrayList<>();

			eventPid = eventLink.getEventPid();
			event.get(eventPid);
			stringList.add(Integer.toString(eventPid));

//			namePid = event.getEventNamePid();
//			name.get(namePid);
//			stringList.add(name.getLabel());
			lls.add(stringList);
		}

		return lls;
	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return EventPid;
	}

	/**
	 * @return the locationEventsPid
	 */
	public int getLocationEventsPid() {
		return LocationEventsPid;
	}

	/**
	 * @return the locationPid
	 */
	public int getLocationPid() {
		return LocationPid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		LocationServer ls;

		final List<String> locationStringList = new ArrayList<>();

		final LocationEvents locationEvents = new LocationEvents();
		final List<LocationEvents> leList = locationEvents.getFKEventPid(key);

		try {
			for (final LocationEvents le : leList) {
				ls = new LocationServer();
				LocationPid = le.getLocationPid();
				ls.get(LocationPid);
				locationStringList.add(Integer.toString(LocationPid));
				locationStringList.add(ls.getPrimaryName());
			}
		} catch (final MvpException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

		lls.add(locationStringList);
		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insert()
	 */
	@Override
	public int insert() throws Exception {
		locationEvent.setEventPid(EventPid);
		locationEvent.setLocationPid(LocationPid);
		locationEvent.setPrimaryEvent(PrimaryEvent);
		locationEvent.setPrimaryLocation(PrimaryLocation);
		return locationEvent.insert();
	}

	/**
	 * @return the primaryEvent
	 */
	public boolean isPrimaryEvent() {
		return PrimaryEvent;
	}

	/**
	 * @return the primaryLocation
	 */
	public boolean isPrimaryLocation() {
		return PrimaryLocation;
	}

	/**
	 * @param eventPid the eventPid to set
	 */
	public void setEventPid(int eventPid) {
		EventPid = eventPid;
	}

	/**
	 * @param locationEventsPid the locationEventsPid to set
	 */
	public void setLocationEventsPid(int locationEventsPid) {
		LocationEventsPid = locationEventsPid;
	}

	/**
	 * @param locationPid the locationPid to set
	 */
	public void setLocationPid(int locationPid) {
		LocationPid = locationPid;
	}

	/**
	 * @param primaryEvent the primaryEvent to set
	 */
	public void setPrimaryEvent(boolean primaryEvent) {
		PrimaryEvent = primaryEvent;
	}

	/**
	 * @param primaryLocation the primaryLocation to set
	 */
	public void setPrimaryLocation(boolean primaryLocation) {
		PrimaryLocation = primaryLocation;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#update()
	 */
	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}

}
