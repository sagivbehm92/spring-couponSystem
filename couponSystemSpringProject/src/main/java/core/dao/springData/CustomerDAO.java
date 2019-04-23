package core.dao.springData;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICustomerDAO;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Customer;

//@Repository
public class CustomerDAO implements ICustomerDAO {

	@PostConstruct
	public void postConstruct() {
		System.out.println(" from CustomerDAO JPA postConstruct");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "demo")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long create(Customer customerEntity) throws CouponSystemException {
		entityManager.persist(customerEntity);
		return customerEntity.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(long customerEntityId) throws CouponSystemException {
		Customer customerEntity = entityManager.find(Customer.class, customerEntityId);
		entityManager.remove(customerEntity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Customer customerEntity) throws CouponSystemException {
		entityManager.merge(customerEntity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Customer read(long customerEntityId) throws CouponSystemException {
		Customer customerEntity = entityManager.find(Customer.class, customerEntityId);
		return customerEntity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean idExists(long customerEntityId) throws CouponSystemException {
		Customer companyEntity = entityManager.find(Customer.class, customerEntityId);
		if (companyEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Customer> getAll() throws CouponSystemException {
		TypedQuery<Customer> query = entityManager.createQuery("SELECT e FROM Customer e", Customer.class);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean nameExists(String companyName) throws CouponSystemException {
		TypedQuery<Customer> query = entityManager.createQuery("SELECT e FROM Customer e WHERE e.name = ? ",
				Customer.class);
		query.setParameter(1, companyName);
		try {
			Customer customerEntity = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Long login(String user, String password) throws CouponSystemException {
		TypedQuery<Customer> query = entityManager
				.createQuery("SELECT e FROM Customer e WHERE e.name = ? AND e.password = ?", Customer.class);
		query.setParameter(1, user);
		query.setParameter(2, password);

		try {
			Customer customerEntity = query.getSingleResult();
			return customerEntity.getId();
		} catch (NoResultException e) {
			return null;
		}

	}

}
