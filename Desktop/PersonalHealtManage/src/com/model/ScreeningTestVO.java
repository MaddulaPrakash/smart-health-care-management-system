package com.model;

import java.util.ArrayList;

public class ScreeningTestVO {
	
	
	private String testName;

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestName() {
		return testName;
	}
	
	
	

	public void setQuestionList(ArrayList<QuestionVO> questionList) {
		this.questionList = questionList;
	}

	public ArrayList<QuestionVO> getQuestionList() {
		return questionList;
	}




	private ArrayList<QuestionVO> questionList;
	

}
