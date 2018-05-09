package com.model;

import java.io.Serializable;

public class ClassificationGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int noOfPersons;

	public int getNoOfPersons() {
		return noOfPersons;
	}

	public void setNoOfPersons(int noOfPersons) {
		this.noOfPersons = noOfPersons;
	}

	public String getStressLabel() {
		return stressLabel;
	}

	public void setStressLabel(String stressLabel) {
		this.stressLabel = stressLabel;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	private String stressLabel;

	private String group;

}
