package com.model;

import java.io.Serializable;

public class AnswerPuzzle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	

	public String getProvidedAnswer() {
		return providedAnswer;
	}

	public void setProvidedAnswer(String providedAnswer) {
		this.providedAnswer = providedAnswer;
	}



	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}



	public String getProvidedAnswerDesc() {
		return providedAnswerDesc;
	}

	public void setProvidedAnswerDesc(String providedAnswerDesc) {
		this.providedAnswerDesc = providedAnswerDesc;
	}



	public String getCorrectAnswerDesc() {
		return correctAnswerDesc;
	}

	public void setCorrectAnswerDesc(String correctAnswerDesc) {
		this.correctAnswerDesc = correctAnswerDesc;
	}



	



	public boolean isAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(boolean answerStatus) {
		this.answerStatus = answerStatus;
	}







	private int questionId;
	
	private String questionDesc;
	
	
	private String providedAnswer;
	
	private String correctAnswer;
	
	private String providedAnswerDesc;
	
	private String correctAnswerDesc;
	
	private boolean answerStatus;

}
