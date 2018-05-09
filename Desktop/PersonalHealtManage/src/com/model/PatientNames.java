package com.model;

import java.io.Serializable;

public class PatientNames implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String patName;

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

}
