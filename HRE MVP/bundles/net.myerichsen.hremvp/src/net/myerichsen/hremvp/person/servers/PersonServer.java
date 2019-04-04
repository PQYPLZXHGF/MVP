package net.myerichsen.hremvp.person.servers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONStringer;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.Events;
import net.myerichsen.hremvp.dbmodels.Hdates;
import net.myerichsen.hremvp.dbmodels.ParentRoles;
import net.myerichsen.hremvp.dbmodels.Parents;
import net.myerichsen.hremvp.dbmodels.PartnerRoles;
import net.myerichsen.hremvp.dbmodels.Partners;
import net.myerichsen.hremvp.dbmodels.PersonEvents;
import net.myerichsen.hremvp.dbmodels.PersonNameParts;
import net.myerichsen.hremvp.dbmodels.PersonNames;
import net.myerichsen.hremvp.dbmodels.Persons;
import net.myerichsen.hremvp.dbmodels.SexTypes;
import net.myerichsen.hremvp.dbmodels.Sexes;

/**
 * Business logic interface for {@link net.myerichsen.hremvp.dbmodels.Persons}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 28. mar. 2019
 */
public class PersonServer implements IHREServer {
	private static final Logger LOGGER = Logger
			.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int personPid;
	private int birthDatePid;
	private int deathDatePid;
	private List<List<String>> nameList;
	private List<List<String>> personList;

	private final Persons person;
	Dictionary dictionary;

	/**
	 * Constructor
	 *
	 */
	public PersonServer() {
		person = new Persons();
		dictionary = new Dictionary();
		nameList = new ArrayList<>();
		personList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void delete(int key) throws Exception {
		// Delete all person_sexes
		final Sexes sex = new Sexes();

		for (final Sexes sexes : sex.getFKPersonPid(key)) {
			sex.delete(sexes.getSexesPid());
		}

		// Delete all person names
		final PersonNames name = new PersonNames();
		int namePid = 0;

		for (final PersonNames names : name.getFKPersonPid(key)) {
			namePid = names.getNamePid();

			// Delete all name parts
			final PersonNameParts part = new PersonNameParts();

			for (final PersonNameParts namePart : part.getFKNamePid(namePid)) {
				part.delete(namePart.getNamePartPid());
			}

			name.delete(namePid);
		}

		// Delete all person events and links
		final PersonEvents event = new PersonEvents();

		for (final PersonEvents events : event.getFKPersonPid(key)) {
			events.delete(events.getEventPid());
			event.delete(events.getPersonEventPid());
		}

		// Delete all partner links
		Partners partner = new Partners();

		for (final Partners p : partner.getFKPartner1(key)) {
			partner.delete(p.getPartnerPid());
		}

		partner = new Partners();

		for (final Partners p : partner.getFKPartner2(key)) {
			partner.delete(p.getPartnerPid());
		}

		// Delete all parent links
		Parents parent = new Parents();

		for (final Parents p : parent.getFKChild(key)) {
			parent.delete(p.getParentPid());
		}

		// Delete all child links
		parent = new Parents();

		for (final Parents p : parent.getFKParent(key)) {
			parent.delete(p.getParentPid());
		}

		// Delete person
		person.delete(key);
	}

	/**
	 * Delete a row
	 *
	 * @param target Target
	 */
	public void deleteRemote(String target) {

	}

	/**
	 * Get all rows
	 *
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<Persons> get() throws Exception {
		return person.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent id of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 *
	 */
	@Override
	public void get(int key) throws Exception {
		person.get(key);
		setBirthDatePid(person.getBirthDatePid());
		setPersonPid(key);
		setDeathDatePid(person.getDeathDatePid());

		// Get all names of the person
		final List<PersonNames> ln = new PersonNames().getFKPersonPid(key);
		PersonNames name;

		final PersonNameServer pns = new PersonNameServer();
		nameList = new ArrayList<>();

		List<String> ls;

		// For each name get pid, name string and primary flag
		for (int i = 0; i < ln.size(); i++) {
			ls = new ArrayList<>();
			name = ln.get(i);
			ls.add(Integer.toString(name.getNamePid()));
			pns.get(name.getNamePid());
			ls.add(pns.getNameStrings()[i]);
			ls.add(Boolean.toString(name.isPrimaryName()));

			nameList.add(ls);
		}

	}

	/**
	 * Get all names for the person
	 *
	 * @return A list of lists of strings of pids and labels
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	public List<List<String>> getAllNames() throws Exception {
		final List<List<String>> allNamesList = new ArrayList<>();
		List<String> stringList;

		final List<Persons> lnsl = person.get();

		List<PersonNames> ln;
		PersonNames name;
		final PersonNameServer ns = new PersonNameServer();
		for (final Persons person : lnsl) {
			// Get all names of each person
			ln = new PersonNames().getFKPersonPid(person.getPersonPid());

			nameList = new ArrayList<>();

			// For each name get pid, name string, and primary flag
			for (int i = 0; i < ln.size(); i++) {
				stringList = new ArrayList<>();
				name = ln.get(i);
				if (name.isPrimaryName()) {
					stringList.add(Integer.toString(name.getNamePid()));
					ns.get(name.getNamePid());
					stringList.add(ns.getNameStrings()[i]);
					allNamesList.add(stringList);
					break;
				}
			}
		}

		return allNamesList;
	}

	/**
	 * Recursive call for ancestors
	 *
	 * @param key
	 * @param childPid
	 * @param generations
	 * @return List of lists of id, child id and primary name
	 * @throws Exception
	 * @throws MvpException
	 */
	public List<List<String>> getAncestorList(int key, int childPid,
			int generations) throws Exception {
		final List<List<String>> ancestorList = new ArrayList<>();
		final PersonNameServer pnp = new PersonNameServer();

		final Parents parentRelation = new Parents();
		final List<Parents> fkChild = parentRelation.getFKChild(key);

		final List<String> ls = new ArrayList<>();
		ls.add(Integer.toString(key));
		ls.add(Integer.toString(childPid));
		ls.add(pnp.getPrimaryNameString(key));
		ancestorList.add(ls);

		if (generations-- > 0) {
			for (final Parents parent : fkChild) {
				ancestorList.addAll(
						getAncestorList(parent.getParent(), key, generations));
			}
		}

		return ancestorList;
	}

	/**
	 * @return the birthDatePid
	 */
	public int getBirthDatePid() {
		return birthDatePid;
	}

	/**
	 * @param key
	 * @return the list of children
	 * @throws Exception
	 */
	public List<List<String>> getChildrenList(int key) throws Exception {
		List<String> ls;

		final PersonNameServer pns = new PersonNameServer();
		final List<List<String>> childrenList = new ArrayList<>();

		for (final Parents parent : new Parents().getFKParent(key)) {
			ls = new ArrayList<>();
			final int pid = parent.getChild();
			ls.add(Integer.toString(pid));

			ls.add(pns.getPrimaryNameString(pid));

			childrenList.add(ls);
		}

		return childrenList;
	}

	/**
	 * @return the deathDatePid
	 */
	public int getDeathDatePid() {
		return deathDatePid;
	}

	/**
	 * Recursive call for descendants
	 *
	 * @param key
	 * @param parentPid
	 * @param generations
	 * @return
	 * @throws Exception
	 * @throws MvpException
	 */
	public List<List<String>> getDescendantList(int key, int parentPid,
			int generations) throws Exception {
		final List<List<String>> descendantList = new ArrayList<>();
		final PersonNameServer pnp = new PersonNameServer();

		final Parents parentRelation = new Parents();
		final List<Parents> fkParent = parentRelation.getFKParent(key);

		final List<String> ls = new ArrayList<>();
		ls.add(Integer.toString(key));
		ls.add(Integer.toString(parentPid));
		ls.add(pnp.getPrimaryNameString(key));
		descendantList.add(ls);

		if (generations-- > 0) {
			for (final Parents parent : fkParent) {
				descendantList.addAll(
						getDescendantList(parent.getChild(), key, generations));
			}
		}

		return descendantList;
	}

	/**
	 * @return the nameList
	 */
	public List<List<String>> getNameList() {
		return nameList;
	}

	/**
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public List<List<String>> getParentList(int key) throws Exception {
		final List<List<String>> parentList = new ArrayList<>();
		List<String> ls;
		int parentPid;
		final PersonNameServer pns = new PersonNameServer();
		ParentRoles role = new ParentRoles();
		Dictionary dictionary = new Dictionary();

		if (key == 0) {
			return parentList;
		}

		for (final Parents parent : new Parents().getFKChild(key)) {
			ls = new ArrayList<>();
			parentPid = parent.getParent();
			ls.add(Integer.toString(parentPid));
			ls.add(pns.getPrimaryNameString(parentPid));

			role.get(parent.getParentRolePid());
			dictionary.getFKLabelPid(role.getLabelPid());
			ls.add(dictionary.getLabel());

			ls.add(Boolean.toString(parent.isPrimaryParent()));

			parentList.add(ls);
		}
		return parentList;
	}

	/**
	 * @param key
	 * @return the partnerList
	 * @throws Exception
	 */
	public List<List<String>> getPartnerList(int key) throws Exception {
		List<String> ls;
		PartnerRoles role2 = new PartnerRoles();
		Dictionary dictionary = new Dictionary();
		final PersonNameServer pns = new PersonNameServer();
		final List<List<String>> partnerList = new ArrayList<>();
		final List<Partners> lpa = new Partners().getFKPartner1(key);
		lpa.addAll(new Partners().getFKPartner2(key));

		for (final Partners partner : lpa) {
			ls = new ArrayList<>();

			if (partner.getPartner1() == key) {
				ls.add(Integer.toString(partner.getPartner2()));
				ls.add(pns.getPrimaryNameString(partner.getPartner2()));
			} else {
				ls.add(Integer.toString(partner.getPartner1()));
				ls.add(pns.getPrimaryNameString(partner.getPartner1()));
			}

			role2.get(partner.getPartnerRolePid());
			dictionary.getFKLabelPid(role2.getLabelPid());
			ls.add(dictionary.getLabel());

			ls.add(Boolean.toString(partner.isPrimaryPartner()));

			partnerList.add(ls);
		}

		return partnerList;
	}

	/**
	 * @param key
	 * @return the personEventList
	 * @throws MvpException
	 * @throws Exception
	 */
	public List<List<String>> getPersonEventList(int key) throws Exception {
		Events event;
		List<String> ls;
		final List<List<String>> eventList = new ArrayList<>();
		final Hdates date = new Hdates();
		int datePid;

		if (key == 0) {
			return eventList;
		}

		for (final PersonEvents personEvent : new PersonEvents()
				.getFKPersonPid(key)) {
			event = new Events();
			event.get(personEvent.getEventPid());
//			eventName = new EventNames();
//			eventName.get(event.getEventNamePid());

			ls = new ArrayList<>();
			ls.add(Integer.toString(event.getEventPid()));
//			ls.add(eventName.getLabel());
//			ls.add(Integer.toString(personEvent.getRolePid()));
			datePid = event.getFromDatePid();
			if (datePid == 0) {
				ls.add("");
			} else {
				date.get(datePid);
				ls.add(date.getDate().toString());
			}
			datePid = event.getToDatePid();
			if (datePid == 0) {
				ls.add("");
			} else {
				date.get(datePid);
				ls.add(date.getDate().toString());
			}

			eventList.add(ls);
		}
		return eventList;
	}

	/**
	 * List all persons.
	 *
	 * @return List of lists of pid, primary name string, birth date and death
	 *         date
	 * @throws MvpException
	 * @throws Exception
	 */
	public List<List<String>> getPersonList() throws Exception {
		List<String> ls;
		final PersonNameServer pns = new PersonNameServer();

		personList.clear();
		final Hdates hdates = new Hdates();
		String s;
		int personPid2;

		for (final Persons person : get()) {
			ls = new ArrayList<>();
			personPid2 = person.getPersonPid();
			ls.add(Integer.toString(personPid2));
			ls.add(pns.getPrimaryNameString(personPid2));
			s = "";
			if (person.getBirthDatePid() > 0) {
				hdates.get(person.getBirthDatePid());
				s = hdates.getDate().toString();
			}
			ls.add(s);
			s = "";
			if (person.getDeathDatePid() > 0) {
				hdates.get(person.getDeathDatePid());
				s = hdates.getDate().toString();
			}
			ls.add(s);
			personList.add(ls);
		}

		return personList;
	}

	/**
	 * @return the personPid
	 */
	public int getPersonPid() {
		return personPid;
	}

	/**
	 * @return The primary name
	 * @throws Exception
	 */
	public String getPrimaryName() throws Exception {
		final PersonNameServer pns = new PersonNameServer();
		return pns.getPrimaryNameString(personPid);
	}

	/**
	 * Get a row remotely
	 *
	 * @param response Response
	 * @param target   Target
	 * @return List of strings
	 * @throws NumberFormatException Thrown to indicate that the application has
	 *                               attempted to converta string to one of the
	 *                               numeric types, but that the string does
	 *                               nothave the appropriate format.
	 * @throws Exception             An exception that provides information on a
	 *                               database access error or other errors
	 * @throws MvpException          Application specific exception
	 * @throws IOException           Signals that an I/O exception of some sort
	 *                               has occurred. Thisclass is the general
	 *                               class of exceptions produced by failed
	 *                               orinterrupted I/O operations
	 * @throws JSONException         The JSONException is thrown by the JSON.org
	 *                               classes when things are amiss
	 */
	public String getRemote(HttpServletResponse response, String target)
			throws Exception {
//		final String[] targetParts = target.split("/");
//		final int targetSize = targetParts.length;
//
//		get(Integer.parseInt(targetParts[targetSize - 1]));
//
		final JSONStringer js = new JSONStringer();
//		js.object();
//		js.key("personPid");
//		js.value(personPid);
//		js.key("birthDatePid");
//		js.value(birthDatePid);
//		js.key("deathDatePid");
//		js.value(deathDatePid);
//
//		js.key("nameList");
//		js.array();
//
//		for (final List<String> list : nameList) {
//			js.object();
//			js.key("namePid");
//			js.value(list.get(0));
//			js.key("nameString");
//			js.value(list.get(1));
//			js.key("primaryName");
//			js.value(list.get(2));
//			js.endObject();
//		}
//
//		js.endArray();
//
//		js.key("sexTypeList");
//		js.array();
//
//		for (final List<String> list : sexesList) {
//			js.object();
//			js.key("sexTypePid");
//			js.value(list.get(0));
//			js.key("sexTypeLabel");
//			js.value(list.get(1));
//			js.key("primarySex");
//			js.value(list.get(2));
//			js.endObject();
//		}
//
//		js.endArray();
//
//		js.key("parentList");
//		js.array();
//
//		for (final List<String> list : parentList) {
//			js.object();
//			js.key("namePid");
//			js.value(list.get(0));
//			js.key("nameString");
//			js.value(list.get(1));
//			js.key("role");
//			js.value(list.get(2));
//			js.key("primaryParent");
//			js.value(list.get(3));
//			js.endObject();
//		}
//
//		js.endArray();
//
//		js.key("partnerList");
//		js.array();
//
//		for (final List<String> list : partnerList) {
//			js.object();
//			js.key("namePid");
//			js.value(list.get(0));
//			js.key("nameString");
//			js.value(list.get(1));
//			js.key("role");
//			js.value(list.get(2));
//			js.key("primaryPartner");
//			js.value(list.get(3));
//			js.endObject();
//		}
//
//		js.endArray();
//
//		js.key("eventList");
//		js.array();
//
//		for (final List<String> list : eventList) {
//			js.object();
//			js.key("eventPid");
//			js.value(list.get(0));
//			js.key("label");
//			js.value(list.get(1));
//			js.key("role");
//			js.value(list.get(2));
//			js.key("fromDate");
//			js.value(list.get(3));
//			js.key("toDate");
//			js.value(list.get(4));
//			js.endObject();
//		}
//
//		js.endArray();
//		js.endObject();
//
//		LOGGER.fine(js.toString());

		return js.toString();
	}

	/**
	 * @param key
	 * @return the sexesList: SexesPid, PersonPid, SexTypePid, SexTypeLabel,
	 *         PrimarySex, FromDate, ToDate
	 * @throws MvpException
	 * @throws Exception
	 */
	public List<List<String>> getSexesList(int key) throws Exception {
		int sexTypePid;
		int datePid;
		List<String> ls;
		String s;

		final SexTypes st = new SexTypes();
		final Hdates hdates = new Hdates();
		final List<List<String>> sexesList = new ArrayList<>();

		if (key == 0) {
			return sexesList;
		}

		for (final Sexes sex : new Sexes().getFKPersonPid(key)) {
			ls = new ArrayList<>();
			ls.add(Integer.toString(sex.getSexesPid()));
			ls.add(Integer.toString(sex.getPersonPid()));
			sexTypePid = sex.getSexTypePid();
			ls.add(Integer.toString(sexTypePid));

			st.get(sexTypePid);
			final List<Dictionary> fkLabelPid = dictionary
					.getFKLabelPid(st.getLabelPid());

			ls.add(fkLabelPid.get(0).getLabel());

			ls.add(Boolean.toString(sex.isPrimarySex()));
			s = "";
			datePid = sex.getFromDatePid();
			if (datePid > 0) {
				hdates.get(datePid);
				s = hdates.getDate().toString();
			}
			ls.add(s);

			s = "";
			datePid = sex.getToDatePid();
			if (datePid > 0) {

				hdates.get(datePid);
				s = hdates.getDate().toString();
			}
			ls.add(s);

			sexesList.add(ls);
		}

		return sexesList;
	}

	/**
	 * @return the siblingList
	 * @throws Exception
	 */
	public List<List<String>> getSiblingList(int personPid) throws Exception {
		final List<List<String>> siblingList = new ArrayList<>();
		List<String> ls;

		if (personPid == 0) {
			return siblingList;
		}

		final PersonNameServer pns = new PersonNameServer();
		final List<Parents> fkParent = new Parents().getFKChild(personPid);

		for (final Parents parents : fkParent) {
			LOGGER.info("Parent id: " + parents.getParentPid());
			final List<Parents> fkChild = new Parents()
					.getFKParent(parents.getParentPid());
			final TreeSet<Integer> childList = new TreeSet<>();

			for (final Parents parents4 : fkChild) {
				LOGGER.info("Child id: " + parents4.getChild());
				childList.add(parents4.getChild());
				childList.remove(personPid);
			}

			for (final int child : childList) {
				ls = new ArrayList<>();
				ls.add(Integer.toString(child));
				ls.add(pns.getPrimaryNameString(child));

				siblingList.add(ls);
			}
		}
		return siblingList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList()
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a list of all names for the person
	 *
	 * @param key
	 * @return
	 * @throws MvpException
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		final List<List<String>> personNameList = new ArrayList<>();
		List<String> stringList;

		if (key == 0) {
			stringList = new ArrayList<>();
			stringList.add("0");
			stringList.add("");
			stringList.add("");
			stringList.add("");
			stringList.add("false");
			personNameList.add(stringList);
			return personNameList;
		}

		final PersonNames name = new PersonNames();
		int namePid = 0;
		StringBuilder sb;

		for (final PersonNames names : name.getFKPersonPid(key)) {
			stringList = new ArrayList<>();

			namePid = names.getNamePid();
			stringList.add(Integer.toString(namePid));

			sb = new StringBuilder();
			final PersonNameParts part = new PersonNameParts();

			// FIXME java.lang.NullPointerException
			for (final PersonNameParts namePart : part.getFKNamePid(namePid)) {
				sb.append(namePart.getLabel() + " ");
			}
			stringList.add(sb.toString().trim());

			final Hdates date = new Hdates();

			int datePid = names.getFromDatePid();

			if (datePid == 0) {
				stringList.add("");
			} else {
				date.get(datePid);
				stringList.add(date.getDate().toString());
			}

			datePid = names.getToDatePid();

			if (datePid == 0) {
				stringList.add("");
			} else {
				date.get(datePid);
				stringList.add(date.getDate().toString());
			}

			stringList.add(Boolean.toString(name.isPrimaryName()));

			personNameList.add(stringList);
		}

		return personNameList;
	}

	/**
	 * Insert a row
	 *
	 * @return The pid of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public int insert() throws Exception {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		return person.insert();
	}

	/**
	 * Insert a row
	 *
	 * @param request Request
	 */
	public void insertRemote(HttpServletRequest request) {

	}

	/**
	 * @param parentPid
	 * @param childPid
	 * @throws MvpException
	 * @throws Exception
	 */
	public void removeChild(int parentPid, int childPid) throws Exception {
		final Parents parent = new Parents();

		for (final Parents p : parent.getFKParent(parentPid)) {
			if (p.getChild() == childPid) {
				parent.delete(p.getParentPid());
			}
		}
	}

	/**
	 * @param eventPid
	 * @throws MvpException
	 * @throws Exception
	 */
	public void removeEvent(int eventPid) throws Exception {
		final PersonEvents personEvent = new PersonEvents();
		personEvent.getFKEventPid(eventPid);
		personEvent.delete(personEvent.getPersonEventPid());

		final Events event = new Events();
		event.delete(eventPid);
	}

	/**
	 * @param personPid
	 * @param parentPid
	 * @throws Exception
	 * @throws MvpException
	 */
	public void removeParent(int personPid, int parentPid) throws Exception {
		final Parents parent = new Parents();

		for (final Parents p : parent.getFKChild(parentPid)) {
			if (p.getParent() == parentPid) {
				parent.delete(p.getParentPid());
			}
		}
	}

	/**
	 * @param personPid
	 * @param partnerPid
	 * @throws Exception
	 * @throws MvpException
	 */
	public void removePartner(int personPid, int partnerPid) throws Exception {
		final Partners partner = new Partners();

		for (final Partners p : partner.getFKPartner1(personPid)) {
			if (p.getPartner2() == partnerPid) {
				partner.delete(p.getPartnerPid());
			}
		}

		for (final Partners p : partner.getFKPartner2(personPid)) {
			if (p.getPartner1() == partnerPid) {
				partner.delete(p.getPartnerPid());
			}
		}
	}

	/**
	 * @param sexPid
	 * @throws Exception
	 * @throws MvpException
	 */
	public void removeSex(int sexPid) throws Exception {
		final Sexes sex = new Sexes();
		sex.delete(sexPid);
	}

	/**
	 * @param i the birthDatePid to set
	 */
	public void setBirthDatePid(int i) {
		birthDatePid = i;
	}

	/**
	 * @param childrenList the childrenList to set
	 */
	public void setChildrenList(List<List<String>> childrenList) {
	}

	/**
	 * @param i the deathDatePid to set
	 */
	public void setDeathDatePid(int i) {
		deathDatePid = i;
	}

	/**
	 * @param nameList the nameList to set
	 */
	public void setNameList(List<List<String>> nameList) {
		this.nameList = nameList;
	}

	/**
	 * @param personList the personList to set
	 */
	public void setPersonList(List<List<String>> personList) {
		this.personList = personList;
	}

	/**
	 * @param personPid the personPid to set
	 */
	public void setPersonPid(int personPid) {
		this.personPid = personPid;
	}

	/**
	 * Update a row
	 *
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void update() throws Exception {
		person.setBirthDatePid(birthDatePid);
		person.setDeathDatePid(deathDatePid);
		person.setPersonPid(personPid);
		person.update();
	}

	/**
	 * Update a row remotely
	 *
	 * @param request Request
	 */
	public void updateRemote(HttpServletRequest request) {
	}
}