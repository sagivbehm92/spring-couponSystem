package core.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import core.enums.ClientType;
import core.enums.CouponType;
import core.enums.ErrorType;
import core.exception.CouponSystemException;

public class UtilApi {

	// half an hour
	public static final int COOKIE_SESSION_TIME = (-1);
	public static final int COOKIE_MAX_TIME = Integer.MAX_VALUE;

	public UtilApi() {};

	/**
	 * get a string and try pares the string to CouponType if succeeded return the
	 * enum CouponType else throw CouponSystemException
	 * 
	 * @param type string you try to pares
	 * @return CouponType enum like the string
	 * @throws CouponSystemException when cen't pares the string to CouponType
	 */
	public static CouponType checkCouponType(String type) throws CouponSystemException {
		try {
			// try parse string to clientType(enum)
			CouponType couponType = CouponType.valueOf(type);
			return couponType;
		} catch (Exception e) {
			// when the string illegal and impossible to parse the string
			throw new CouponSystemException("the coupon type you insert not allowed", ErrorType.ILLEGAL_TYPE);
		}

	}

	/**
	 * get a httpServletRequest and cookieName and return cookie matching to
	 * httpServletRequest and cookieName or null if not exists
	 * 
	 * @param httpServletRequest the request off client
	 * @param cookieName         the cookie name you wont get back
	 * @return cookie name like the name you give this method or null if not exists
	 */
	public static Cookie getCookie(HttpServletRequest httpServletRequest, String cookieName) {
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * get a string and try pares the string to ClientType if succeeded return the
	 * enum ClientType else throw CouponSystemException
	 * 
	 * @param type string you try to pares
	 * @return ClientType enum like the string
	 * @throws CouponSystemException when cen't pares the string to client type
	 */
	public static ClientType checkClientType(String type) throws CouponSystemException {
		try {
			// try parse string to clientType(enum)
			ClientType clientType = ClientType.valueOf(type);
			return clientType;
		} catch (Exception e) {
			// when the string illegal and impossible to parse the string
			throw new CouponSystemException("the type you insert is illegal", ErrorType.ILLEGAL_TYPE);
		}

	}

	/**
	 * when the client try active method he don't have a permission
	 * 
	 * @param methodException the method client try to active
	 * @param clientType      off the client try to active
	 * @param cookieIdUser    the id client try to active
	 * @return CouponSystemException
	 */
	public static CouponSystemException securityBreachException(String methodException, ClientType clientType,
			Cookie cookieIdUser) {
		return new CouponSystemException(methodException + cookieIdUser.getValue() + " user Type : " + clientType,
				ErrorType.SECURITY_BREACH_ACCESS_NOT_ALLOWED);

	}

}
