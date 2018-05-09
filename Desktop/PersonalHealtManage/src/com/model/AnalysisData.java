package com.model;

import java.io.Serializable;

public class AnalysisData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int rating;
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStresslabel() {
		return stresslabel;
	}

	public void setStresslabel(String stresslabel) {
		this.stresslabel = stresslabel;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	private String stresslabel;
	
	private String testname;
	
	private String timeStamp;

}
