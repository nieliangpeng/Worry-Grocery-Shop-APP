package com.sky.Bean;
//用户关注的话题
public class FollowType {
	private int FT_id;
	private User user;
	private InvtType invtType;
	
	public int getFT_id() {
		return FT_id;
	}
	public void setFT_id(int fT_id) {
		FT_id = fT_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public InvtType getInvtType() {
		return invtType;
	}
	public void setInvtType(InvtType invtType) {
		this.invtType = invtType;
	}
	
	
	
}
