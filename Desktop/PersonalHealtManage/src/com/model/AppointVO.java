package com.model;

public class AppointVO {

	private int appointId;

	public void setAppointId(int appointId) {
		this.appointId = appointId;
	}

	public int getAppointId() {
		return appointId;
	}

	private String date;

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	private String fromTime;

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setPateintName(String pateintName) {
		this.pateintName = pateintName;
	}

	public String getPateintName() {
		return pateintName;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocId() {
		return docId;
	}

	private String toTime;

	private String pateintName;

	private int status;

	private String docId;

}
