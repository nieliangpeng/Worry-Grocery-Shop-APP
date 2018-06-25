package com.sky.Bean;

public class Admin {
	private int admin_id;
	private String admin_phone;
	private String admin_pwd;
	private String admin_name;
	private String admin_header;
	private String admin_validatecode;
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_phone() {
		return admin_phone;
	}
	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}
	public String getAdmin_pwd() {
		return admin_pwd;
	}
	public void setAdmin_pwd(String admin_pwd) {
		this.admin_pwd = admin_pwd;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getAdmin_header() {
		return admin_header;
	}
	public void setAdmin_header(String admin_header) {
		this.admin_header = admin_header;
	}
	public String getAdmin_validatecode() {
		return admin_validatecode;
	}
	public void setAdmin_validatecode(String admin_validatecode) {
		this.admin_validatecode = admin_validatecode;
	}
	@Override
	public String toString() {
		return "Admin [admin_id=" + admin_id + ", admin_phone=" + admin_phone + ", admin_pwd=" + admin_pwd
				+ ", admin_name=" + admin_name + ", admin_header=" + admin_header + ", admin_validatecode="
				+ admin_validatecode + "]";
	}
	
}
