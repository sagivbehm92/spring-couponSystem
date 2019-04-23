package core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTable {

	public static void main(String[] args) {
		String user = "root";
		String password = "almogk012";
		String url = "jdbc:mysql://localhost:3306/couponsystem?autoReconnect=true&serverTimezone=UTC&useSSL=false";
		String driver = "com.mysql.jdbc.Driver";
		String sql2 = "DROP TABLE Customer_Coupon";
		String sql3 = "DROP TABLE coupon";
//		String sql4 = "DROP TABLE Company";
//		String sql5 = "DROP TABLE customer";
//		String sql6 = "DROP TABLE logAdmin";

		try {
			Class.forName(driver);
			System.out.println("Driver loaded : " + driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stat = con.createStatement()) {

			dropTable(stat, sql2);
			dropTable(stat, sql3);
//			dropTable(stat, sql4);
//			dropTable(stat, sql5);
//			dropTable(stat, sql6);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void dropTable(Statement stat, String sql) {
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
	}
}
