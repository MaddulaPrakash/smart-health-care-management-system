package com.model;

public class PartialClassifierInfo {

	private String catName;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public double getPositiveRatio() {
		return positiveRatio;
	}

	public void setPositiveRatio(double positiveRatio) {
		this.positiveRatio = positiveRatio;
	}

	public double getNegativeRatio() {
		return negativeRatio;
	}

	public void setNegativeRatio(double negativeRatio) {
		this.negativeRatio = negativeRatio;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String userId;

	private double positiveRatio;

	private double negativeRatio;

}
