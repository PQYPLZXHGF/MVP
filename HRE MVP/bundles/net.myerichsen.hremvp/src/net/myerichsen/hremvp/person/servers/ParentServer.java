package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Parents;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. jan. 2019
 *
 */
public class ParentServer implements IHREServer {
	private int ParentPid;
	private int Child;
	private int Parent;
	private String ParentRole;
	private boolean PrimaryParent;
	private int LanguagePid;

	private Parents parentRelation;

	/**
	 * Constructor
	 *
	 */
	public ParentServer() {
		parentRelation = new Parents();
	}

	/**
	 * @return the child
	 */
	public int getChild() {
		return Child;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return LanguagePid;
	}

	/**
	 * @return the parent
	 */
	public int getParent() {
		return Parent;
	}

	/**
	 * @return the parentPid
	 */
	public int getParentPid() {
		return ParentPid;
	}

	/**
	 * @return the parentRole
	 */
	public String getParentRole() {
		return ParentRole;
	}

	/**
	 * @return the primaryParent
	 */
	public boolean isPrimaryParent() {
		return PrimaryParent;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(int child) {
		Child = child;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		LanguagePid = languagePid;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(int parent) {
		Parent = parent;
	}

	/**
	 * @param parentPid the parentPid to set
	 */
	public void setParentPid(int parentPid) {
		ParentPid = parentPid;
	}

	/**
	 * @param parentRole the parentRole to set
	 */
	public void setParentRole(String parentRole) {
		ParentRole = parentRole;
	}

	/**
	 * @param primaryParent the primaryParent to set
	 */
	public void setPrimaryParent(boolean primaryParent) {
		PrimaryParent = primaryParent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.myerichsen.hremvp.servers.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}
}
