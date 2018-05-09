package com.model;

import java.io.Serializable;
import java.util.Map;

public class NaiveBayesDataSetModelInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Double> mapCriteria;

	public Map<String,Double> getMapCriteria() {
		return mapCriteria;
	}

	public void setMapCriteria(Map<String,Double> mapCriteria) {
		this.mapCriteria = mapCriteria;
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	private String userId;
	

}
