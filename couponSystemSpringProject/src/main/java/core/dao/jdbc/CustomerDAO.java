package core.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import java.sql.Statement;

import core.couponSystemUtil.ConnectionPool;
import core.dao.idao.ICustomerDAO;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Customer;


//@Repository
public class CustomerDAO implements ICustomerDAO {
	
	@PostConstruct
	public void postConstruct () {
		System.out.println(" from CustomerDAO JDBC postConstruct");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public long create(Customer customer) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO Customer(cust_name , password) VALUES(?,?)";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getLong(1);
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}

	}

	@Override
	public void remove(long idCustomer) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM customer WHERE id=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idCustomer);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);

		} finally {
			DAOUtil.closeResources(preparedStatement);
			ConnectionPool.getinstance().returnConnection(connection);
		}

	}

	@Override
	public void update(Customer customer) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE customer SET cust_name=? , password=?  WHERE id = ?";
		Connection con = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement);
			ConnectionPool.getinstance().returnConnection(con);
		}

	}

	@Override
	public Customer read(long idCustomer) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM customer  WHERE id=?";
		Customer customer = null;
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idCustomer);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customer = new Customer();
				customer.setId(resultSet.getLong("id"));
				customer.setName(resultSet.getString("cust_name"));
				customer.setPassword(resultSet.getString("password"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}

		return customer;
	}

	/**
	 * this method get all customer from date base on table customer and return list
	 * with all customers Details throw went data base is shutdown
	 * 
	 * @return list of customer from SQL data base customer table
	 * @throws CouponSystemException When there is a problem with a database
	 */
	@Override
	public List<Customer> getAll() throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM customer ";
		List<Customer> customers = new ArrayList<>();
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getLong("id"));
				customer.setName(resultSet.getString("cust_name"));
				customer.setPassword(resultSet.getString("password"));
				customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}
	}

	@Override
	public boolean idExists(long idCustomer) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM customer  WHERE id=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idCustomer);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}
	}

	@Override
	public boolean nameExists(String customerName) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM customer WHERE cust_name=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}

	}

	@Override
	public Long login(String user, String password) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT id FROM customer WHERE cust_name=? AND password=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getLong("id");
			}
			return null;
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}

	}

}
