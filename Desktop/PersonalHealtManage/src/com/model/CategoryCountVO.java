package com.model;

import java.io.Serializable;

public class CategoryCountVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noOfValues;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getNoOfValues() {
		return noOfValues;
	}

	public void setNoOfValues(int noOfValues) {
		this.noOfValues = noOfValues;
	}

	private String catName;

}
