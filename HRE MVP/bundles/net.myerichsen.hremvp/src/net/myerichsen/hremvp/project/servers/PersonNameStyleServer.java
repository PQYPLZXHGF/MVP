package net.myerichsen.hremvp.project.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.PersonNameMaps;
import net.myerichsen.hremvp.dbmodels.PersonNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.NameStyles}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 19. feb. 2019
 *
 */
public class PersonNameStyleServer implements IHREServer {
	private int nameStylePid;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;

	private PersonNameStyles style;
	private Languages language;
	private List<PersonNameMaps> mapList;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public PersonNameStyleServer() throws SQLException {
		style = new PersonNameStyles();
		language = new Languages();
		mapList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		style.delete(key);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<PersonNameStyles> get() throws SQLException {
		return style.get();
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		style.get(key);
		setLabel("style.getLabelPid()");
		setNameStylePid(style.getNameStylePid());

		setLanguageLabel(language.getLabel());
		setIsoCode(language.getIsocode());

		mapList = new PersonNameMaps().getFKNameStylePid(nameStylePid);
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the language
	 */
	public Languages getLanguage() {
		return language;
	}

	/**
	 * @return the languageLabel
	 */
	public String getLanguageLabel() {
		return languageLabel;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the mapList
	 */
	public List<PersonNameMaps> getMapList() {
		return mapList;
	}

	/**
	 * @return the nameStylePid
	 */
	public int getNameStylePid() {
		return nameStylePid;
	}

	/**
	 * @return the style
	 */
	public PersonNameStyles getStyle() {
		return style;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public int insert() throws SQLException {
		style.setLabelPid(0);
		style.setNameStylePid(nameStylePid);
		return style.insert();
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(Languages language) {
		this.language = language;
	}

	/**
	 * @param languageLabel the languageLabel to set
	 */
	public void setLanguageLabel(String languageLabel) {
		this.languageLabel = languageLabel;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param mapList the mapList to set
	 */
	public void setMapList(List<PersonNameMaps> mapList) {
		this.mapList = mapList;
	}

	/**
	 * @param nameStylePid the nameStylePid to set
	 */
	public void setNameStylePid(int nameStylePid) {
		this.nameStylePid = nameStylePid;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(PersonNameStyles style) {
		this.style = style;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public void update() throws SQLException {
		style.setLabelPid(0);
		style.setNameStylePid(nameStylePid);
		style.update();
	}
}