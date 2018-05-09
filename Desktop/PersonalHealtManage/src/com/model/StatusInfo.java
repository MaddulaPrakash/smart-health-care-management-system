package com.model;

import org.springframework.ui.Model;

public class StatusInfo {
	
	private boolean status;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	private String errMsg;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	
	public void setModel(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	private int type;
	
	private Object model;
	
	private String testName;
	
	private String exceptionMsg;
	
	

}
