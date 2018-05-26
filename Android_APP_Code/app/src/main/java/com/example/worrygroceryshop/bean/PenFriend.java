package com.example.worrygroceryshop.bean;

public class PenFriend {
	private int PF_id;
	private User userA;
	private int user_id_B;
	
	public int getPF_id() {
		return PF_id;
	}
	public void setPF_id(int pF_id) {
		PF_id = pF_id;
	}
	public User getUserA() {
		return userA;
	}
	public void setUserA(User userA) {
		this.userA = userA;
	}
	public int getUser_id_B() {
		return user_id_B;
	}
	public void setUser_id_B(int user_id_B) {
		this.user_id_B = user_id_B;
	}
	
}
