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
import core.dao.idao.ICompanyDAO;
import core.enums.ErrorType;
import core.exception.CouponSystemException;
import core.javaBeans.entity.Company;

/**
 * @author user1
 *
 */
//@Repository
public class CompanyDAO implements ICompanyDAO {
	
	
	@PostConstruct
	public void postConstruct () {
		System.out.println(" from CompanyDAO JDBC postConstruct");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public long create(Company company) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO company(comp_name , password , email) VALUES(?,?,?)";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
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
	public void remove(long idCompany) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		Connection connection = ConnectionPool.getinstance().getConnection();
		String sql = "DELETE FROM company WHERE id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idCompany);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement);
			ConnectionPool.getinstance().returnConnection(connection);
		}
	}

	@Override
	public void update(Company company) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		Connection connection = ConnectionPool.getinstance().getConnection();
		String sql = "UPDATE company SET comp_name=? , password=? , email=? WHERE id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);

		} finally {
			DAOUtil.closeResources(preparedStatement);
			ConnectionPool.getinstance().returnConnection(connection);
		}
	}

	@Override
	public Company read(long companyId) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = ConnectionPool.getinstance().getConnection();
		String sql = "SELECT * FROM company WHERE id=?";
		Company company = null;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				company = new Company();
				company.setId(resultSet.getLong("id"));
				company.setName(resultSet.getString("comp_name"));
				company.setPassword(resultSet.getString("password"));
				company.setEmail(resultSet.getString("email"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(con);
		}
		return company;
	}

	/**
	 * this method get all Company from date base on table Company and return list
	 * with all Companies Details throw went data base is shutdown
	 * 
	 * @return list of Company from SQL data base Company table
	 * @throws CouponSystemException When there is a problem with a database
	 * @throws SQLException 
	 */
	@Override
	public List<Company> getAll() throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = ConnectionPool.getinstance().getConnection();
		String sql = "SELECT * FROM company";
		List<Company> companies = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getLong("id"));
				company.setName(resultSet.getString("comp_name"));
				company.setPassword(resultSet.getString("password"));
				company.setEmail(resultSet.getString("email"));
				companies.add(company);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
		} finally {
			DAOUtil.closeResources(preparedStatement, resultSet);
			ConnectionPool.getinstance().returnConnection(connection);
		}
		return companies;
	}

	@Override
	public boolean idExists(long idCompany) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM company  WHERE id=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idCompany);
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
	public boolean nameExists(String companyName) throws CouponSystemException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM company WHERE comp_name=?";
		Connection connection = ConnectionPool.getinstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyName);
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
		String sql = "SELECT id FROM company WHERE comp_name=? AND password=?";
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
