package core.couponSystemUtil;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import core.exception.CouponSystemException;

/**
 * this the main class for all this API
 */
@Component
public class CouponSystem {

	@Autowired
	private DailyCouponExpirationTask Task;

	/**
	 * this coRs responsible for call method ConnectionPool.getinstanee() to
	 * initialize and load the connection pool and create new
	 * dailyCouponExpirationTask and initialize the task
	 */
	private CouponSystem() throws CouponSystemException {
		ConnectionPool.getinstance();
	}

	/**
	 * this method start when you try shutDown all AIP is responsible to start 2.
	 * method one closAllconnections to close all connection u have to the database.
	 * two stopTask to stop the Task immediately
	 * 
	 * @throws when fail close
	 */
	@PreDestroy
	public void shutDown() throws CouponSystemException {
		Task.stopTask();
		try {
			Task.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ConnectionPool.getinstance().closeAllconnections();
	}
}
