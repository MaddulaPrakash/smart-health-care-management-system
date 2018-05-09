package com.model;

import java.io.Serializable;

public class NaiveBayesInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double fti;

	public Double getFti() {
		return fti;
	}

	public void setFti(Double fti) {
		this.fti = fti;
	}

	public Double getT3() {
		return t3;
	}

	public void setT3(Double t3) {
		this.t3 = t3;
	}

	public Double getT4U() {
		return t4U;
	}

	public void setT4U(Double t4u) {
		t4U = t4u;
	}

	public Double getTsH() {
		return tsH;
	}

	public void setTsH(Double tsH) {
		this.tsH = tsH;
	}

	public Double getTt4() {
		return tt4;
	}

	public void setTt4(Double tt4) {
		this.tt4 = tt4;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getT3Lung() {
		return t3Lung;
	}

	public void setT3Lung(Double t3Lung) {
		this.t3Lung = t3Lung;
	}

	public Double getT4ULung() {
		return t4ULung;
	}

	public void setT4ULung(Double t4uLung) {
		t4ULung = t4uLung;
	}

	public Double getTsHLung() {
		return tsHLung;
	}

	public void setTsHLung(Double tsHLung) {
		this.tsHLung = tsHLung;
	}

	public Double getTt4Lung() {
		return tt4Lung;
	}

	public void setTt4Lung(Double tt4Lung) {
		this.tt4Lung = tt4Lung;
	}

	public Double getFtiLung() {
		return ftiLung;
	}

	public void setFtiLung(Double ftiLung) {
		this.ftiLung = ftiLung;
	}

	private Double t3;

	private Double t4U;

	private Double tsH;

	private Double tt4;

	private String userId;

	private Double t3Lung;

	private Double t4ULung;

	private Double tsHLung;

	private Double tt4Lung;

	private Double ftiLung;

	private Double t3Thy;

	public Double getT3Thy() {
		return t3Thy;
	}

	public void setT3Thy(Double t3Thy) {
		this.t3Thy = t3Thy;
	}

	public Double getT4UThy() {
		return t4UThy;
	}

	public void setT4UThy(Double t4uThy) {
		t4UThy = t4uThy;
	}

	public Double getTsHThy() {
		return tsHThy;
	}

	public void setTsHThy(Double tsHThy) {
		this.tsHThy = tsHThy;
	}

	public Double getTt4Thy() {
		return tt4Thy;
	}

	public void setTt4Thy(Double tt4Thy) {
		this.tt4Thy = tt4Thy;
	}

	public Double getFtiThy() {
		return ftiThy;
	}

	public void setFtiThy(Double ftiThy) {
		this.ftiThy = ftiThy;
	}

	private Double t4UThy;

	private Double tsHThy;

	private Double tt4Thy;

	private Double ftiThy;

}
