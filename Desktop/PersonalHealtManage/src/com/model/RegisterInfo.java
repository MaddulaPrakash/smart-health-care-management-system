package com.model;

public class RegisterInfo {
	
	private String errCode;
	
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}
	
	private boolean status;
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}
	
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public int getLoginType() {
		return loginType;
	}

	private int loginType;
}
