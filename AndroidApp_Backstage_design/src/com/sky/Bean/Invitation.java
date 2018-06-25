package com.sky.Bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

//帖子
public class Invitation {
	private int ivtn_id;
	private String ivtn_time;
	private String ivtn_content;
	
	private String ivtn_title;
	private int ivtn_PraiseNum;
	private String ivtn_state;
	private String ivtn_isPublic;
	private String ivtn_lookNum;
	private String image;
	@Transient
	private Integer user_id;
	@Transient
	private String user_phone;
	@Transient 
	private String user_name;
	@Transient
	private String type_name;
	@Transient
	private Integer commentNum;
	
	private User user;
	private InvtType invtType;

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
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum() {
		this.commentNum = commentSet.size();
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
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
	public int getIvtn_id() {
		return ivtn_id;
	}
	public void setIvtn_id(int ivtn_id) {
		this.ivtn_id = ivtn_id;
	}
	
	public String getIvtn_time() {
		return ivtn_time;
	}
	public void setIvtn_time(String ivtn_time) {
		this.ivtn_time = ivtn_time;
	}
	
	public String getIvtn_content() {
		return ivtn_content;
	}
	public void setIvtn_content(String ivtn_content) {
		this.ivtn_content = ivtn_content;
	}
	
	public String getIvtn_title() {
		return ivtn_title;
	}
	public void setIvtn_title(String ivtn_title) {
		this.ivtn_title = ivtn_title;
	}
	
	public int getIvtn_PraiseNum() {
		return ivtn_PraiseNum;
	}
	public void setIvtn_PraiseNum(int ivtn_PraiseNum) {
		this.ivtn_PraiseNum = ivtn_PraiseNum;
	}
	
	public String getIvtn_state() {
		return ivtn_state;
	}
	public void setIvtn_state(String ivtn_state) {
		this.ivtn_state = ivtn_state;
	}
	public String getIvtn_isPublic() {
		return ivtn_isPublic;
	}
	public void setIvtn_isPublic(String ivtn_isPublic) {
		this.ivtn_isPublic = ivtn_isPublic;
	}
	public String getIvtn_lookNum() {
		return ivtn_lookNum;
	}
	public void setIvtn_lookNum(String ivtn_lookNum) {
		this.ivtn_lookNum = ivtn_lookNum;
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
	@Override
	public String toString() {
		return "Invitation [ivtn_id=" + ivtn_id + ", ivtn_time=" + ivtn_time + ", ivtn_content=" + ivtn_content
				+ ", ivtn_title=" + ivtn_title + ", ivtn_PraiseNum=" + ivtn_PraiseNum + ", ivtn_state=" + ivtn_state
				+ ", ivtn_isPublic=" + ivtn_isPublic + ", ivtn_lookNum=" + ivtn_lookNum + ", image=" + image
				+ ", user_id=" + user_id + ", user_phone=" + user_phone + ", user_name=" + user_name + ", type_name="
				+ type_name + ", commentNum=" + commentNum + "]";
	}
	
	
}
