//package core.dao.jdbc;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import java.sql.Statement;
//
//import core.couponSystemUtil.ConnectionPool;
//import core.dao.idao.ICouponDAO;
//import core.enums.CouponType;
//import core.enums.ErrorType;
//import core.exception.CouponSystemException;
//import core.javaBeans.Coupon;
//
///**
// * 
// * 
// */
////@Repository
//public class CouponDAO implements ICouponDAO {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public long create(Coupon coupon) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String sql = "INSERT INTO coupon(title ,start_date , end_date , amount , type , message , price , image , company_id) "
//				+ " VALUES(?,?,?,?,?,?,?,?,?)";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			preparedStatement.setString(1, coupon.getTitle());
//			preparedStatement.setDate(2, new Date(coupon.getStartDate().getTime()));
//			preparedStatement.setDate(3, new Date(coupon.getEndDate().getTime()));
//			preparedStatement.setInt(4, coupon.getAmount());
//			preparedStatement.setString(5, String.valueOf(coupon.getType()));
//			preparedStatement.setString(6, coupon.getMessage());
//			preparedStatement.setDouble(7, coupon.getPrice());
//			preparedStatement.setString(8, coupon.getImage());
//			preparedStatement.setLong(9, coupon.getIdCompany());
//			preparedStatement.executeUpdate();
//
//			resultSet = preparedStatement.getGeneratedKeys();
//			resultSet.next();
//			return resultSet.getLong(1);
//
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//	}
//
//	@Override
//	public void update(Coupon coupon) throws CouponSystemException {
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		PreparedStatement preparedStatement = null;
//		String sql = "UPDATE coupon SET title=? ,start_date=? , end_date=?, amount=?"
//				+ " , type=? , message=? , price=?  , image=? , company_id=?  WHERE id=?";
//		// String sql = "UPDATE coupon SET title=? ,start_date=? , end_date=?,
//		// amount=amount-1"
//		// + " , type=? , message=? , price=? , image=? , company_id=? WHERE id=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, coupon.getTitle());
//			preparedStatement.setDate(2, new Date(coupon.getStartDate().getTime()));
//			preparedStatement.setDate(3, new Date(coupon.getEndDate().getTime()));
//			preparedStatement.setInt(4, coupon.getAmount());
//			preparedStatement.setString(5, String.valueOf(coupon.getType()));
//			preparedStatement.setString(6, coupon.getMessage());
//			preparedStatement.setDouble(7, coupon.getPrice());
//			preparedStatement.setString(8, coupon.getImage());
//			preparedStatement.setLong(9, coupon.getIdCompany());
//			preparedStatement.setLong(10, coupon.getId());
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//	}
//
//	@Override
//	public void updateAmount(long idCoupon, int amountDifferense) throws CouponSystemException {
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		PreparedStatement preparedStatement = null;
//		String sql = "UPDATE coupon SET amount=amount+?" + "  WHERE id=? AND amount+?>=0";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setInt(1, amountDifferense);
//			preparedStatement.setLong(2, idCoupon);
//			preparedStatement.setInt(3, amountDifferense);
//			int intResult = preparedStatement.executeUpdate();
//			if (intResult != 0) {
//				return;
//			} else {
//				throw new CouponSystemException(ErrorType.OUT_OFF_STOCK);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//	}
//
//	@Override
//	public void remove(long idCoupon) throws CouponSystemException {
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		PreparedStatement preparedStatement = null;
//		String sql2 = "DELETE FROM coupon WHERE id=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql2);
//			preparedStatement.setLong(1, idCoupon);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql2, e, ErrorType.DATE_BASE_ERROR);
//
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//	}
//
//	@Override
//	public Coupon read(long idCoupon) throws CouponSystemException {
//		Coupon coupon = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		String sql = "SELECT * FROM coupon WHERE id=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCoupon);
//			resultSet = preparedStatement.executeQuery();
//
//			while (resultSet.next()) {
//				coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//		return coupon;
//	}
//
//	@Override
//	public boolean idExists(long IdBeans) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String sql = "SELECT * FROM coupon WHERE id=?";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, IdBeans);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				return true;
//			}
//			return false;
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//	}
//
//	@Override
//	public boolean titlelExists(String title) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String sql = "SELECT * FROM coupon WHERE title=?";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, title);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				return true;
//			}
//			return false;
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//	}
//
//	@Override
//	public void removeExpiredCoupons(java.util.Date date) throws CouponSystemException {
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		PreparedStatement preparedStatement = null;
//		String sql_customer = "DELETE customer_coupon FROM customer_coupon "
//				+ "INNER JOIN coupon ON coupon.id = customer_coupon.coupon_id " + "WHERE coupon.end_date<?";
//		String sql_coupon = "DELETE  FROM coupon WHERE end_date<?";
//
//		try {
//			preparedStatement = connection.prepareStatement(sql_customer);
//			preparedStatement.setDate(1, new Date(date.getTime()));
//			preparedStatement.executeUpdate();
//			preparedStatement.close();
//			preparedStatement = connection.prepareStatement(sql_coupon);
//			preparedStatement.setDate(1, new Date(date.getTime()));
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql_customer, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//	}
//
//	
//	/**
//	 * this method go to data base and get all coupons from coupon table
//	 * 
//	 * @return list of all coupons on data base
//	 * @throws CouponSystemException When there is a problem with a database
//	 */
//	@Override
//	public List<Coupon> getAll() throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String sql = "SELECT * FROM coupon";
//		List<Coupon> coupons = new ArrayList<>();
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	// customer coupon
//	// =========================================================
//	@Override
//	// בפונקציה הזאת צריך לשלב שתי טבלאות ולעשות גוין בניהם כדי שנקבל את כול
//	// הקופונים שיש ללקוח
//	public List<Coupon> getCustomerCoupons(long idCustomer) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<Coupon> coupons = new ArrayList<>();
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		String sql = "SELECT * FROM coupon " + " INNER JOIN customer_coupon  "
//				+ "ON coupon.id = customer_coupon.coupon_id " + " WHERE customer_coupon.cust_id=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCustomer);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	// TODO הפונקציה לא עובדת לא מצליח לחקת רק את הקופונים שאין ללקוח הזה
//	@Override
//	public List<Coupon> getCustomerCouponsNotPurchase(long idCustomer) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<Coupon> coupons = new ArrayList<>();
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		String sql = "SELECT * FROM coupon LEFT JOIN customer_coupon "
//				+ "ON coupon.id=customer_coupon.coupon_id AND customer_coupon.cust_id=? "
//				+ " WHERE customer_coupon.cust_id is null";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCustomer);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	@Override
//	public List<Coupon> getCustomerCouponsByType(CouponType type, long idCustomer) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<Coupon> coupons = new ArrayList<>();
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		String sql = "SELECT * FROM coupon " + " INNER JOIN Customer_Coupon ON coupon.id= Customer_Coupon.coupon_id "
//				+ " WHERE Customer_Coupon.cust_id=? AND coupon.type=? ";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCustomer);
//			preparedStatement.setString(2, String.valueOf(type));
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	@Override
//	public List<Coupon> getCustomerCouponsByPrice(double price, long idCustomer) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<Coupon> coupons = new ArrayList<>();
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		String sql = "SELECT * FROM coupon INNER JOIN Customer_Coupon" + " ON coupon.id = Customer_Coupon.coupon_id "
//				+ "WHERE Customer_Coupon.cust_id=? AND coupon.price<=? ";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCustomer);
//			preparedStatement.setDouble(2, price);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	@Override
//	public void purchaseCoupon(long idCust, long idCoupon) throws CouponSystemException {
//		String sql = "INSERT INTO Customer_Coupon VALUES(?,?)";
//		Connection con = ConnectionPool.getinstance().getConnection();
//		try (PreparedStatement prep = con.prepareStatement(sql);) {
//			prep.setLong(1, idCust);
//			prep.setLong(2, idCoupon);
//			prep.executeUpdate();
//		} catch (SQLException e) {
//			// updateAmount(idCoupon, 1);
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			ConnectionPool.getinstance().returnConnection(con);
//		}
//
//	}
//
//	@Override
//	public void removeCustomerCouponByCoupon(long idCoupon) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		String sql = "DELETE FROM Customer_Coupon WHERE coupon_id=?";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCoupon);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//	}
//
//	@Override
//	public void removeCustomerCouponByCustomer(long idCust) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		String sql = "DELETE FROM Customer_Coupon WHERE cust_id=?";
//		Connection con = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = con.prepareStatement(sql);
//			preparedStatement.setLong(1, idCust);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(con);
//		}
//
//	}
//
//	// company coupon
//	// =========================================================
//	@Override
//	public List<Coupon> getCompanyCoupons(long idCompany) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		List<Coupon> coupons = new ArrayList<>();
//		String sql = "SELECT * FROM coupon WHERE company_id=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCompany);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			new SQLException(sql, e);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//		return coupons;
//	}
//
//	@Override
//	public List<Coupon> getCompanyCouponsByPrice(long idCompany, double price) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		List<Coupon> coupons = new ArrayList<>();
//		String sql = "SELECT * FROM coupon WHERE company_id=? AND price<=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCompany);
//			preparedStatement.setDouble(2, price);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			new SQLException(sql, e);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//		return coupons;
//	}
//
//	@Override
//	public List<Coupon> getCompanyCouponsByType(CouponType type, long idCompany) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		List<Coupon> coupons = new ArrayList<>();
//		String sql = "SELECT * FROM coupon WHERE company_id=? AND type=?";
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setLong(1, idCompany);
//			preparedStatement.setString(2, String.valueOf(type));
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//		} catch (SQLException e) {
//			new SQLException(sql, e);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//		return coupons;
//	}
//
//	
//	@Override
//	public List<Coupon> getCompanyCouponsByDate(java.util.Date date, long idCompany) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		List<Coupon> coupons = new ArrayList<>();
//		String sql = "SELECT * FROM coupon WHERE end_date<=? AND company_id=?";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setDate(1, new Date(date.getTime()));
//			preparedStatement.setLong(2, idCompany);
//			resultSet = preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				Coupon coupon = new Coupon();
//				coupon.setId(resultSet.getLong("id"));
//				coupon.setTitle(resultSet.getString("title"));
//				coupon.setStartDate(resultSet.getDate("start_date"));
//				coupon.setEndDate(resultSet.getDate("end_date"));
//				coupon.setAmount(resultSet.getInt("amount"));
//				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
//				coupon.setMessage(resultSet.getString("message"));
//				coupon.setPrice(resultSet.getDouble("price"));
//				coupon.setImage(resultSet.getString("image"));
//				coupon.setIdCompany(resultSet.getLong("company_id"));
//				coupons.add(coupon);
//			}
//
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement, resultSet);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//
//		return coupons;
//	}
//
//	@Override
//	public void removeCompanyCouponsBycompany(long idCompany) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		String sql2 = "DELETE FROM coupon WHERE company_id=?";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql2);
//			preparedStatement.setLong(1, idCompany);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql2, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//	}
//
//	@Override
//	public void removeCustomerCouponByCompany(long idCompany) throws CouponSystemException {
//		PreparedStatement preparedStatement = null;
//		String sql1 = "DELETE customer_coupon FROM customer_coupon "
//				+ " INNER JOIN coupon ON coupon.id = customer_coupon.coupon_id " 
//				+ " WHERE coupon.company_id = ? ";
//		Connection connection = ConnectionPool.getinstance().getConnection();
//		try {
//			preparedStatement = connection.prepareStatement(sql1);
//			preparedStatement.setLong(1, idCompany);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw new CouponSystemException(sql1, e, ErrorType.DATE_BASE_ERROR);
//		} finally {
//			DAOUtil.closeResources(preparedStatement);
//			ConnectionPool.getinstance().returnConnection(connection);
//		}
//	}
//
//}
