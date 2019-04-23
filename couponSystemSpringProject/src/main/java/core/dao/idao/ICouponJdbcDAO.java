package core.dao.idao;

import java.util.Date;
import java.util.List;

import core.enums.CouponType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Coupon;

public interface ICouponJdbcDAO extends IDAO<Coupon>{
	
	

	/**
	 *  remove all expired when the end_data value of coupon smaller the data param.
	 *  remove from customer_couponcoupon and from coupon tables
	 * 
	 * @param date too expired coupons  
	 * @throws CouponSystemException When there is a problem with a database
	 */
	void removeExpiredCoupons(Date date) throws CouponSystemException;

	/**
	 * this method check if this title exists on date base if exists return true
	 * else false
	 * 
	 * @param title the title you want to check
	 * @return if the title exists return true else false
	 * @throws CouponSystemException When there is a problem with a database
	 */
	boolean titlelExists(String title) throws CouponSystemException;

	/**
	 * return all coupon this customer can buy
	 * 
	 * @param idCustomer the customer user you want to check
	 * @return list off coupon this customer can buy
	 * @throws CouponSystemException When there is a problem with a database
	 */
	List<Coupon> getCustomerCouponsNotPurchase(long idCustomer) throws CouponSystemException;

	/**
	 * this method add details to the customer coupon table when customer buy coupon
	 * 
	 * @param idCust   the id of customer buy the coupon
	 * @param idCoupon the coupon this customer buy
	 * @throws CouponSystemException When there is a problem with a database
	 */
	void purchaseCoupon(long idCust, long idCoupon) throws CouponSystemException;

	/**
	 * get idCustomer and return the list of coupons this customer
	 * specific buy
	 * 
	 * @param idCustomer the customer coupons you want to get back
	 * @return list of coupons from data base of this customer buy
	 * @throws CouponSystemException When there is a problem with a database
	 */
	List<Coupon> getCustomerCoupons(long idCustomer) throws CouponSystemException;

	/**
	 * get customer id and type and return all coupons this Customer
	 * have off this price or last
	 * 
	 * @param type       the type of coupons
	 * @param idCustomer the id customer
	 * @return list off coupons
	 * @throws CouponSystemException When there is a problem with a database
	 */
	List<Coupon> getCustomerCouponsByType(CouponType type, long idCustomer) throws CouponSystemException;

	/**
	 * get customer id and price and return all coupons this Customer
	 * have off this price or last
	 * 
	 * @param price      the price or last off coupon you want to get back
	 * @param idCustomer the customer id
	 * @return
	 * @throws CouponSystemException
	 */
	List<Coupon> getCustomerCouponsByPrice(double price, long idCustomer) throws CouponSystemException;

	/**
	 * remove from customer coupon table in data base this coupon you
	 * insert. where the id coupon you insert equal to id coupon on data base
	 * 
	 * @param coupon the coupon you insert to remove from data
	 * @throws DAOException When there is a problem with a database
	 */
	void removeCustomerCouponByCoupon(long idCoupon) throws CouponSystemException;

	/**
	 * remove details from customer coupon table when we delete customer
	 * remove all coupons this customer have and from customer coupon
	 * 
	 * @param idCust the id of customer you want to remove
	 * @throws CouponSystemException When there is a problem with a database
	 */
	void removeCustomerCouponByCustomer(long idCust) throws CouponSystemException;

	/**
	 * get company and return the list of coupons this company have to
	 * supply for sale
	 * 
	 * @param company the company coupons you want to get back
	 * @return list of coupons from data base of this company supply
	 * @throws CouponSystemException When there is a problem with a database
	 */
	List<Coupon> getCompanyCoupons(long idCompany) throws CouponSystemException;

	/**
	 *  return all coupon this company have by price she add
	 * 
	 * @param idCompany the id company you want to get back the coupon
	 * @return list off coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCouponsByPrice(long idCompany, double price) throws CouponSystemException;

	/**
	 *  return all coupon this company have by type she add
	 * 
	 * @param idCompany the id company you want to get back the coupon by type
	 * @param type      the type off coupons you want to get back
	 * @return list off coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCouponsByType(CouponType type, long idCompany) throws CouponSystemException;

	/**
	 *  return all coupon this company have by price she add
	 * 
	 * @param idCompany the id company you want to get back the coupon
	 * @param date      the date or more off this date you want get back
	 * @return list off coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCouponsByDate(Date date, long idCompany) throws CouponSystemException;

	/**
	 *  delete all coupon off this company have
	 * 
	 * @param idCompany the company wont to delete
	 * @throws CouponSystemException When there is a problem with a database
	 */
	void removeCompanyCouponsBycompany(long idCompany) throws CouponSystemException;

	/**
	 * this method remove all customer coupon table for this company have
	 * 
	 * @param idCompany the company want to remove all coupon the have
	 * @throws CouponSystemException
	 */
	void removeCustomerCouponByCompany(long idCompany) throws CouponSystemException;

	/**
	 * check on the data base if have more coupon to buy if the amount is
	 * 0 throws CouponSystmException else set the amount of this coupon to
	 * (amount-1)
	 * 
	 * @param idCoupon the coupon you want to buy
	 * @param amountDifferense the quantity you want to change on the amount
	 * @throws CouponSystemException 1 problem on data base 2. if the amount of this
	 *                              coupon is 0
	 */
	void updateAmount(long idCoupon , int amountDifferense) throws CouponSystemException;

	

}
