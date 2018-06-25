package com.sky.Bean;

import java.util.HashSet;
import java.util.Set;

//帖子的类型,话题
public class InvtType {
	private Integer type_id;
	private String type_name;
	private String type_image;
	private String type_desc;
	private Set followTypeSet=new HashSet<FollowType>();
	private Set invitationSet=new HashSet<Invitation>();
	
	public Set getInvitationSet() {
		return invitationSet;
	}
	public void setInvitationSet(Set invitationSet) {
		this.invitationSet = invitationSet;
	}
	public Set getFollowTypeSet() {
		return followTypeSet;
	}
	public void setFollowTypeSet(Set followTypeSet) {
		this.followTypeSet = followTypeSet;
	}
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_image() {
		return type_image;
	}
	public void setType_image(String type_image) {
		this.type_image = type_image;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	@Override
	public String toString() {
		return "InvtType [type_id=" + type_id + ", type_name=" + type_name + ", type_image=" + type_image
				+ ", type_desc=" + type_desc 
				+ "]";
	}
	
	
}
