package com.example.worrygroceryshop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//帖子
public class Invitation implements Serializable{
	private int ivtn_id;
	private String ivtn_time;
	private String ivtn_content;
	private String ivtn_title;
	private int ivtn_PraiseNum;
	private String ivtn_state;
	private String ivtn_isPublic;
	private String ivtn_lookNum;
	
	private User user;
	private InvtType invtType;
	private Set imageSet=new HashSet<Image>();
	private Set collectSet=new HashSet<Collect>(); 
	private Set commentSet=new HashSet<Comment>();
	//
	private int user_id;
	private String user_phone;
	private String user_name;
	private String type_name;
	private int commentNum;

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

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	//
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
		return ivtn_id;
	}
	public void setIvtn_id(int ivtn_id) {
		ivtn_id = ivtn_id;
	}

	public String getIvtn_time() {
		return ivtn_time;
	}

	public void setIvtn_time(String ivtn_time) {
		ivtn_time = ivtn_time;
	}

	public String getIvtn_content() {
		return ivtn_content;
	}
	public void setIvtn_content(String ivtn_content) {
		ivtn_content = ivtn_content;
	}
	public String getIvtn_title() {
		return ivtn_title;
	}
	public void setIvtn_title(String ivtn_title) {
		ivtn_title = ivtn_title;
	}
	public int getIvtn_PraiseNum() {
		return ivtn_PraiseNum;
	}
	public void setIvtn_PraiseNum(int ivtn_PraiseNum) {
		ivtn_PraiseNum = ivtn_PraiseNum;
	}
	public String getIvtn_state() {
		return ivtn_state;
	}
	public void setIvtn_state(String ivtn_state) {
		ivtn_state = ivtn_state;
	}
	public String getIvtn_isPublic() {
		return ivtn_isPublic;
	}
	public void setIvtn_isPublic(String ivtn_isPublic) {
		ivtn_isPublic = ivtn_isPublic;
	}
	public String getIvtn_lookNum() {
		return ivtn_lookNum;
	}
	public void setIvtn_lookNum(String ivtn_lookNum) {
		ivtn_lookNum = ivtn_lookNum;
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
