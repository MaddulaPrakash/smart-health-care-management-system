package com.model;

import java.io.Serializable;
import java.util.List;


public class RowInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<AttributeInformation> attributeInfo;


	public List<AttributeInformation> getAttributeInfo() {
		return attributeInfo;
	}


	public void setAttributeInfo(List<AttributeInformation> attributeInfo) {
		this.attributeInfo = attributeInfo;
	}


	

}
