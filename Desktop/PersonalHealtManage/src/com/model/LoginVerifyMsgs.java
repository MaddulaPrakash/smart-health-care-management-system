package com.model;

import java.io.Serializable;

public class LoginVerifyMsgs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userNameErrMsg;
	
	public String getUserNameErrMsg() {
		return userNameErrMsg;
	}

	public void setUserNameErrMsg(String userNameErrMsg) {
		this.userNameErrMsg = userNameErrMsg;
	}

	public String getPasswordErrMsg() {
		return passwordErrMsg;
	}

	public void setPasswordErrMsg(String passwordErrMsg) {
		this.passwordErrMsg = passwordErrMsg;
	}

	public void setSeverMessage(String severMessage) {
		this.severMessage = severMessage;
	}

	public String getSeverMessage() {
		return severMessage;
	}

	

	

	private String passwordErrMsg;
	
	private String severMessage;
	
	

}
