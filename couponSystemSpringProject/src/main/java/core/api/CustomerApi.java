package core.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.enums.ClientType;
import core.exception.CouponSystemException;
import core.javaBeans.PasswordBean;
import core.javaBeans.entity.Customer;
import core.logic.CustomerController;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

	@Autowired
	private CustomerController customerController;

	public CustomerApi() {
	}

	@GetMapping("/{idCustomer}")
	public Customer getCustomer(@PathVariable long idCustomer, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only admin type have permission)
		if (clientType == ClientType.ADMIN) {
			return customerController.getCustomer(idCustomer);
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not admin
		throw UtilApi.securityBreachException("security breach on : getCustomer user id : ", clientType, cookieIdUser);
	}

	@PostMapping
	public long createCustomer(@RequestBody Customer customerEntity, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only admin type have permission)
		if (clientType == ClientType.ADMIN) {
			return customerController.createCustomer(customerEntity);
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not admin
		throw UtilApi.securityBreachException("security breach on : createCustomer user id : ", clientType,
				cookieIdUser);
	}

	//
	@DeleteMapping("/{idCustomer}")
	public void removeCustomer(@PathVariable long idCustomer, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only admin type have permission)
		if (clientType == ClientType.ADMIN) {
			customerController.removeCustomer(idCustomer);
			return;
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not admin
		throw UtilApi.securityBreachException("security breach on : removeCustomer user id : ", clientType,
				cookieIdUser);
	}

	@PutMapping
	public void updateCustomer(@RequestBody Customer customerEntity, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only admin type have permission)
		if (clientType == ClientType.ADMIN) {
			customerController.updateCustomer(customerEntity);
			return;
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not admin
		throw UtilApi.securityBreachException("security breach on : updateCustomer user id : ", clientType,
				cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/getAll")
	public List<Customer> getAllCustomer(HttpServletRequest httpServletRequest) throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only admin type have permission)
		if (clientType == ClientType.ADMIN) {
			return customerController.getAllCustomer();
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not admin
		throw UtilApi.securityBreachException("security breach on : getAllCustomer user id : ", clientType,
				cookieIdUser);
	}

	@PutMapping
	@RequestMapping("/byCustomer")
	public void updateCustomerByCustomer(@RequestBody PasswordBean passwordBean, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		// get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		// parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		// check the client type (only customer type have permission)
		if (clientType == ClientType.CUSTOMER) {
			cookie = UtilApi.getCookie(httpServletRequest, "userId");
			long cookieCustomerId = Long.parseLong(cookie.getValue());
			customerController.updateCustomerByCustomer(passwordBean, cookieCustomerId);
			return;
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		// when the client type not customer
		throw UtilApi.securityBreachException("security breach on : updateCustomerByCustomer user id : ", clientType,
				cookieIdUser);
	}

}
