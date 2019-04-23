package core.dao.springData;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICompanyDAO;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;

//@Repository
public class CompanyDAO implements ICompanyDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void postConstruct() {
		System.out.println(" from CompanyDAO JPA postConstruct");
	}

	@PersistenceContext(unitName = "demo")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long create(Company companyEntity) throws CouponSystemException {
		entityManager.persist(companyEntity);
		return companyEntity.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(long companyEntityId) throws CouponSystemException {
		Company companyEntity = entityManager.find(Company.class, companyEntityId);
		entityManager.remove(companyEntity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Company companyEntity) throws CouponSystemException {
		entityManager.merge(companyEntity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Company read(long companyEntityId) throws CouponSystemException {
		Company companyEntity = entityManager.find(Company.class, companyEntityId);
		return companyEntity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean idExists(long IdBeans) throws CouponSystemException {
		Company companyEntity = entityManager.find(Company.class, IdBeans);
		if (companyEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Company> getAll() throws CouponSystemException {
		TypedQuery<Company> query = entityManager.createQuery("SELECT e FROM Company e", Company.class);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean nameExists(String companyName) throws CouponSystemException {
		TypedQuery<Company> query = entityManager.createQuery("SELECT e FROM Company e WHERE e.name = ? ",
				Company.class);
		query.setParameter(1, companyName);
		try {
			Company companyEntity = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Long login(String user, String password) throws CouponSystemException {
		TypedQuery<Company> query = entityManager
				.createQuery("SELECT e FROM Company e WHERE e.name = ? AND e.password = ?", Company.class);
		query.setParameter(1, user);
		query.setParameter(2, password);

		try {
			Company companyEntity = query.getSingleResult();
			return companyEntity.getId();
		} catch (NoResultException e) {
			return null;
		}

	}
}
