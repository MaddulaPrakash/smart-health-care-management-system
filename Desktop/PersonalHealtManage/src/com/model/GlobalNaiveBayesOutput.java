package com.model;

import java.io.Serializable;

public class GlobalNaiveBayesOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NaiveBayesOutput hyperTensionOutput;

	public NaiveBayesOutput getHyperTensionOutput() {
		return hyperTensionOutput;
	}

	public void setHyperTensionOutput(NaiveBayesOutput hyperTensionOutput) {
		this.hyperTensionOutput = hyperTensionOutput;
	}

	public NaiveBayesOutput getLungcancerOutput() {
		return lungcancerOutput;
	}

	public void setLungcancerOutput(NaiveBayesOutput lungcancerOutput) {
		this.lungcancerOutput = lungcancerOutput;
	}

	public NaiveBayesOutput getThyroidOutput() {
		return thyroidOutput;
	}

	public void setThyroidOutput(NaiveBayesOutput thyroidOutput) {
		this.thyroidOutput = thyroidOutput;
	}

	public String getGlobalException() {
		return globalException;
	}

	public void setGlobalException(String globalException) {
		this.globalException = globalException;
	}

	private NaiveBayesOutput lungcancerOutput;

	private NaiveBayesOutput thyroidOutput;

	private String globalException;

}
