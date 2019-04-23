package core.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.javaBeans.ErrorBean;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

//	public ResponseEntity<ErrorBean> dsadsadsadsa(Exception exception , WebRequest webRequest ) {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorBean> response(Exception exception) {
		System.out.println("from ExceptionsHandler");
		if (exception instanceof CouponSystemException) {
			CouponSystemException couponSystemException = (CouponSystemException) exception;
			if (couponSystemException.getErorType().isLogDataBase() == true) {
				// save the error on date base or log to file
			}
			ErrorBean errorBean = new ErrorBean(couponSystemException.getErorType().getEror(),
					couponSystemException.getMessage(), couponSystemException.getErorType().getErrorClientMessage());
			System.out.println(exception.getMessage()+"  error message : "+couponSystemException.getErorType().getErrorClientMessage());
			return ResponseEntity.status(600).body(errorBean);
		}
		ErrorBean errorBean = new ErrorBean();
		errorBean.setMessage("general error");
		errorBean.setInternalErrorMessage("general error");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
		return ResponseEntity.status(800).body(errorBean);

	}

//	@ExceptionHandler(CouponSystemException.class)
//	public ResponseEntity<ErrorBean> CouponSystem(CouponSystemException exception) {
//		
//		if (exception instanceof CouponSystemException) {
//			CouponSystemException couponSystemException = (CouponSystemException) exception;
//			if (couponSystemException.getErorType().isLogDataBase() == true) {
//				// save the error on date base or log to file
//				
//			}
//			ErrorBean errorBean = new ErrorBean(couponSystemException.getErorType().getEror(),
//					couponSystemException.getMessage(), couponSystemException.getErorType().getErrorClientMessage());
//			return ResponseEntity.status(600).body(errorBean);
//		}
//		ErrorBean errorBean = new ErrorBean();
//		errorBean.setMessage("general error");
//		System.out.println(exception.getMessage());
//		exception.printStackTrace();
//		return ResponseEntity.status(800).body(errorBean);
//		
//	}

}
