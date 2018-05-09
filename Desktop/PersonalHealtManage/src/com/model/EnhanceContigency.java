package com.model;

import java.math.BigDecimal;

public class EnhanceContigency {

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getPositiveCatRatio() {
		return positiveCatRatio;
	}

	public void setPositiveCatRatio(BigDecimal positiveCatRatio) {
		this.positiveCatRatio = positiveCatRatio;
	}

	public BigDecimal getOtherCatRatio() {
		return otherCatRatio;
	}

	public void setOtherCatRatio(BigDecimal otherCatRatio) {
		this.otherCatRatio = otherCatRatio;
	}

	private String catName;

	private BigDecimal positiveCatRatio;

	private BigDecimal otherCatRatio;

	private String userName;

}
