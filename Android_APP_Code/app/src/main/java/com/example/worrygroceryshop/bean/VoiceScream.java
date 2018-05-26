package com.example.worrygroceryshop.bean;

public class VoiceScream {
	private int voiceScream_id;
	private User user;
	private String voice_fileName;
	
	public int getVoiceScream_id() {
		return voiceScream_id;
	}
	public void setVoiceScream_id(int voiceScream_id) {
		this.voiceScream_id = voiceScream_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getVoice_fileName() {
		return voice_fileName;
	}
	public void setVoice_fileName(String voice_fileName) {
		this.voice_fileName = voice_fileName;
	}
	
}
