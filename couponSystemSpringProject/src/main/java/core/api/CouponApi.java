package core.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.enums.ClientType;
import core.enums.CouponType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Coupon;
import core.logic.CouponController;

@RestController
@RequestMapping("/coupon")
public class CouponApi {
	@Autowired
	private CouponController couponController;

	public CouponApi() {
	}

	@GetMapping("/{idCoupon}")
//	@RequestMapping("/{idCoupon}")
	public Coupon getCoupon(@PathVariable long idCoupon) throws CouponSystemException {
		return couponController.getCoupon(idCoupon);
	}

	@GetMapping
	@RequestMapping("/getall")
	public List<Coupon> getAllCoupon(HttpServletResponse httpServletResponse) throws CouponSystemException {
		List<Coupon> coupons= couponController.getAllCoupon();
		System.out.println(coupons);
		return coupons;
	}
	// customer
	// =========================================

	@GetMapping
	@RequestMapping("/couponNotPurchase")
	public List<Coupon> getCustomerCouponsNotPurchase(HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			long cookieCustomerId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCustomerCouponsNotPurchase(cookieCustomerId);
		}
		// when the client type not customer
		throw UtilApi.securityBreachException("security breach on : getCustomerCouponsNotPurchase user id : ",
				clientType, cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/customerCoupons")
	public List<Coupon> getCustomerCoupons(HttpServletRequest httpServletRequest) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			long cookieCustomerId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCustomerCoupons(cookieCustomerId);
		}
		// when the client type not customer
		throw UtilApi.securityBreachException("security breach on : getCustomerCoupons user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/customerCouponsByType")
	public List<Coupon> getCustomerCouponsByType(HttpServletRequest httpServletRequest,
			@RequestParam("type") String type) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			long cookieCustomerId = Long.parseLong(cookieIdUser.getValue());
			CouponType couponType = UtilApi.checkCouponType(type);
			return couponController.getCustomerCouponsByType(couponType, cookieCustomerId);
		}
		throw UtilApi.securityBreachException("security breach on : getCustomerCouponsByType user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/customerCouponsByPrice")
	public List<Coupon> getCustomerCouponsByPrice(HttpServletRequest httpServletRequest,
			@RequestParam("price") double price) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			long cookieCustomerId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCustomerCouponsByPrice(price, cookieCustomerId);
		}
		throw UtilApi.securityBreachException("security breach on : getCustomerCouponsByPrice user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/purchaseCoupon/{idCoupon}")
	public String purchaseCoupon(HttpServletRequest httpServletRequest, @PathVariable("idCoupon") long idCoupon)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			long cookieCustomerId = Long.parseLong(cookieIdUser.getValue());
			couponController.purchaseCoupon(cookieCustomerId, idCoupon);
			return "all ok have fun with this coupon";
		}
		throw UtilApi.securityBreachException("security breach on : purchaseCoupon user id : ", clientType,
				cookieIdUser);
	}

	// company coupon
	// =========================================================
	@PostMapping
	@RequestMapping("/create")
	public long createCoupon(@RequestBody Coupon coupon, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only company type have permission)
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			return couponController.createCoupon(coupon, cookieCompanyId);
		}
		throw UtilApi.securityBreachException("security breach on : createCoupon user id : ", clientType, cookieIdUser);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.COMPANY) {
			System.out.println(coupon);
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			couponController.updateCoupon(coupon, cookieCompanyId);
			return;
		}
		throw UtilApi.securityBreachException("security breach on : updateCoupon user id : ", clientType, cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/companyCoupons")
	public List<Coupon> getCompanyCoupons(HttpServletRequest httpServletRequest) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCompanyCoupons(cookieCompanyId);
		}
		throw UtilApi.securityBreachException("security breach on : getCompanyCoupons user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/companyCouponsByPrice")
	public List<Coupon> getCompanyCouponsByPrice(HttpServletRequest httpServletRequest,
			@RequestParam("price") double price) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCompanyCouponsByPrice(cookieCompanyId, price);
		}
		throw UtilApi.securityBreachException("security breach on : getCompanyCouponsByPrice user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/companyCouponsByType")
	public List<Coupon> getCompanyCouponsByType(@RequestParam("type") String type,
			HttpServletRequest httpServletRequest) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			CouponType couponType = UtilApi.checkCouponType(type);
			return couponController.getCompanyCouponsByType(couponType, cookieCompanyId);
		}
		throw UtilApi.securityBreachException("security breach on : getCompanyCouponsByType user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/companyCouponsByDate")
	public List<Coupon> getCompanyCouponsByDate(@RequestParam("date") long date, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// get cookie cookieIdUser
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// check the client type (only customer type have permission)
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			return couponController.getCompanyCouponsByDate(new Date(date), cookieCompanyId);
		}
		throw UtilApi.securityBreachException("security breach on : getCompanyCouponsByDate user id : ", clientType,
				cookieIdUser);
	}

	/**
	 * when the company want to remove one coupon from list of coupon this company
	 * create
	 * 
	 * @param idCoupon  the id coupon the company want to remove
	 * @param idCompany the id off the company want to remove the coupon
	 * @throws CouponSystemException
	 */
	@DeleteMapping("/{idCoupon}")
//	@RequestMapping("/{idcoupon}")
	public void removeCompanyCouponByCoupon(@PathVariable long idCoupon, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		if (clientType == ClientType.COMPANY) {
			long cookieCompanyId = Long.parseLong(cookieIdUser.getValue());
			couponController.removeCompanyCouponByCoupon(idCoupon, cookieCompanyId);
			return;
		}
		throw UtilApi.securityBreachException("security breach on : removeCompanyCouponByCoupon user id : ", clientType,
				cookieIdUser);
	}
}
