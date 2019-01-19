package net.myerichsen.hremvp.person.providers;

import java.sql.SQLException;

import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.person.servers.PersonEventServer;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 19. jan. 2019
 *
 */
public class PersonEventProvider {
	private int PersonEventPid;
	private int EventPid;
	private int PersonPid;
	private String Role;
	private boolean PrimaryPerson;
	private boolean PrimaryEvent;

	PersonEventServer server;

	/**
	 * Constructor
	 *
	 */
	public PersonEventProvider() {
		server = new PersonEventServer();
	}

	/**
	 * @param personEventPid2
	 * @throws MvpException
	 * @throws SQLException
	 */
	public void delete(int key) throws SQLException, MvpException {
		server.delete(key);

	}

	/**
	 * @return the eventPid
	 */
	public int getEventPid() {
		return EventPid;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 *
	 */
	public boolean areMoreEvents(int key) throws SQLException {
		return server.areMoreEvents(key);
	}

	/**
	 * @return the personEventPid
	 */
	public int getPersonEventPid() {
		return PersonEventPid;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return PersonPid;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return Role;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public int insert() throws SQLException {
		server.setEventPid(EventPid);
		server.setPersonPid(PersonPid);
		server.setPrimaryEvent(isPrimaryEvent());
		server.setPrimaryPerson(isPrimaryPerson());
		server.setRole(Role);

		return server.insert();
	}

	/**
	 * @return the primaryEvent
	 */
	public boolean isPrimaryEvent() {
		return PrimaryEvent;
	}

	/**
	 * @return the primaryPerson
	 */
	public boolean isPrimaryPerson() {
		return PrimaryPerson;
	}

	/**
	 * @param eventPid the eventPid to set
	 */
	public void setEventPid(int eventPid) {
		EventPid = eventPid;
	}

	/**
	 * @param personEventPid the personEventPid to set
	 */
	public void setPersonEventPid(int personEventPid) {
		PersonEventPid = personEventPid;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		PersonPid = personPid;
	}

	/**
	 * @param primaryEvent the primaryEvent to set
	 */
	public void setPrimaryEvent(boolean primaryEvent) {
		PrimaryEvent = primaryEvent;
	}

	/**
	 * @param primaryPerson the primaryPerson to set
	 */
	public void setPrimaryPerson(boolean primaryPerson) {
		PrimaryPerson = primaryPerson;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		Role = role;
	}
}
