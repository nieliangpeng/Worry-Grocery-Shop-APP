package com.sky.Bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class User {
	private Integer user_id;
	
	private String user_name;
	private String user_pwd;
	private String user_phone;
	private String user_desc;
	private String user_image;
	private String user_state;
	private String master_profile;
	private String detailintroduction;
	private String user_validatecode;
	private Set <PenFriend>penFriendSet=new HashSet<PenFriend>();
	private Set <Follow>followSet=new HashSet<Follow>();
	private Set <FollowType>followTypeSet=new HashSet<FollowType>();
	private Set <Invitation>invitationSet=new HashSet<Invitation>();
	private Set <Collect>collectSet=new HashSet<Collect>();

	private Set <Reply>replySet=new HashSet<Reply>();
	private Set <TreeHoles>letterSet=new HashSet<TreeHoles>();
	
	private Set getLetterSet=new HashSet<GetLetter>();//
	private Set voiceScreamSet=new HashSet<VoiceScream>();
	private Set textShoutSet=new HashSet<TextShout>();
	@Transient
	private Integer letter_num;
	@Transient
	private Integer follow_num;
	@Transient
	private Integer collect_num;
	@Transient
	private Integer invitation_num;
	public Integer getFollow_num() {
		return follow_num;
	}
	public void setFllow_num() {
		this.follow_num =followSet.size();
	}
	public Integer getCollect_num() {
		return collect_num;
	}
	public void setCollect_num() {
		this.collect_num =collectSet.size();
	}
	public Integer getInvitation_num() {
		return invitation_num;
	}
	public void setInvitation_num() {
		this.invitation_num =invitationSet.size();
	}
	public void setLetter_num() {
		this.letter_num =letterSet.size();
	}
	public Integer getLetter_num() {
		return letter_num;
	}
	
	public Set getTextShoutSet() {
		return textShoutSet;
	}
	public void setTextShoutSet(Set textShoutSet) {
		this.textShoutSet = textShoutSet;
	}
	public Set getVoiceScreamSet() {
		return voiceScreamSet;
	}
	public void setVoiceScreamSet(Set voiceScreamSet) {
		this.voiceScreamSet = voiceScreamSet;
	}
	public Set getGetLetterSet() {
		return getLetterSet;
	}
	public void setGetLetterSet(Set getLetterSet) {
		this.getLetterSet = getLetterSet;
	}
	
	public Set getLetterSet() {
		return letterSet;
	}		
	public void setLetterSet(Set letterSet) {
		this.letterSet = letterSet;
	}
	public Set getReplySet() {
		return replySet;
	}
	public void setReplySet(Set replySet) {
		this.replySet = replySet;
	}
	
	public Set getCollectSet() {
		return collectSet;
	}
	public void setCollectSet(Set collectSet) {
		this.collectSet = collectSet;
	}
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
	public Set getFollowSet() {
		return followSet;
	}
	public void setFollowSet(Set followSet) {
		this.followSet = followSet;
	}
	public Set getPenFriendSet() {
		return penFriendSet;
	}
	public void setPenFriendSet(Set penFriendSet) {
		this.penFriendSet = penFriendSet;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_desc() {
		return user_desc;
	}
	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getUser_state() {
		return user_state;
	}
	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}
	public String getMaster_profile() {
		return master_profile;
	}
	public void setMaster_profile(String master_profile) {
		this.master_profile = master_profile;
	}
	public String getDetailintroduction() {
		return detailintroduction;
	}
	public void setDetailintroduction(String detailintroduction) {
		this.detailintroduction = detailintroduction;
	}
	public String getUser_validatecode() {
		return user_validatecode;
	}
	public void setUser_validatecode(String user_validatecode) {
		this.user_validatecode = user_validatecode;
	}
//	public int getletter_num() {
//		return letterSet.size();
//	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_pwd=" + user_pwd + ", user_phone="
				+ user_phone + ", user_desc=" + user_desc + ", user_image=" + user_image + ", user_state=" + user_state
				+ ", master_profile=" + master_profile + ", detailintroduction=" + detailintroduction
				+ ", user_validatecode=" + user_validatecode + ", letter_num=" + letter_num + ", follow_num="
				+ follow_num + ", collect_num=" + collect_num + ", invitation_num=" + invitation_num + "]";
	}
	
	
	
	
}
