package com.sky.Bean;

import java.util.Date;

public class GetLetter {
	private int gl_id;
	private User user;
	private TreeHoles letter;
	private String gl_time;
	
	public int getGl_id() {
		return gl_id;
	}
	public void setGl_id(int gl_id) {
		this.gl_id = gl_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TreeHoles getLetter() {
		return letter;
	}
	public void setLetter(TreeHoles letter) {
		this.letter = letter;
	}
	public String getGl_time() {
		return gl_time;
	}
	public void setGl_time(String gl_time) {
		this.gl_time = gl_time;
	}
	@Override
	public String toString() {
		return "GetLetter [gl_id=" + gl_id + ", user=" + user + ", letter=" + letter + ", gl_time=" + gl_time + "]";
	}
	
	
}
