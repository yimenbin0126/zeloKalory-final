package com.zerocalorie.calender.dto;

import java.sql.Date;

public class CheerMsgDTO {
	
	private int chr_no;
	private String chr_msg;
	private Date chr_time;
	private int fr_member_no;
	private int to_member_no;
	private int chr_parents_no;
	private String nickname; 
	private String id;
	private int depth;
	private int rnum;
	public int getChr_no() {
		return chr_no;
	}
	public void setChr_no(int chr_no) {
		this.chr_no = chr_no;
	}
	public String getChr_msg() {
		return chr_msg;
	}
	public void setChr_msg(String chr_msg) {
		this.chr_msg = chr_msg;
	}
	public Date getChr_time() {
		return chr_time;
	}
	public void setChr_time(Date chr_time) {
		this.chr_time = chr_time;
	}
	public int getFr_member_no() {
		return fr_member_no;
	}
	public void setFr_member_no(int fr_member_no) {
		this.fr_member_no = fr_member_no;
	}
	public int getTo_member_no() {
		return to_member_no;
	}
	public void setTo_member_no(int to_member_no) {
		this.to_member_no = to_member_no;
	}
	public int getChr_parents_no() {
		return chr_parents_no;
	}
	public void setChr_parents_no(int chr_parents_no) {
		this.chr_parents_no = chr_parents_no;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	
	   
}
