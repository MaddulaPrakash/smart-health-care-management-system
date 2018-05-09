package com.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProbabilityInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int probId;

	private String catName;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getProbId() {
		return probId;
	}

	public void setProbId(int probId) {
		this.probId = probId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	private BigDecimal probability;

	private BigDecimal negativeProbability;

	private String username;

}
