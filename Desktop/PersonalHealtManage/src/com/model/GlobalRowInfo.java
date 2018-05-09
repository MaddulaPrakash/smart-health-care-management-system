package com.model;

import java.io.Serializable;
import java.util.List;

public class GlobalRowInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RowInfo> rowInfoList;

	public List<RowInfo> getRowInfoList() {
		return rowInfoList;
	}

	public void setRowInfoList(List<RowInfo> rowInfoList) {
		this.rowInfoList = rowInfoList;
	}
	

}
