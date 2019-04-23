package core.couponSystemUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import core.enums.ErrorType;
import core.exception.CouponSystemException;

/**
 * this class in charge of the connections in the API
 * 
 */
public class ConnectionPool {
	

	private Set<Connection> connections = new HashSet<>();
	// this list save reference to connection we give to client too use the data
	// base
	private List<Connection> outConnections = new ArrayList<>();
	private static ConnectionPool instance;
	private boolean available = true;
	private static final int MAX_CONNECTION = 10;
	private static Object Mutex;

	static {
		Mutex = new Object();
	}

	/**
	 * this method return instance to singleton ConnectionPool object. if this
	 * 
	 * @return object ConnectionPool
	 * @throws DAOException
	 */
	public static ConnectionPool getinstance() throws CouponSystemException {

		if (instance == null) {
			synchronized (Mutex) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	private ConnectionPool() throws CouponSystemException {

		this.setConnections();
	}

	/**
	 * this method give a live connection to the database from Collection
	 * connections and remove the connection from Collection connections the method
	 * give back if the Collection empty this method well waiting return connection
	 * to the {@link Set} connections
	 * 
	 * @return live connection to the date base
	 * @throws when the API shutdown
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		Connection connection;

		if (available) {

			while (connections.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new CouponSystemException(e.getMessage(), e, ErrorType.THREAD_INTERRUPTED);
				}
			}
			Iterator<Connection> it = connections.iterator();
			connection = it.next();
			it.remove();
			outConnections.add(connection);
			return connection;
		} else {

			throw new CouponSystemException("this app shutDown plese try later ", ErrorType.SHUT_DOWN);

		}

	}

	/**
	 * this method return connection to the Collection connection and notify for a
	 * connection back to {@link Set} connections
	 * 
	 * @return void
	 */

	public synchronized void returnConnection(Connection connection) {
		outConnections.remove(connection);
		connections.add(connection);
		notify();
	}

	/**
	 * this method close all Collection connections and he will wait for all
	 * connections came back to the Collection connection when the API shutdown the
	 * method closAllconnections will be start
	 * 
	 * @param available
	 * @return void
	 * @throws DAOException
	 */
	public synchronized void closeAllconnections() throws CouponSystemException {

		available = false;
		while (connections.size() != MAX_CONNECTION) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (Exception e) {
				throw new CouponSystemException("closeAllconnections failed ", e, ErrorType.CONNECTION_POOL);
			}
		}
	}

	/**
	 * this method initialized 10 live connection to the database
	 * 
	 * @return void
	 * @throws when the database unavailable or the URL incorrect
	 */
	public synchronized void setConnections() throws CouponSystemException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/couponsystem?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC";
		String user = "root";
		String password = "almogk012";
		try {
			Class.forName(driver);
			while (this.connections.size() != MAX_CONNECTION) {
				Connection connection = DriverManager.getConnection(url, user, password);
				connections.add(connection);
			}
			System.out.println("Succeeded setConnections");
		} catch (SQLException | ClassNotFoundException e) {
			throw new CouponSystemException("setConnections failed ", e, ErrorType.SET_CONNECTION);
		}
	}

	// לבדיקה
	public Set<Connection> getAll() {
		return this.connections;
	}

}
