package net.myerichsen.hremvp.project.servers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Dictionary;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameStyles}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. mar. 2019
 *
 */
public class LocationNameStyleServer implements IHREServer {
	private int LocationNameStylePid;
	private String IsoCode;
	private int FromDatePid;
	private int ToDatePid;
	private int LabelPid;

	private final LocationNameStyles style;

	/**
	 * Constructor
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 *
	 */
	public LocationNameStyleServer() {
		style = new LocationNameStyles();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws Exception {
		style.delete(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#deleteRemote(java.lang.String)
	 */
	@Override
	public void deleteRemote(String target) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#get()
	 */
	public List<LocationNameStyles> get() throws Exception {
		return style.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws Exception    An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws Exception {
		style.get(key);
		setLocationNameStylePid(key);
		setIsoCode(style.getIsoCode());
		setFromDatePid(style.getFromDatePid());
		setToDatePid(style.getToDatePid());

	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return FromDatePid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return IsoCode;
	}

	/**
	 * @return the labelPid
	 */
	public int getLabelPid() {
		return LabelPid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return LocationNameStylePid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getRemote(javax.servlet.http.
	 * HttpServletResponse, java.lang.String)
	 */
	@Override
	public String getRemote(HttpServletResponse response, String target)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return lls A list of lists of pid, iso code,label pid and label
	 * @throws Exception
	 */
	@Override
	public List<List<String>> getStringList() throws Exception {
		final List<List<String>> lls = new ArrayList<>();
		List<String> stringList;
		final Dictionary dictionary = new Dictionary();

		final List<LocationNameStyles> list = get();

		for (final LocationNameStyles lns : list) {
			stringList = new ArrayList<>();
			stringList.add(Integer.toString(lns.getLocationNameStylePid()));
			stringList.add(lns.getIsoCode());
			LabelPid = lns.getLabelPid();
			final List<Dictionary> fkLabelPid = dictionary
					.getFKLabelPid(LabelPid);
			stringList.add(fkLabelPid.get(0).getLabel());
			lls.add(stringList);
		}
		return lls;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#getStringList(int)
	 */
	@Override
	public List<List<String>> getStringList(int key) throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return ToDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	@Override
	public int insert() throws Exception {
		style.setLocationNameStylePid(LocationNameStylePid);
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		style.setFromDatePid(FromDatePid);
		style.setToDatePid(ToDatePid);
		return style.insert();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#insertRemote(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public void insertRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		FromDatePid = fromDatePid;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		IsoCode = isoCode;
	}

	/**
	 * @param labelPid the labelPid to set
	 */
	public void setLabelPid(int labelPid) {
		LabelPid = labelPid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		LocationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		ToDatePid = toDatePid;
	}

	/**
	 * Update a row
	 *
	 * @throws Exception An exception that provides information on a database
	 *                   access error or other errors
	 */
	@Override
	public void update() throws Exception {
		style.setLocationNameStylePid(LocationNameStylePid);
		style.setIsoCode(IsoCode);
		style.setLabelPid(LabelPid);
		style.setFromDatePid(FromDatePid);
		style.setToDatePid(ToDatePid);
		style.update();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.IHREServer#updateRemote(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public void updateRemote(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}
}
