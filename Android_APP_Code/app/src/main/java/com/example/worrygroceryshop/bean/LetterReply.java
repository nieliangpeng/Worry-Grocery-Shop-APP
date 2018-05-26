package com.example.worrygroceryshop.bean;

import java.util.Date;

public class LetterReply {
	private int letter_reply_id;
	private TreeHoles letter;
	private User user;
	private String letter_content;
	private Date letter_time;
	
	public int getLetter_reply_id() {
		return letter_reply_id;
	}
	public void setLetter_reply_id(int letter_reply_id) {
		this.letter_reply_id = letter_reply_id;
	}
	public TreeHoles getLetter() {
		return letter;
	}
	public void setLetter(TreeHoles letter) {
		this.letter = letter;
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
	
}
