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
import core.javaBeans.entity.Company;
import core.logic.CompanyController;

@RestController
@RequestMapping("/company")
public class CompanyApi {
	@Autowired
	private CompanyController companyCntroller;

	public CompanyApi() {
	}

	@GetMapping
	@RequestMapping("/{idCompany}")
	public Company getCompany(@PathVariable long idCompany, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		//get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		//check the client type (only admin type have permission)
		if (clientType==ClientType.ADMIN) {
			return companyCntroller.getCompany(idCompany);
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		//when the client type not admin 
		throw UtilApi.securityBreachException("security breach on : getCompany user id : ", clientType, cookieIdUser);
	}

	@PostMapping
	public long createCompany(@RequestBody Company companyEntity, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		//get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest , "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		//check the client type (only admin type have permission)
		if (clientType==ClientType.ADMIN) {
			System.out.println(companyEntity);
			return companyCntroller.createCompany(companyEntity);
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest , "userId");
		//when the client type not admin 
		throw UtilApi.securityBreachException("security breach on : createCompany user id : ", clientType, cookieIdUser);
	}

	@DeleteMapping("/{idCompany}")
	public void removeCompany(@PathVariable long idCompany, HttpServletRequest httpServletRequest)
			throws CouponSystemException {
		//get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest , "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		if (clientType==ClientType.ADMIN) {
			//check the client type (only admin type have permission)
			companyCntroller.removeCompany(idCompany);
			return;
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		//when the client type not admin 
		throw UtilApi.securityBreachException("security breach on : removeCompany user id : ", clientType, cookieIdUser);
	}

	@PutMapping
	public void updateCompany( HttpServletRequest httpServletRequest,@RequestBody  Company companyEntity)
			throws CouponSystemException {
		//get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		//check the client type (admin and company type have permission)
		if (clientType==ClientType.ADMIN || clientType==ClientType.COMPANY) {
			companyCntroller.updateCompany(companyEntity, clientType);
			return;
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		//when the type not matching to admin or company
		throw UtilApi.securityBreachException("security breach on : updateCompany user id : ", clientType, cookieIdUser);
	}

	@GetMapping
	@RequestMapping("/getAll")
	public List<Company> getAllCompanies(HttpServletRequest httpServletRequest) throws CouponSystemException {
		//get cookie name client type
		Cookie cookie = UtilApi.getCookie(httpServletRequest, "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookie.getValue());
		//check the client type (only admin type have permission)
		if (clientType==ClientType.ADMIN) {
			return companyCntroller.getAllCompanies();
		}
		Cookie cookieIdUser = UtilApi.getCookie(httpServletRequest, "userId");
		//when the type not matching to admin
		throw UtilApi.securityBreachException("security breach on : getAllCompanies user id : ", clientType, cookieIdUser);
	}

	@PutMapping
	@RequestMapping("/byCompanyPassword")
	public void updatePasswordByCompany( HttpServletRequest httpServletRequest,@RequestBody  PasswordBean passwordBean)
			throws CouponSystemException {
		Cookie cookieId = UtilApi.getCookie(httpServletRequest, "userId");
		//get cookie company id and parse too long
		long cookieCompanyId = Long.parseLong(cookieId.getValue());
		
		cookieId = UtilApi.getCookie(httpServletRequest, "type");
		//parse cookie string too client type
		ClientType clientType = UtilApi.checkClientType(cookieId.getValue());
		//check the client type (only company type have permission)
		if (clientType==ClientType.COMPANY) {
			companyCntroller.updatePasswordByCompany(cookieCompanyId, passwordBean, clientType);
			return;
		}
		//when the type not matching to company 
		throw UtilApi.securityBreachException("security breach on : byCompanyPassword user id : ", clientType, cookieId);
	}

}
