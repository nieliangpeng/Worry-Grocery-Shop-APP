package com.example.worrygroceryshop.bean;

import java.util.Date;

public class Video {
	private int video_id;
	private String video_fileName;
	private String video_resource;
	private Date video_time;
	private int good_num;
	public int getVideo_id() {
		return video_id;
	}
	public void setVideo_id(int video_id) {
		this.video_id = video_id;
	}
	public String getVideo_fileName() {
		return video_fileName;
	}
	public void setVideo_fileName(String video_fileName) {
		this.video_fileName = video_fileName;
	}
	public String getVideo_resource() {
		return video_resource;
	}
	public void setVideo_resource(String video_resource) {
		this.video_resource = video_resource;
	}
	public Date getVideo_time() {
		return video_time;
	}
	public void setVideo_time(Date video_time) {
		this.video_time = video_time;
	}
	public int getGood_num() {
		return good_num;
	}
	public void setGood_num(int good_num) {
		this.good_num = good_num;
	}
	
}
