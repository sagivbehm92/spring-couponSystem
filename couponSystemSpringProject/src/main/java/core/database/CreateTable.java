package core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "almogk012";
		String url = "jdbc:mysql://localhost:3306/couponsystem?autoReconnect=true&useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true";

		String sqlCompany = "CREATE TABLE company(id BIGINT AUTO_INCREMENT , comp_name VARCHAR(50)"
				+ " , password VARCHAR(50) , email VARCHAR(50) , primary key(ID) )";
		
		String sqlCoupon = "CREATE TABLE coupon(id BIGINT AUTO_INCREMENT, title VARCHAR(50),start_date DATE ,"
				+ " end_date DATE , amount INTEGER , type VARCHAR(50) , message VARCHAR(50) ,"
				+ " price FLOAT , image VARCHAR(150) ,company_id BIGINT, primary key(ID) ,"
				+ "FOREIGN KEY (company_id) REFERENCES company(id))";

		String sqlCustomer = "CREATE TABLE customer(id BIGINT AUTO_INCREMENT, cust_name VARCHAR(50)" 
				+ " , password VARCHAR(50) , PRIMARY KEY(id) )";

		String sqlCustomerCoupon = "CREATE TABLE customer_Coupon(cust_id BIGINT,coupon_id BIGINT,"
				+ "primary key(cust_id,coupon_id)," + "FOREIGN KEY (cust_id) REFERENCES customer(id),"
				+ "FOREIGN KEY (coupon_id) REFERENCES coupon(id))";
		
		String sqlAdminLog = "CREATE TABLE logAdmin (id BIGINT AUTO_INCREMENT , logMessage VARCHAR(100) , "+
				" logTime DATE , logException VARCHAR(120) , logType VARCHAR(20), primary key(id) )";
		
		try {
			Class.forName(driver);
			System.out.println("Driver loaded : " + driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(url, user, password);
				Statement statement = connection.createStatement();) {
			createTable(statement, sqlCompany);
			System.out.println("createTable 1");
			createTable(statement, sqlCoupon);
			System.out.println("createTable 2");
			createTable(statement, sqlCustomer);
			System.out.println("createTable 3");
			createTable(statement, sqlCustomerCoupon);
			System.out.println("createTable 4");
			createTable(statement, sqlAdminLog);
			System.out.println("createTable 5");
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}

	public static void createTable(Statement statement, String sql) {
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}

	}
}
