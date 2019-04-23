package core.dao.idao;


import java.sql.SQLException;

import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;

/**
 * extends DAO and interface of data access object Represents company beans
 */
public interface ICompanyDAO extends IDAO<Company> {



	/**
	 * this method check if the companyName exists in the data base
	 * if exists return true else false
	 * 
	 * @param companyName the company name you want to check
	 * @return true if exists else false
	 * @throws CouponSystemException when have sum problem on data base
	 * @throws SQLException 
	 */
	boolean nameExists(String companyName) throws CouponSystemException;

	/**
	 * this method check if this client exists in the data base company table if
	 * exists return id of this company else return null
	 * 
	 * @param user the user name of client
	 * @param password the password of this company name
	 * @return if exists return id of this company else return null
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	Long login(String user, String password) throws CouponSystemException;

}
