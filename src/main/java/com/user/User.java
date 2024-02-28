package com.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.clan.Clan;
import com.questions.Question;
import com.solutions.Solution;

public class User {
	String userName;
	String mailID;
	int score;
	int streak;
	LocalDate dateJoined;
	LocalDate streakDate;
	int Ace_badge;
	int Conquer_badge;
	int Crown_badge;
	Map<String, Integer> badges;
	Clan clan;
	List<Question> Completed;
	List<Question> authored;
	List<Solution> attemptedSolutions;
	

	public String getUserName() {
		return userName;
	}


	public String getMailID() {
		return mailID;
	}


	public int getScore() {
		return score;
	}


	public int getStreak() {
		return streak;
	}


	public LocalDate getDateJoined() {
		return dateJoined;
	}


	public LocalDate getStreakDate() {
		return streakDate;
	}


	public int getAce_badge() {
		return Ace_badge;
	}


	public int getConquer_badge() {
		return Conquer_badge;
	}


	public int getCrown_badge() {
		return Crown_badge;
	}


	public Clan getClan() {
		return clan;
	}


	public List<Question> getCompleted() {
		return Completed;
	}


	public List<Question> getAuthored() {
		return authored;
	}


	public List<Solution> getAttemptedSolutions() {
		return attemptedSolutions;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setMailID(String mailID) {
		this.mailID = mailID;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public void setStreak(int streak) {
		this.streak = streak;
	}


	public void setDateJoined(LocalDate dateJoined) {
		this.dateJoined = dateJoined;
	}


	public void setStreakDate(LocalDate streakDate) {
		this.streakDate = streakDate;
	}


	public void setAce_badge(int ace_badge) {
		Ace_badge = ace_badge;
	}


	public void setConquer_badge(int conquer_badge) {
		Conquer_badge = conquer_badge;
	}


	public void setCrown_badge(int crown_badge) {
		Crown_badge = crown_badge;
	}


	public void setClan(Clan clan) {
		this.clan = clan;
	}


	public void setCompleted(List<Question> completed) {
		Completed = completed;
	}


	public void setAuthored(List<Question> authored) {
		this.authored = authored;
	}


	public void setAttemptedSolutions(List<Solution> attemptedSolutions) {
		this.attemptedSolutions = attemptedSolutions;
	}


	public User(){
		// Default constructor
	}
	
	
	public JSONObject toJSON() {
		return null;
	}
	
}
