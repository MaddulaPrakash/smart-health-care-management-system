package com.model;

import java.io.Serializable;
import java.util.List;

public class Information implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AnswerPuzzle> puzzleAnswers;

	public List<AnswerPuzzle> getPuzzleAnswers() {
		return puzzleAnswers;
	}

	public void setPuzzleAnswers(List<AnswerPuzzle> puzzleAnswers) {
		this.puzzleAnswers = puzzleAnswers;
	}

}
