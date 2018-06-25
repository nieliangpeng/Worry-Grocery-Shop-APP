package com.sky.Bean;

import java.util.Date;

public class Collect {
	private int collect_id;
	private User user;
	private Invitation invitation;
	private String collect_time;
	
	public int getCollect_id() {
		return collect_id;
	}
	public void setCollect_id(int collect_id) {
		this.collect_id = collect_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Invitation getInvitation() {
		return invitation;
	}
	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}
	public String getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(String collect_time) {
		this.collect_time = collect_time;
	}
	@Override
	public String toString() {
		return "Collect [collect_id=" + collect_id + ", user=" + user + ", invitation=" + invitation + ", collect_time="
				+ collect_time + "]";
	}
	
}
