package com.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AttributeComputationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal meanPositive;

	public BigDecimal getMeanPositive() {
		return meanPositive;
	}

	public void setMeanPositive(BigDecimal meanPositive) {
		this.meanPositive = meanPositive;
	}

	public BigDecimal getStandardDevPositive() {
		return standardDevPositive;
	}

	public void setStandardDevPositive(BigDecimal standardDevPositive) {
		this.standardDevPositive = standardDevPositive;
	}

	public BigDecimal getTotalSumPositive() {
		return totalSumPositive;
	}

	public void setTotalSumPositive(BigDecimal totalSumPositive) {
		this.totalSumPositive = totalSumPositive;
	}

	public BigDecimal getProbabilityPositive() {
		return probabilityPositive;
	}

	public void setProbabilityPositive(BigDecimal probabilityPositive) {
		this.probabilityPositive = probabilityPositive;
	}

	public BigDecimal getMeanNegative() {
		return meanNegative;
	}

	public void setMeanNegative(BigDecimal meanNegative) {
		this.meanNegative = meanNegative;
	}

	public BigDecimal getStandardDevNegative() {
		return standardDevNegative;
	}

	public void setStandardDevNegative(BigDecimal standardDevNegative) {
		this.standardDevNegative = standardDevNegative;
	}

	public BigDecimal getTotalSumNegative() {
		return totalSumNegative;
	}

	public void setTotalSumNegative(BigDecimal totalSumNegative) {
		this.totalSumNegative = totalSumNegative;
	}

	public BigDecimal getProbabilityNegative() {
		return probabilityNegative;
	}

	public void setProbabilityNegative(BigDecimal probabilityNegative) {
		this.probabilityNegative = probabilityNegative;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	private BigDecimal standardDevPositive;

	private BigDecimal totalSumPositive;

	private BigDecimal probabilityPositive;

	private BigDecimal meanNegative;

	private BigDecimal standardDevNegative;

	private BigDecimal totalSumNegative;

	private BigDecimal probabilityNegative;
	
	private String attributeName;

}
