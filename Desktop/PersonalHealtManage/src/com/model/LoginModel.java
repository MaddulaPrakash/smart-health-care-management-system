package com.model;

import java.io.Serializable;

public class LoginModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginuserId;

	public String getLoginuserId() {
		return loginuserId;
	}

	public void setLoginuserId(String loginuserId) {
		this.loginuserId = loginuserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}



	private String password;

	private int loginType;

	

}
