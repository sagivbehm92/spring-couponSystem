package core.dao.springData;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import core.dao.idao.ICompanyDAO;
import core.dao.idao.ICouponDAO;
import core.dao.idao.ICustomerDAO;
import core.enums.CouponType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;
import core.javaBeans.entity.Coupon;
import core.javaBeans.entity.Customer;
//@Repository
public class CouponDAO implements ICouponDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void postConstruct() {
		System.out.println(" from CouponDAO JPA postConstruct");
	}

	@PersistenceContext(unitName = "demo")
	private EntityManager entityManager;

	@Resource(name = "companyJpa")
	private ICompanyDAO companyDAO;
	@Resource(name = "customerJpa")
	private ICustomerDAO customerDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long create(Coupon coupon) throws CouponSystemException {
		entityManager.persist(coupon);
		return coupon.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(long idCoupon) throws CouponSystemException {
		Coupon coupon = entityManager.find(Coupon.class, idCoupon);
		coupon.getCustomers();
		entityManager.remove(coupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(Coupon coupon) throws CouponSystemException {
		entityManager.merge(coupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Coupon read(long idCoupon) throws CouponSystemException {
		return entityManager.find(Coupon.class, idCoupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean idExists(long IdBeans) throws CouponSystemException {
		Coupon coupon = entityManager.find(Coupon.class, IdBeans);
		if (coupon != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Coupon> getAll() throws CouponSystemException {
		System.out.println("DAO GetAllCoupons");
		TypedQuery<Coupon> query = entityManager.createQuery("SELECT e FROM Coupon e ", Coupon.class);
		List<Coupon> coupons = query.getResultList();
		System.out.println(coupons);
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeExpiredCoupons(Date date) throws CouponSystemException {
		String hql = "DELETE  FROM  Coupon e WHERE e.endDate<?";
		Query query = entityManager.createQuery(hql);
		query.setParameter(1, date, TemporalType.TIME);
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean titlelExists(String title) throws CouponSystemException {
		TypedQuery<Coupon> query = entityManager.createQuery("SELECT e FROM Coupon e WHERE e.title = ? ", Coupon.class);
		query.setParameter(1, title);
		try {
			Coupon coupon = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCustomerCouponsNotPurchase(long idCustomer) throws CouponSystemException {
		String sql = "SELECT * FROM coupon LEFT JOIN customer_coupon "
				+ "ON coupon.id=customer_coupon.coupon_id AND customer_coupon.customer_id = ? "
				+ " WHERE customer_coupon.customer_id is null";
		Query query = entityManager.createNativeQuery(sql, Coupon.class);
		query.setParameter(1, idCustomer);
		List<Coupon> coupons = (List<Coupon>) query.getResultList();
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void purchaseCoupon(long idCust, long idCoupon) throws CouponSystemException {
		Coupon coupon = read(idCoupon);
		Customer customer = customerDAO.read(idCust);
		coupon.getCustomers().add(customer);
		//		customer.getCoupons().add(coupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCustomerCoupons(long idCustomer) throws CouponSystemException {
		Customer customer = customerDAO.read(idCustomer);
		List<Coupon> coupons = customer.getCoupons();
		System.out.println(coupons);
		return coupons;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCustomerCouponsByType(CouponType type, long idCustomer) throws CouponSystemException {

		String hql = "SELECT coupons FROM Coupon AS coupons INNER JOIN coupons.customers AS customer WHERE customer.id = ? "
				+ "and coupons.type = ?";
		Query query = entityManager.createQuery(hql, Coupon.class);
		query.setParameter(1, idCustomer);
		query.setParameter(2, type);
		List<Coupon> coupons = query.getResultList();
		System.out.println("THE CUSTOMER ID : " + idCustomer + "   " + coupons);
		return coupons;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCustomerCouponsByPrice(double price, long idCustomer) throws CouponSystemException {
		String hql = "SELECT coupons FROM Coupon AS coupons INNER JOIN coupons.customers as customer WHERE customer.id = ? "
				+ "and coupons.price<= ?";
		Query query = entityManager.createQuery(hql);
		query.setParameter(1, idCustomer);
		query.setParameter(2, price);
		List<Coupon> coupons = query.getResultList();
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCompanyCoupons(long idCompany) throws CouponSystemException {
		Company company = entityManager.find(Company.class, idCompany);
		List<Coupon> coupons = company.getCoupons();
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCompanyCouponsByPrice(long idCompany, double price) throws CouponSystemException {
		String hql = "FROM Coupon as coupon WHERE coupon.company = ? AND coupon.price <= ?";
		TypedQuery<Coupon> query = entityManager.createQuery(hql, Coupon.class);
		Company company = companyDAO.read(idCompany);
		query.setParameter(1, company);
		query.setParameter(2, price);
		List<Coupon> coupons = query.getResultList();
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCompanyCouponsByType(CouponType type, long idCompany) throws CouponSystemException {
		String hql = "FROM Coupon as coupon WHERE coupon.company = ? AND coupon.type = ?";
		TypedQuery<Coupon> query = entityManager.createQuery(hql, Coupon.class);
		Company company = companyDAO.read(idCompany);
		query.setParameter(1, company);
		query.setParameter(2, type);
		List<Coupon> coupons = query.getResultList();
		return coupons;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Coupon> getCompanyCouponsByDate(Date date, long idCompany) throws CouponSystemException {
		String hql = "FROM Coupon as coupon WHERE coupon.company = ? AND coupon.endDate<= ? ";
		Query query = entityManager.createQuery(hql);
		Company company = companyDAO.read(idCompany);
		System.out.println(date);
		query.setParameter(1, company);
		query.setParameter(2, date);
		List<Coupon> coupons = query.getResultList();
		return coupons;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateAmount(long idCoupon, int amountDifferense) throws CouponSystemException {
		String sql = "UPDATE coupon SET amount=amount+?" + "  WHERE id=? AND amount+?>=0";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, amountDifferense);
		query.setParameter(2, idCoupon);
		query.setParameter(3, amountDifferense);
		return query.executeUpdate();
	}
}
