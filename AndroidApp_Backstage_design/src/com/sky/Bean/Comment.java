package com.sky.Bean;
//评论

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class Comment {
	private int comment_id;
	@Transient
	private String user_name;
	@Transient 
	private String user_phone;
	private Integer user_id;
	private Invitation invitation;
	private String comment_content;
	private String comment_state;
	private String comment_namexu;
	private String comment_image;
	private String comment_time;
	private int comment_praiseNum;
	private Set replySet=new HashSet<Reply>();
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
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
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Invitation getInvitation() {
		return invitation;
	}
	
	public String getComment_namexu() {
		return comment_namexu;
	}
	public void setComment_namexu(String comment_namexu) {
		this.comment_namexu = comment_namexu;
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
	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", invitation=" + invitation
				+ ", comment_content=" + comment_content + ", comment_state=" + comment_state + ", comment_namexu="
				+ comment_namexu + ", comment_image=" + comment_image + ", comment_time=" + comment_time
				+ ", comment_praiseNum=" + comment_praiseNum + ", replySet=" + replySet + "]";
	}
	
	
}
