package com.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class NaiveBayesOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<AttributeComputationModel> attributeComputationModelList;

	

	public boolean isOutputFactor() {
		return outputFactor;
	}

	public void setOutputFactor(boolean outputFactor) {
		this.outputFactor = outputFactor;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BigDecimal getTotalMeasureNegative() {
		return totalMeasureNegative;
	}

	public void setTotalMeasureNegative(BigDecimal totalMeasureNegative) {
		this.totalMeasureNegative = totalMeasureNegative;
	}

	public BigDecimal getTotalMeasurePositive() {
		return totalMeasurePositive;
	}

	public void setTotalMeasurePositive(BigDecimal totalMeasurePositive) {
		this.totalMeasurePositive = totalMeasurePositive;
	}

	public BigDecimal getClassYesProbability() {
		return classYesProbability;
	}

	public void setClassYesProbability(BigDecimal classYesProbability) {
		this.classYesProbability = classYesProbability;
	}

	public BigDecimal getClassNoProbability() {
		return classNoProbability;
	}

	public void setClassNoProbability(BigDecimal classNoProbability) {
		this.classNoProbability = classNoProbability;
	}

	public List<AttributeComputationModel> getAttributeComputationModelList() {
		return attributeComputationModelList;
	}

	public void setAttributeComputationModelList(
			List<AttributeComputationModel> attributeComputationModelList) {
		this.attributeComputationModelList = attributeComputationModelList;
	}

	private BigDecimal totalMeasureNegative;

	private BigDecimal totalMeasurePositive;

	private BigDecimal classYesProbability;

	private BigDecimal classNoProbability;

	private boolean outputFactor;

	private String exceptionMessage;

}
