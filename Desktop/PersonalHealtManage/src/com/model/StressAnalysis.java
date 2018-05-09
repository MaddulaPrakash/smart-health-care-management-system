package com.model;

import java.io.Serializable;

public class StressAnalysis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getAgeGroupDesc() {
		return ageGroupDesc;
	}

	public void setAgeGroupDesc(String ageGroupDesc) {
		this.ageGroupDesc = ageGroupDesc;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}

	public String getIncomeGroup() {
		return incomeGroup;
	}

	public void setIncomeGroup(String incomeGroup) {
		this.incomeGroup = incomeGroup;
	}

	public String getIncomeGroupDesc() {
		return incomeGroupDesc;
	}

	public void setIncomeGroupDesc(String incomeGroupDesc) {
		this.incomeGroupDesc = incomeGroupDesc;
	}

	public String getProfExpGroup() {
		return profExpGroup;
	}

	public void setProfExpGroup(String profExpGroup) {
		this.profExpGroup = profExpGroup;
	}

	public String getProfExpGroupDesc() {
		return profExpGroupDesc;
	}

	public void setProfExpGroupDesc(String profExpGroupDesc) {
		this.profExpGroupDesc = profExpGroupDesc;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	public int getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	private String stressLabel;

	private String ageGroup;

	private String ageGroupDesc;

	private String sex;

	private String sexDesc;

	private String incomeGroup;

	private String incomeGroupDesc;

	private String profExpGroup;

	private String profExpGroupDesc;

	private String registered;
	
	private int totalRating;
	
	private String testName;

}
