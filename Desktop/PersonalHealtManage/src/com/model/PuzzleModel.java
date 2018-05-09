package com.model;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PuzzleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String agegroup;

	private String questionDesc;

	private String answer1;

	private String answer2;

	private String answer3;

	private String answer4;

	private String correctAnswer;

	private String name = null;

	private CommonsMultipartFile file = null;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public CommonsMultipartFile getFile() {

		return file;

	}

	public void setFile(CommonsMultipartFile file) {

		this.file = file;

		this.contentType = file.getContentType();

		this.data = file.getBytes();

		this.name = file.getOriginalFilename();

	}

	public String getAgegroup() {
		return agegroup;
	}

	public void setAgegroup(String agegroup) {
		this.agegroup = agegroup;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private byte[] data;

	private String contentType;

}
