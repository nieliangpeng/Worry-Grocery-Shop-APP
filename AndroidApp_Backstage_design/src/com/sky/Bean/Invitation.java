package com.sky.Bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//帖子
public class Invitation {
	private int Ivtn_id;
	private Date Ivtn_time;
	private String Ivtn_content;
	private String Ivtn_title;
	private int Ivtn_PraiseNum;
	private String Ivtn_state;
	private String Ivtn_isPublic;
	private String Ivtn_lookNum;
	
	private User user;
	private InvtType invtType;
	private Set imageSet=new HashSet<Image>();
	private Set collectSet=new HashSet<Collect>(); 
	private Set commentSet=new HashSet<Comment>(); 
	
	public Set getCommentSet() {
		return commentSet;
	}
	public void setCommentSet(Set commentSet) {
		this.commentSet = commentSet;
	}
	public Set getCollectSet() {
		return collectSet;
	}
	public void setCollectSet(Set collectSet) {
		this.collectSet = collectSet;
	}
	public Set getImageSet() {
		return imageSet;
	}
	public void setImageSet(Set imageSet) {
		this.imageSet = imageSet;
	}
	public int getIvtn_id() {
		return Ivtn_id;
	}
	public void setIvtn_id(int ivtn_id) {
		Ivtn_id = ivtn_id;
	}
	public Date getIvtn_time() {
		return Ivtn_time;
	}
	public void setIvtn_time(Date ivtn_time) {
		Ivtn_time = ivtn_time;
	}
	public String getIvtn_content() {
		return Ivtn_content;
	}
	public void setIvtn_content(String ivtn_content) {
		Ivtn_content = ivtn_content;
	}
	public String getIvtn_title() {
		return Ivtn_title;
	}
	public void setIvtn_title(String ivtn_title) {
		Ivtn_title = ivtn_title;
	}
	public int getIvtn_PraiseNum() {
		return Ivtn_PraiseNum;
	}
	public void setIvtn_PraiseNum(int ivtn_PraiseNum) {
		Ivtn_PraiseNum = ivtn_PraiseNum;
	}
	public String getIvtn_state() {
		return Ivtn_state;
	}
	public void setIvtn_state(String ivtn_state) {
		Ivtn_state = ivtn_state;
	}
	public String getIvtn_isPublic() {
		return Ivtn_isPublic;
	}
	public void setIvtn_isPublic(String ivtn_isPublic) {
		Ivtn_isPublic = ivtn_isPublic;
	}
	public String getIvtn_lookNum() {
		return Ivtn_lookNum;
	}
	public void setIvtn_lookNum(String ivtn_lookNum) {
		Ivtn_lookNum = ivtn_lookNum;
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
