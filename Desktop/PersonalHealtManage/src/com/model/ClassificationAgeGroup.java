package com.model;

import java.io.Serializable;

public class ClassificationAgeGroup implements Serializable {

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

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	private String stressLabel;

	private String ageGroup;

}
