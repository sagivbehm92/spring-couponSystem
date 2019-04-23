package core.couponSystemUtil;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import core.dao.idao.ICouponDAO;
import core.exception.CouponSystemException;

/**
 * class {@link DailyCouponExpirationTask} responsible to delete all the coupon
 * expired from database
 * 
 */
@Component
public class DailyCouponExpirationTask implements Runnable {
	
	private final static long TAMESLEEP = 1000 * 60 * 60 * 24; // one day
	private boolean quit = true;
	private	Thread thread;
	@Resource(name = "couponJpa")
	private ICouponDAO couponDAO;

	public DailyCouponExpirationTask() {
		
	}
	
	@PostConstruct
	public void init () {
		thread = new Thread(this);
		thread.start();		
	}
	
	
	@Override
	public void run() {
	
		while (quit) {
			try { 
				couponDAO.removeExpiredCoupons(new Date());
				System.out.println("the DailyTask try/remove the expired coupon ");
			} catch (CouponSystemException e1) {
				e1.printStackTrace();
			}
			try {
				Thread.sleep(TAMESLEEP);
			} catch (InterruptedException e) {
				break;
			}
		}

		System.out.println("the end DailyTask quit = "+quit + "when the app shutdown");
	}

	/**
	 * when the API shutdown this method will be stop the dailyCouponExpirationTask
	 * task
	 */
	public void stopTask() {
		this.quit=false;
		this.thread.interrupt();
	}
	
	public Thread getThread() {
		return this.thread;
	}

}
