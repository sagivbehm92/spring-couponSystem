package core.logic;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICouponDAO;
import core.dao.idao.ICustomerDAO;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.PasswordBean;
import core.javaBeans.entity.Coupon;
import core.javaBeans.entity.Customer;

@Service
public class CustomerController {
	
	@Resource(name = "customerJpa")
	private ICustomerDAO customerDAO;
	@Resource(name = "couponJpa")
	private ICouponDAO couponDAO;

	public CustomerController() {
	}

	public long createCustomer(Customer customer) throws CouponSystemException {
		ValidateBeansUtil.customerValidate(customer);
		// Checking if the customer name exists on data base
		if (customerDAO.nameExists(customer.getName())) {
			throw new CouponSystemException(ErrorType.EXISTS_NAME);
		}
		// create the customer
		return customerDAO.create(customer);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeCustomer(long idCustomer) throws CouponSystemException {
		// Checking if the company id exists on data base
		if (customerDAO.idExists(idCustomer)) {
			// remove all coupon this customer have on customer coupon
			Customer customer=customerDAO.read(idCustomer);
			List<Coupon>couponsCustomer = customer.getCoupons();
			for (Coupon coupon : couponsCustomer) {
				coupon.getCustomers().remove(customer);
				couponDAO.update(coupon);
			}
			// remove the customer from data base
			customerDAO.remove(idCustomer);
		} else {
			throw new CouponSystemException(ErrorType.ID_NOT_EXISTS);
		}
	}

	public void updateCustomer(Customer customerEntity) throws CouponSystemException {
		Customer updateCustomer = customerDAO.read(customerEntity.getId());
		if (updateCustomer != null) {
			// update the original bean
			updateCustomer.setPassword(customerEntity.getPassword());
			// send updated original bean to validate
			ValidateBeansUtil.customerValidate(updateCustomer);
			// send updated original bean back to data base
			customerDAO.update(updateCustomer);
		} else {
			// if the customer not exists on data base
			throw new CouponSystemException(ErrorType.CUSTOMER_NOT_EXISTS);
		}
	}

	public void updateCustomerByCustomer(PasswordBean passwordBean, long cookieCustomerId)
			throws CouponSystemException {
		// check if the id bean equal to cookieCustomerId
		if (passwordBean.geÃ¿tId() == cookieCustomerId) {
			// get the customer beans from data base
			Customer updateCustomer = customerDAO.read(passwordBean.geÃ¿tId());
			// check if the password from password bean not macing the password no date base
			if (updateCustomer.getPassword().equals(passwordBean.getPassword())) {
				// check validity of new password
				ValidateBeansUtil.validatePassword(passwordBean.getNewPassword());
				// update the customer beans from data base after validate the password
				updateCustomer.setPassword(passwordBean.getNewPassword());
				// send updated original bean back to data base
				customerDAO.update(updateCustomer);
			} else {
				// when the password the client give not macing the password no date base
				throw new CouponSystemException(ErrorType.UPDATE_BY_CUSTOMER_PASSWORD_NOT_MATCHING);
			}
		} else {
			// when the customer id not exists on data base
			throw new CouponSystemException(ErrorType.UPDATE_BY_CUSTOMER);
		}
	}

	public Customer getCustomer(long idCustomer) throws CouponSystemException {
		Customer customerEntity = customerDAO.read(idCustomer);
		if (customerEntity != null) {
			return customerEntity;
		} else {
			// when the customer id not exists on data base
			throw new CouponSystemException(ErrorType.CUSTOMER_NOT_EXISTS);
		}
	}

	public List<Customer> getAllCustomer() throws CouponSystemException {
		// get all the customer from data base and send
		List<Customer> customers = customerDAO.getAll();
		return customers;
	}

	public Long login(String user, String password) throws CouponSystemException {
		// send the userName and password to data base and check if matching on data
		// base return the User id
		return customerDAO.login(user, password);
	}

}
