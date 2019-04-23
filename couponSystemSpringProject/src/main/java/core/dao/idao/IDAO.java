package core.dao.idao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import core.exception.CouponSystemException;

/**
 * this interface DAO data access object of this API the objective to create or
 * remove or update or read information from the data base
 */
public interface IDAO<T> extends Serializable {
	/**
	 * represents create beans specific Details in a row table. throws
	 * Exception when data base shutdown or SQL exception
	 * 
	 * @param tyep the type of beans you Want add to the data base
	 * @throws CouponSystemException when data base shutdown or SQL exception
	 */
	long create(T beans) throws CouponSystemException;

	/**
	 * represents remove beans Details from row in table on the data base.
	 * throws Exception when data base shutdown or SQL exception
	 * 
	 * @param tyep the type of beans you Want add to the data base
	 * @throws CouponSystemException when data base shutdown or SQL exception
	 */
	void remove(long Idbeans) throws CouponSystemException;

	/**
	 * represents update Details beans specific row in table. throws Exception
	 * when data base shutdown or SQL exception
	 * 
	 * @param tyep the type of beans you Want add to the data base
	 * @throws CouponSystemException when data base shutdown or SQL exception
	 */
	void update(T type) throws CouponSystemException;

	/**
	 * get a beans id and search in date base if have this specific
	 * beans . where the id beans input Equal to id in data base return beans With
	 * all the details else return null
	 * 
	 * @param idBeans the of Beans you want to get by id.
	 * @return return Beans With all the details if have this id on data else return
	 *         null
	 * @throws CouponSystemException when the data shutdown or {@link SQLException}
	 */
	T read(long idBeans) throws  CouponSystemException;

	/**
	 * check if the id exists in the data base
	 * 
	 * @param IdBeans the id you want to check
	 * @return true if exists else false
	 * @throws CouponSystemException when have sum problem on data base
	 */
	boolean idExists(long IdBeans) throws CouponSystemException;
	
	/**
	 * get all beans from date base on table beans type and return list
	 * with all beans Details throw went data base is shutdown
	 * 
	 * @return list of Company from SQL data base Company table
	 * @throws CouponSystemException When there is a problem with a database
	 */
	List<T> getAll() throws CouponSystemException;	

}