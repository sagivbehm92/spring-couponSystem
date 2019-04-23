package core.javaBeans;


public class ErrorBean {

	private int internalErrorCode;
	private String internalErrorMessage;
	private String message;

	public ErrorBean() {
	}

	public ErrorBean(int internalErrorCode, String internalErrorMessage) {
		this.internalErrorCode = internalErrorCode;
		this.internalErrorMessage = internalErrorMessage;
	}

	public ErrorBean(int internalErrorCode, String internalErrorMessage, String message) {
		this.internalErrorCode = internalErrorCode;
		this.internalErrorMessage = internalErrorMessage;
		this.message = message;
	}
	

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalErrorMessage() {
		return internalErrorMessage;
	}

	public void setInternalErrorMessage(String internalErrorMessage) {
		this.internalErrorMessage = internalErrorMessage;
	}

	public void setInternalErrorCode(int internalErrorCode) {
		this.internalErrorCode = internalErrorCode;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String internalErrorMessage) {
		this.internalErrorMessage = internalErrorMessage;
	}

}
