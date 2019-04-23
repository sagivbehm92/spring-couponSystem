package core.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.enums.ErrorType;
import core.exception.CouponSystemException;

public class DAOUtil {
	public DAOUtil() {};

	public static  void closeResources(PreparedStatement preparedStatement) throws CouponSystemException {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				throw new CouponSystemException(e.getMessage(), e, ErrorType.DATE_BASE_ERROR);
			}
		}
	}

	public static void closeResources(PreparedStatement preparedStatement, ResultSet resultSet)
			throws CouponSystemException {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				throw new CouponSystemException(e.getMessage(), e, ErrorType.DATE_BASE_ERROR);
			}
		}

	}

}
