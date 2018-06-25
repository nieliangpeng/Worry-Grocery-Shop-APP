package com.sky.Bean;

import java.util.Date;

public class LetterReply {
	private Integer letter_reply_id;
	private Integer user_id; 
	private TreeHoles letter;
	
	private String letter_content;
	private String letter_time;
	
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public Integer getLetter_reply_id() {
		return letter_reply_id;
	}
	public void setLetter_reply_id(Integer letter_reply_id) {
		this.letter_reply_id = letter_reply_id;
	}
	public TreeHoles getLetter() {
		return letter;
	}
	public void setLetter(TreeHoles letter) {
		this.letter = letter;
	}
	public String getLetter_content() {
		return letter_content;
	}
	public void setLetter_content(String letter_content) {
		this.letter_content = letter_content;
	}
	public String getLetter_time() {
		return letter_time;
	}
	public void setLetter_time(String letter_time) {
		this.letter_time = letter_time;
	}
	@Override
	public String toString() {
		return "LetterReply [letter_reply_id=" + letter_reply_id + ", letter_content="
				+ letter_content + ", letter_time=" + letter_time + "]";
	}
	
	
}
