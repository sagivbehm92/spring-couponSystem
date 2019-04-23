package core.dao.idao;

import java.sql.SQLException;

import core.exception.CouponSystemException;
import core.javaBeans.entity.Customer;

/**
 * extends DAO and interface of data access object Represents customer beans
 */
public interface ICustomerDAO extends IDAO<Customer> {

	
	/**
	 * this method check if the customerName exists in the data base
	 * 
	 * @param customerName the coustomerName you want to check
	 * @return true if exists else false
	 * @throws CouponSystemException when have sum problem on data base
	 * @throws SQLException
	 */
	boolean nameExists(String customerName) throws CouponSystemException;

	/**
	 * this method check if this client exists in the data base customer table if
	 * exists return id of this Customer else return null
	 * 
	 * @param user     the user name
	 * @param password the password of this customer name
	 * @return if exists return id of this Customer else return null
	 * @throws CouponSystemException
	 */
	Long login(String user, String password) throws CouponSystemException;

}
