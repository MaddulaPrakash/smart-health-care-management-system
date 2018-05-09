package com.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DeleteAppointInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> appointmentList;

	public void setAppointmentList(ArrayList<Integer> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public ArrayList<Integer> getAppointmentList() {
		return appointmentList;
	}

}
