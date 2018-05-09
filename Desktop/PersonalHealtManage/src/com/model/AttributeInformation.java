package com.model;

import java.io.Serializable;

public class AttributeInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getOutputFactor() {
		return outputFactor;
	}

	public void setOutputFactor(String outputFactor) {
		this.outputFactor = outputFactor;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	private String attributeValue;

	private String outputFactor;
	
	private int rowId;

}
