package core.enums;

public enum ErrorType {
	EXISTS_TITLE(903, "the title you insert already exists", false),
	EXISTS_NAME(904, "the name you insert already exists", false),
	ID_NOT_EXISTS(905, "the id you insert not exists", false),
	COUPON_NOT_EXISTS(905, "the coupon id you insert not exists", false),
	CUSTOMER_NOT_EXISTS(905, "the customer id you insert not exists", false),
	COMPANY_NOT_EXISTS(905, "the company id you insert not exists", false),
	PURCHASE_COUPON(905, "you all ready have this coupon", false),
	OUT_OFF_STOCK(905, "This coupon is out of stock", false),
	ILLEGAL_DATE(905, " the date you insert illegal cen't read the date", false),
	// ========INVALIDATE
	INVALIDATE_ID(700, "the id you insert is invalidate", false),
	INVALIDATE_ID_SHORT(700, "the id you insert short ", false),
	INVALIDATE_TITLE(701, "the title you insert is invalidate", false),
	INVALIDATE_TITLE_SHORT(701, "the title you insert is short", false),
	INVALIDATE_DATE(702, "the endDate befor the startDay", false),
	INVALIDATE_DATE_END_DATE(702, "the endDate befor current Time", false),
	INVALIDATE_NAME(703, "the name you insert is invalidate ", false),
	INVALIDATE_NAME_SHORT(703, "the name you insert is short ", false),
	INVALIDATE_NAME_LONG(704, "the name you insert too long", false),
	INVALIDATE_PASSWORD(704, "the password is requierd and can't by null ", false),
	INVALIDATE_PASSWORD_SHORT(704, "the password you insert short", false),
	INVALIDATE_PASSWORD_LONG(704, "the password you insert too long", false),
	INVALIDATE_EMAIL(705, "the email you insert invalidate", false),
	INVALIDATE_AMOUNT(706, "the amount must be biger then zero", false),
	INVALIDATE_PRICE(706, "The price can not be less than zero", false),
	INVALIDATE_TYPE(707, "the type is requierd and can't by null", false),
	ILLEGAL_TYPE(707, "the type you insert is illegal", false),
	INVALIDATE_ID_COMPANY_NULL(708, "the idCompany must be initialize , cen't be null", false),
	// לכול המתודות שרשום בהם TRUE צריך להוסיף עוד איזה מסג שנירשום אותו בלוגר לעוד
	// איזה טבלה שתישמור מידע על ניסיות פירצות אבטחה
	// #@#!#! security breach #@##@#!#@!#@#!
	UPDATE_OR_REMOVE_BY_COMPANY_NOT_CREATE_THIS_COUPON(708, "you cen change/delete only your coupon", true),
	INVALIDATE_ID_COUPON_BY_COMPANY(708,"the idCompany Must be compatible with a company trying to create a new coupon ", true),
	UPDATE_BY_CUSTOMER(708, "you can change only your account,the id not matching with your id", true),
	UPDATE_BY_CUSTOMER_PASSWORD_NOT_MATCHING(708, "the password you insert not matching your real paswoord", true),
	UPDATE_BY_COMPANY(708, "you can change only your account,the id not matching with your id", true),
	UPDATE_BY_COMPANY_PASSWORD_NOT_MATCHING(708, "the password you insert not matching your real paswoord", true),
	UPDATE_COUPON_NOT_MATCHING(708, "coupon details not matching", true),
	SECURITY_BREACH_ACCESS_NOT_ALLOWED(709, "you dont have access to this section", true),
	// ===LOGIN
	LOGIN(709, "the userName or password incorrect", true), // ==>>#@#!#! security breach #@##@#!#@!#@#!
	LOGIN_ERROR_TYPE(709, "the type you insert incorrect", false),
	CHECK_LOGIN_ERROR(709, "you must login first", false),
	// Program Error
	DATE_BASE_ERROR(801, "this app shutDown plese try later ", true),
	THREAD_INTERRUPTED(801, "thred Interrupted", true),
	SHUT_DOWN(801, "this app shutDown plese try later ", true),
	SET_CONNECTION(801, "this app shutDown plese try later ", true),
	CONNECTION_POOL(801, "this app shutDown plese try later ", true);

	private final String errorClientMessage;
//	private final String getErrorServerMessage;
	private final int eror;
	private final boolean logDataBase;

	private ErrorType(int error, String errorClientMessage, boolean logDataBase) {
		this.eror = error;
		this.errorClientMessage = errorClientMessage;
		this.logDataBase = logDataBase;
	}

	public boolean isLogDataBase() {
		return logDataBase;
	}

	public String getErrorClientMessage() {
		return errorClientMessage;
	}


	public int getEror() {
		return eror;
	}

//	public String getErrorServerMessage() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
