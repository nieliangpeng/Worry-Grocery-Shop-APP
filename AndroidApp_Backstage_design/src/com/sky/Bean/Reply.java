package com.sky.Bean;
//回复
public class Reply {
	private int reply_id;
	private Comment comment;
	private User user;
	private String reply_content;
	
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	@Override
	public String toString() {
		return "Reply [reply_id=" + reply_id + ", comment=" + comment + ", user=" + user + ", reply_content="
				+ reply_content + "]";
	}
	
}
