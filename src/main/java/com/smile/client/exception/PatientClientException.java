package com.smile.client.exception;

public class PatientClientException extends Exception {

	private String errorCode;
	private String errorMsg;
	

	public PatientClientException(String errorCode,String errorMsg) {
		super(errorCode+errorMsg);
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}

	public PatientClientException() {
		// TODO Auto-generated constructor stub
	}

	public PatientClientException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PatientClientException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public PatientClientException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PatientClientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
