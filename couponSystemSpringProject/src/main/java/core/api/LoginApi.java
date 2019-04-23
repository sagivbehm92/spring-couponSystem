package core.api;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.enums.ClientType;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.UserLogin;
import core.logic.CompanyController;
import core.logic.CustomerController;

@RestController
@RequestMapping("")
public class LoginApi {

	@Autowired
	CompanyController companyController;

	@Autowired
	CustomerController customerController;

	// the client get hare when client first loud web page and rememberMe = true
	@GetMapping
	@RequestMapping("/checklogin")
	public UserLogin getDetails(HttpServletRequest httpServletRequest) throws CouponSystemException {
		Cookie[] cookies = httpServletRequest.getCookies();
		UserLogin userLogin = new UserLogin();
		if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("type")) {
					cookie.setDomain("localhost:8080/");
					userLogin.setType(cookie.getValue());
					continue;
				}
				if (cookie.getName().equals("userId")) {
					userLogin.setId(Long.parseLong(cookie.getValue()));
					continue;
				}
				if (cookie.getName().equals("userName")) {
					userLogin.setName(cookie.getValue());
					continue;
				}
			}
			return userLogin;
		}
		throw new CouponSystemException("this client try to reconnect befor login ", ErrorType.CHECK_LOGIN_ERROR);
	}

	@PostMapping
	@RequestMapping("/login")
	public long login(@RequestBody UserLogin userLogin, HttpServletResponse httpServletResponse,
			HttpServletRequest httpServletRequest) throws CouponSystemException, SQLException {
		// parse userLogin.type string too client type
		System.out.println(userLogin);
		ClientType clientType = UtilApi.checkClientType(userLogin.getType());
		switch (clientType) {
		case ADMIN:
			if (userLogin.getName().equals("admin") && userLogin.getPassword().equals("1234")) {
				userLogin.setId((long) 777);
				sendCookie(userLogin, httpServletResponse);
				return userLogin.getId();

			} else {
				throw new CouponSystemException(ErrorType.LOGIN);
			}

		case COMPANY:
			userLogin.setId(companyController.login(userLogin.getName(), userLogin.getPassword()));
			if (userLogin.getId() != null) {
				sendCookie(userLogin, httpServletResponse);
				return userLogin.getId();
			} else {
				throw new CouponSystemException(ErrorType.LOGIN);
			}
		case CUSTOMER:
			userLogin.setId(customerController.login(userLogin.getName(), userLogin.getPassword()));
			if (userLogin.getId() != null) {
				sendCookie(userLogin, httpServletResponse);
				return userLogin.getId();
			} else {
				throw new CouponSystemException(ErrorType.LOGIN);
			}

		default:
			throw new CouponSystemException(ErrorType.LOGIN_ERROR_TYPE);
		}

	}

	@DeleteMapping
	@RequestMapping("/logout")
	public void deleteLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Cookie[] cookies = httpServletRequest.getCookies();
		// delete all the cookie this client have
		if (cookies != null) {
			System.out.println("client logout : ");
			for (Cookie cookie : cookies) {
				System.out.println("the cookie name : " + cookie.getName() + " the value : " + cookie.getValue());
				cookie.setMaxAge(0);
				httpServletResponse.addCookie(cookie);
			}
		}

	}

	private void sendCookie(UserLogin userLogin, HttpServletResponse httpServletResponse) {

		Cookie cookie;
		// if remember me false cookie value = session
		if (userLogin.isRememberMe() == false) {
			System.out.println("from RememberMe : false");
			cookie = new Cookie("userId", String.valueOf(userLogin.getId()));
			cookie.setMaxAge(UtilApi.COOKIE_SESSION_TIME);
			// cookie.setPath("");
			httpServletResponse.addCookie(cookie);
			cookie = new Cookie("type", userLogin.getType());
			cookie.setMaxAge(UtilApi.COOKIE_SESSION_TIME);
			httpServletResponse.addCookie(cookie);
			cookie = new Cookie("userName", userLogin.getName());
			cookie.setMaxAge(UtilApi.COOKIE_SESSION_TIME);
			httpServletResponse.addCookie(cookie);
			return;

		} else {
			// if remember me true cookie value = max integer
			System.out.println("from RememberMe : true");
			cookie = new Cookie("userId", String.valueOf(userLogin.getId()));
			cookie.setMaxAge(UtilApi.COOKIE_MAX_TIME);
			httpServletResponse.addCookie(cookie);
			cookie = new Cookie("type", userLogin.getType());
			cookie.setMaxAge(UtilApi.COOKIE_MAX_TIME);
			httpServletResponse.addCookie(cookie);
			cookie = new Cookie("userName", userLogin.getName());
			cookie.setMaxAge(UtilApi.COOKIE_MAX_TIME);
			httpServletResponse.addCookie(cookie);
			return;

		}

	}

}
