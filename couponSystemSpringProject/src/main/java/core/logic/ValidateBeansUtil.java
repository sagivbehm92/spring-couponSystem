package core.logic;

import java.util.Date;

import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;
import core.javaBeans.entity.Coupon;
import core.javaBeans.entity.Customer;

/**
 * @author user1 this class charge of all validate in the logic section
 *
 */

public class ValidateBeansUtil {
	// static variables to make validations
	private static final int NAME_LENGTH_MIN = 5;
	private static final int NAME_LENGTH_MAX = 50;
	private static final int PASSWORD_LENGTH_MIN = 5;
	private static final int PASSWORD_LENGTH_MAX = 50;

	public ValidateBeansUtil() {};

	/**
	 * this method check the validate of coupon
	 * 
	 * @param coupon the coupon you want to validate
	 * @throws CouponSystemException
	 */
	public static void couponValidate(Coupon coupon) throws CouponSystemException {
		// validate the title
		if (coupon.getTitle() == null) {
			throw new CouponSystemException(ErrorType.INVALIDATE_TITLE);
		} else if (coupon.getTitle().length() < 3) {
			throw new CouponSystemException(ErrorType.INVALIDATE_TITLE_SHORT);
		}
		// validate the amount
		if (coupon.getAmount() < 1) {
			throw new CouponSystemException(ErrorType.INVALIDATE_AMOUNT);
		}
		// validate the price
		if (coupon.getPrice() < 0) {
			throw new CouponSystemException(ErrorType.INVALIDATE_PRICE);
		}
		
		// validate the end date
		if (coupon.getEndDate().before(new Date((System.currentTimeMillis() - 100000)))) {
			throw new CouponSystemException(ErrorType.INVALIDATE_DATE_END_DATE);
		}
		// validate the start date(Checking if the start date befor the end date)
		if (coupon.getStartDate().getTime() > coupon.getEndDate().getTime()) {
			throw new CouponSystemException(ErrorType.INVALIDATE_DATE);
		}
		// validate the type
		if (coupon.getType() == null) {
			throw new CouponSystemException(ErrorType.INVALIDATE_TYPE);
		}
	}

	/**
	 * this method check the validate of customer beans
	 * 
	 * @param customer the customer you want to validate
	 * @throws CouponSystemException
	 */
	public static void customerValidate(Customer customer) throws CouponSystemException {
		// validate the name customer
		validateName(customer.getName());
		// validate the password customer
		validatePassword(customer.getPassword());
	}

	/**
	 * this method check the validate of company beans
	 * 
	 * @param company the company you want to validate
	 * @throws CouponSystemException
	 */
	public static void companyValidate(Company company) throws CouponSystemException {
		// validate the company name
		validateName(company.getName());
		// validate the company password
		validatePassword(company.getPassword());
		// validate the company email
		validateEmail(company.getEmail());
	}

	// ====================
	// hare we have all method this beans share
	public static void validatePassword(String password) throws CouponSystemException {
		if (password == null) {
			throw new CouponSystemException(ErrorType.INVALIDATE_PASSWORD);
		}
		if (password.length() < PASSWORD_LENGTH_MIN) {
			throw new CouponSystemException("the password you insert short must be big then :" + PASSWORD_LENGTH_MIN,
					ErrorType.INVALIDATE_PASSWORD_SHORT);
		}
		if (password.length() > PASSWORD_LENGTH_MAX) {
			throw new CouponSystemException("the password you insert big must be smaller then :" + PASSWORD_LENGTH_MAX,
					ErrorType.INVALIDATE_PASSWORD_LONG);
		}

	}

	public static void validateName(String BaensName) throws CouponSystemException {
		if (BaensName == null) {
			throw new CouponSystemException(ErrorType.INVALIDATE_NAME);
		}
		if (BaensName.length() < NAME_LENGTH_MIN || BaensName.length() > NAME_LENGTH_MAX) {
			throw new CouponSystemException("the name you insert short must be big then :" + NAME_LENGTH_MIN,
					ErrorType.INVALIDATE_NAME_SHORT);
		}
		if(BaensName.length() > NAME_LENGTH_MAX) {
			throw new CouponSystemException("the name you insert big must be smaller then:" + NAME_LENGTH_MAX,
					ErrorType.INVALIDATE_NAME_LONG);
		}

	}

	public static void validateEmail(String email) throws CouponSystemException {
		if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new CouponSystemException(ErrorType.INVALIDATE_EMAIL);
		}

	}

}
