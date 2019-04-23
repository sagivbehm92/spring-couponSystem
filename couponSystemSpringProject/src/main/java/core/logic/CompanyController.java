package core.logic;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICompanyDAO;
import core.dao.idao.ICouponDAO;
import core.enums.ClientType;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.PasswordBean;
import core.javaBeans.entity.Company;

@Service
public class CompanyController {

//	private ICompanyDAO companyDAO;
	@Resource(name = "companyJpa")
	private ICompanyDAO companyDAO;
	@Resource(name = "couponJpa")
	private ICouponDAO couponDAO;

	public CompanyController() {
		
	}

	public long createCompany(Company company) throws CouponSystemException {
		ValidateBeansUtil.companyValidate(company);
		// Checking if the company name exists on data base
		if (companyDAO.nameExists(company.getName())) {
			throw new CouponSystemException(ErrorType.EXISTS_NAME);
		}
		// create the company
		return companyDAO.create(company);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeCompany(long idCompany) throws CouponSystemException {
		// Checking if the company id exists on data base
		if (companyDAO.idExists(idCompany)) {
			// remove all coupon this company have on customer coupon
//			couponDAO.removeCustomerCouponByCompany(idCompany);
			// remove all coupon this company have on coupon
//			couponDAO.removeCompanyCouponsBycompany(idCompany);
			// remove the company from data base
			companyDAO.remove(idCompany);
		} else {
			// if the id not exists
			throw new CouponSystemException(ErrorType.ID_NOT_EXISTS);
		}
	}

	public void updateCompany(Company companyEntity, ClientType clientType) throws CouponSystemException {
		Company updateCompany = companyDAO.read(companyEntity.getId());
		if (updateCompany != null) {
			if (clientType.equals(ClientType.ADMIN)) {
				// update the original bean
				updateCompany.setPassword(companyEntity.getPassword());
				updateCompany.setEmail(companyEntity.getEmail());
				// send updated original bean to validate
				ValidateBeansUtil.companyValidate(updateCompany);
				// send updated original bean back to data base
				companyDAO.update(updateCompany);
			} else if (clientType.equals(ClientType.COMPANY)) {
				updateCompany.setEmail(companyEntity.getEmail());
				// send updated original bean to validate
				ValidateBeansUtil.companyValidate(updateCompany);
				// send updated original bean back to data base
				companyDAO.update(updateCompany);
			}
		} else {
			// if the company not exists on data base
			throw new CouponSystemException(ErrorType.COMPANY_NOT_EXISTS);
		}
	}

	public void updatePasswordByCompany(long cookieCompanyId, PasswordBean passwordBean, ClientType clientType)
			throws CouponSystemException {
		// check if the id bean equal to cookieCompanyId
		if (passwordBean.geÃ¿tId() == cookieCompanyId) {
			// get the company beans from data base
			Company updateCompany = companyDAO.read(cookieCompanyId);
			if (updateCompany != null) {
				// check if the password from password bean not macing the password no date base
				if (updateCompany.getPassword().equals(passwordBean.getPassword())) {
					// check validity of new password
					ValidateBeansUtil.validatePassword(passwordBean.getNewPassword());
					// update the customer beans from data base after validate the password
					updateCompany.setPassword(passwordBean.getNewPassword());
					// send updated original bean back to data base
					companyDAO.update(updateCompany);
				} else {
					// when the password the client give not macing the password no date base
					throw new CouponSystemException(ErrorType.UPDATE_BY_COMPANY_PASSWORD_NOT_MATCHING);
				}
			} else {
				// when the company id not exists on data base
				throw new CouponSystemException("the id you insert not exists", ErrorType.COMPANY_NOT_EXISTS);
			}
		} else {
			// when company try to change password of anther company
			throw new CouponSystemException(ErrorType.UPDATE_BY_COMPANY);
		}
	}

	public Company getCompany(long companyId) throws CouponSystemException {
		Company company = companyDAO.read(companyId);
		if (company != null) {
			return company;
		} else {
			// when the company id not exists on data base
			throw new CouponSystemException(ErrorType.COMPANY_NOT_EXISTS);
		}
	}

	public List<Company> getAllCompanies() throws CouponSystemException {
		// get all the company from data base and send
		return companyDAO.getAll();
	}

	public Long login(String user, String password) throws CouponSystemException {
		// send the userName and password to data base and check if matching on data
		// base return the User id
		return companyDAO.login(user, password);
	}

}
