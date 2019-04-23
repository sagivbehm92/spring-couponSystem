package core.exception;

import core.enums.ErrorType;

public class CouponSystemException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorType erorType;
	
	public CouponSystemException(ErrorType erorType) {
		this.erorType=erorType;
	}
	public CouponSystemException(String message,ErrorType erorType) {
		super(message);
		this.erorType=erorType;
	}
	
	public CouponSystemException(String message , Throwable exception , ErrorType erorType) {
		super(message, exception);
		this.erorType = erorType;
	}

	public ErrorType getErorType() {
		return erorType;
	}
	
	
	
	
	
	
}
