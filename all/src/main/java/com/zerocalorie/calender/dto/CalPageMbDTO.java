package com.zerocalorie.calender.dto;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

// 페이지 주인 정보
public class CalPageMbDTO {
	private int member_no;
	private String nickname;
	private String pro_img;
	
	
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPro_img() {
		return pro_img;
	}
	public void setPro_img(String pro_img) {
		this.pro_img = pro_img;
	}

}
