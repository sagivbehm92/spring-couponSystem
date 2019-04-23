package core.logic;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICompanyDAO;
import core.dao.idao.ICouponDAO;
import core.enums.CouponType;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;
import core.javaBeans.entity.Coupon;

@Service
public class CouponController {

	@Resource(name = "couponJpa")
	private ICouponDAO couponDAO;
	@Resource(name = "companyJpa")
	private ICompanyDAO companyDAO;

	public CouponController() {

	}

	@Transactional
	public void updateCoupon(Coupon coupon, long cookieCompanyId) throws CouponSystemException {
		// checking the validate of this coupon
		ValidateBeansUtil.couponValidate(coupon);
		// read the coupon from data base with id
		Coupon updateCoupon = couponDAO.read(coupon.getId());

		// checking if exists
		if (updateCoupon != null) {
			if (updateCoupon.getCompany().getId() != cookieCompanyId) {
				throw new CouponSystemException(ErrorType.INVALIDATE_ID_COUPON_BY_COMPANY);
			}
			// checking the couponUpdate if equals coupon
			if (updateCoupon.equals(coupon)) {
				// update the original beans
				updateCoupon.setEndDate(coupon.getEndDate());
				updateCoupon.setPrice(coupon.getPrice());
				// send the original beans to data base
				couponDAO.update(updateCoupon);
				return;
			} else {
				// if the coupon not equals
				throw new CouponSystemException(ErrorType.UPDATE_COUPON_NOT_MATCHING);
			}
		}
		// if the coupon id not exists on data base
		throw new CouponSystemException(ErrorType.COUPON_NOT_EXISTS);
	}

	public Coupon getCoupon(long idCoupon) throws CouponSystemException {
		// read the coupon from data base with id
		Coupon coupon = couponDAO.read(idCoupon);
		if (coupon == null) {
			// if the coupon id not exists on data base
			throw new CouponSystemException(ErrorType.COUPON_NOT_EXISTS);
		} else {
			return coupon;
		}
	}

	public List<Coupon> getAllCoupon() throws CouponSystemException {
		// read all coupon from data base
		return couponDAO.getAll();
	}

	public List<Coupon> getCustomerCouponsNotPurchase(long idCustomer) throws CouponSystemException {
		// read all coupon from data base of this customer can buy
		return couponDAO.getCustomerCouponsNotPurchase(idCustomer);
	}

	// customer coupon
	// =========================================================

	public List<Coupon> getCustomerCoupons(long idCustomer) throws CouponSystemException {
		// read all coupon from data base of this customer already buy
		return couponDAO.getCustomerCoupons(idCustomer);
	}

	public List<Coupon> getCustomerCouponsByType(CouponType type, long idCustomer) throws CouponSystemException {
		// read all coupon from data base of this customer already buy where the Type
		// coupon matching to the type
		return couponDAO.getCustomerCouponsByType(type, idCustomer);
	}

	public List<Coupon> getCustomerCouponsByPrice(double price, long idCustomer) throws CouponSystemException {
		// read all coupon from data base of this customer already buy where the price
		// coupon equal or less this price
		return couponDAO.getCustomerCouponsByPrice(price, idCustomer);
	}

	public void purchaseCoupon(long idCust, long idCoupon) throws CouponSystemException {
		// read the coupon from data base with id
		Coupon coupon = couponDAO.read(idCoupon);
		// checking if the coupon exists on data base
		if (coupon != null) {
			// first we 'save' the coupon for this customer
			int numberOfUpdate = couponDAO.updateAmount(idCoupon, -1);
			if (numberOfUpdate != 0) {
				// read all customer coupon this customer have on data base
				List<Coupon> customerCupons = couponDAO.getCustomerCoupons(idCust);
				// checking if the this coupon contains on list customer coupon
				if (customerCupons.contains(coupon)) {
					// if the customer have already this coupon we change back the amount
					couponDAO.updateAmount(idCoupon, 1);
					// and we send exception to the customer
					throw new CouponSystemException(ErrorType.PURCHASE_COUPON);
				}
				// else we update the customer table
				couponDAO.purchaseCoupon(idCust, idCoupon);
			} else
				throw new CouponSystemException(ErrorType.OUT_OFF_STOCK);
		} else {
			// if the coupon id no exists not need to updateAmount if the coupon not exists
			throw new CouponSystemException(ErrorType.COUPON_NOT_EXISTS);
		}
	}

	// company coupon
	// =========================================================
	@Transactional
	public long createCoupon(Coupon coupon, long cookieCompanyId) throws CouponSystemException {
		// validate the new coupon
		ValidateBeansUtil.couponValidate(coupon);
		// checking if the title not already exists
		if (couponDAO.titlelExists(coupon.getTitle())) {
			throw new CouponSystemException(ErrorType.EXISTS_TITLE);
		}
		// get the specific company try to create the this coupon
		Company company = companyDAO.read(cookieCompanyId);
		// add the company object to to coupon (foreign key)
		coupon.setCompany(company);
		// create the coupon and return the coupon id from data base
		return couponDAO.create(coupon);
	}

	public List<Coupon> getCompanyCoupons(long idCompany) throws CouponSystemException {
		// read all coupons this company have on data base
		return couponDAO.getCompanyCoupons(idCompany);
	}

	public List<Coupon> getCompanyCouponsByPrice(long idCompany, double price) throws CouponSystemException {
		// read all coupons this company have on this price or less
		return couponDAO.getCompanyCouponsByPrice(idCompany, price);
	}

	public List<Coupon> getCompanyCouponsByType(CouponType type, long idCompany) throws CouponSystemException {
		// read all coupons this company have on this type
		return couponDAO.getCompanyCouponsByType(type, idCompany);
	}

	public List<Coupon> getCompanyCouponsByDate(Date date, long idCompany) throws CouponSystemException {
		// read all coupons this company have on this date or before
		return couponDAO.getCompanyCouponsByDate(date, idCompany);
	}

	public void removeCompanyCouponByCoupon(long idCoupon, long idCompany) throws CouponSystemException {
		// read the coupon from data base whit id
		Coupon coupon = couponDAO.read(idCoupon);

		// checking if the coupon different from null
		if (coupon != null) {
			// read all coupons this company have
			List<Coupon> companyCoupons = couponDAO.getCompanyCoupons(idCompany);
			// checking if the coupon contains of this company (if this company create the
			// coupon)
			if (companyCoupons.contains(coupon)) {
				// delete the coupon from coupon
				couponDAO.remove(coupon.getId());
			} else {
				// when this company don't have this coupon
				throw new CouponSystemException("the company id : " + idCompany,
						ErrorType.UPDATE_OR_REMOVE_BY_COMPANY_NOT_CREATE_THIS_COUPON);
			}
		} else {
			// when the coupon not exists
			throw new CouponSystemException(ErrorType.ID_NOT_EXISTS);
		}
	}
}
