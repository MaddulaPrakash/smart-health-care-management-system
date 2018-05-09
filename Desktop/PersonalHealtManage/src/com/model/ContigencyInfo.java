package com.model;

import java.math.BigDecimal;

public class ContigencyInfo {

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

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public BigDecimal getNegativeProbability() {
		return negativeProbability;
	}

	public void setNegativeProbability(BigDecimal negativeProbability) {
		this.negativeProbability = negativeProbability;
	}

	public BigDecimal getTotalPositiveOthers() {
		return totalPositiveOthers;
	}

	public void setTotalPositiveOthers(BigDecimal totalPositiveOthers) {
		this.totalPositiveOthers = totalPositiveOthers;
	}

	public BigDecimal getTotalNegativeOthers() {
		return totalNegativeOthers;
	}

	public void setTotalNegativeOthers(BigDecimal totalNegativeOthers) {
		this.totalNegativeOthers = totalNegativeOthers;
	}

	private String catName;

	private BigDecimal probability;

	private BigDecimal negativeProbability;

	private BigDecimal totalPositiveOthers;

	private BigDecimal totalNegativeOthers;

	private String userName;

}
