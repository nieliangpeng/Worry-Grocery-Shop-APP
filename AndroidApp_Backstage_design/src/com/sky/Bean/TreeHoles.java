package com.sky.Bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TreeHoles {
	private int letter_id;
	private User user;
	private String letter_content;
	private Date letter_time;
	private String isShowInTheTree;
	private Set letterReplySet=new HashSet<LetterReply>();
	private Set getLetterSet=new HashSet<GetLetter>();
	
	public Set getGetLetterSet() {
		return getLetterSet;
	}
	public void setGetLetterSet(Set getLetterSet) {
		this.getLetterSet = getLetterSet;
	}
	public Set getLetterReplySet() {
		return letterReplySet;
	}
	public void setLetterReplySet(Set letterReplySet) {
		this.letterReplySet = letterReplySet;
	}
	public int getLetter_id() {
		return letter_id;
	}
	public void setLetter_id(int letter_id) {
		this.letter_id = letter_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLetter_content() {
		return letter_content;
	}
	public void setLetter_content(String letter_content) {
		this.letter_content = letter_content;
	}
	public Date getLetter_time() {
		return letter_time;
	}
	public void setLetter_time(Date letter_time) {
		this.letter_time = letter_time;
	}
	public String getIsShowInTheTree() {
		return isShowInTheTree;
	}
	public void setIsShowInTheTree(String isShowInTheTree) {
		this.isShowInTheTree = isShowInTheTree;
	}
	
}
