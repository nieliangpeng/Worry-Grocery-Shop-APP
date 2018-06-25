package com.example.worrygroceryshop.bean;
//评论

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Comment {
	private int comment_id;

	private Invitation invitation;
	private String comment_content;
	private String comment_state;
	private String comment_isRealNam;
	private String comment_image;
	private String comment_time;
	private int comment_praiseNum;
	private Set replySet=new HashSet<Reply>();
	//
	private int user_id;
	private String user_phone;
	private String user_name;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Set getReplySet() {
		return replySet;
	}
	public void setReplySet(Set replySet) {
		this.replySet = replySet;
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public Invitation getInvitation() {
		return invitation;
	}
	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_state() {
		return comment_state;
	}
	public void setComment_state(String comment_state) {
		this.comment_state = comment_state;
	}
	public String getComment_isRealNam() {
		return comment_isRealNam;
	}
	public void setComment_isRealNam(String comment_isRealNam) {
		this.comment_isRealNam = comment_isRealNam;
	}
	public String getComment_image() {
		return comment_image;
	}
	public void setComment_image(String comment_image) {
		this.comment_image = comment_image;
	}

	public String getComment_time() {
		return comment_time;
	}

	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}

	public int getComment_praiseNum() {
		return comment_praiseNum;
	}
	public void setComment_praiseNum(int comment_praiseNum) {
		this.comment_praiseNum = comment_praiseNum;
	}
	
	
}
