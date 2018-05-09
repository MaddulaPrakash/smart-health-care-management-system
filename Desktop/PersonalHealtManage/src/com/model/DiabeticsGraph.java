package com.model;

public class DiabeticsGraph {
	
	
	private String patName;
	
	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	private int rating;
	
	private String testName;
	
	private String timeStamp;

}
