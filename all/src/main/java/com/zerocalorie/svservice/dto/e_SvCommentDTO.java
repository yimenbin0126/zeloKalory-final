package com.zerocalorie.svservice.dto;

public class e_SvCommentDTO {
	private int c_code;
	private int origin_code;
	private String type_code;
	private String to_nickname;
	private String nickname;
	private String comment_code;
	private String create_time;
	private int bno;
	private int member_no;
	private String pro_img;
	
	public int getC_code() {
		return c_code;
	}
	public void setC_code(int c_code) {
		this.c_code = c_code;
	}
	public int getOrigin_code() {
		return origin_code;
	}
	public void setOrigin_code(int origin_code) {
		this.origin_code = origin_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getTo_nickname() {
		return to_nickname;
	}
	public void setTo_nickname(String to_nickname) {
		this.to_nickname = to_nickname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getComment_code() {
		return comment_code;
	}
	public void setComment_code(String comment_code) {
		this.comment_code = comment_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	public String getPro_img() {
		return pro_img;
	}
	public void setPro_img(String pro_img) {
		this.pro_img = pro_img;
	}
	
	@Override
	public String toString() {
		return "e_SvCommentDTO [c_code=" + c_code + ", origin_code=" + origin_code + ", type_code=" + type_code
				+ ", to_nickname=" + to_nickname + ", nickname=" + nickname + ", comment_code=" + comment_code
				+ ", create_time=" + create_time + ", bno=" + bno + ", member_no=" + member_no + ", pro_img=" + pro_img
				+ "]";
	}
	
}
