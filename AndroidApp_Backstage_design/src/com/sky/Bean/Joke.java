package com.sky.Bean;

public class Joke {
	private int joke_id;
	private String joke_content;
	public int getJoke_id() {
		return joke_id;
	}
	public void setJoke_id(int joke_id) {
		this.joke_id = joke_id;
	}
	public String getJoke_content() {
		return joke_content;
	}
	public void setJoke_content(String joke_content) {
		this.joke_content = joke_content;
	}
	@Override
	public String toString() {
		return "Joke [joke_id=" + joke_id + ", joke_content=" + joke_content + "]";
	}
	
}
