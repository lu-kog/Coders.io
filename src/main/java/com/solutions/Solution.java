package com.solutions;

import java.time.LocalDate;

import com.questions.Question;
import com.user.User;

public class Solution {
	private String solutionID;
    private User user;
    private Question question;
    private String code;
    private Status status;
    private SolvedType solvedType;
    private LocalDate attemptedDate;

    public Solution() {
        // Default constructor
    }

    public Solution(String solID, User user, Question question, String sol, Status status, SolvedType solvedType, LocalDate attemptedDate) {
        this.solutionID = solID;
        this.user = user;
        this.question = question;
        this.code = sol;
        this.status = status;
        this.solvedType = solvedType;
        this.attemptedDate = attemptedDate;
    }

	public String solutionID() {
		return solutionID;
	}

	public User getUser() {
		return user;
	}

	public Question getQuestion() {
		return question;
	}

	public String getCode() {
		return code;
	}

	public Status getStatus() {
		return status;
	}

	public SolvedType getSolvedType() {
		return solvedType;
	}

	public LocalDate getAttemptedDate() {
		return attemptedDate;
	}

	public void setSolID(String solID) {
		this.solutionID = solID;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setqID(Question qID) {
		this.question = qID;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setSolvedType(SolvedType solvedType) {
		this.solvedType = solvedType;
	}

	public void setAttemptedDate(LocalDate attemptedDate) {
		this.attemptedDate = attemptedDate;
	}

	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}

enum SolvedType {
	PRACTICE,
	TOURNAMENT
}

enum Status {
	ATTEMPTED,
	COMPLETED
}

