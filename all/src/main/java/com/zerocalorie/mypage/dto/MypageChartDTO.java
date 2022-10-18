package com.zerocalorie.mypage.dto;

import java.sql.Date;

public class MypageChartDTO {

	private int weight_no;
	private int current_weight;
	private int target_weight;	
	private Date weight_date;
	private int member_no;
	
	
	public int getWeight_no() {
		return weight_no;
	}
	public void setWeight_no(int weight_no) {
		this.weight_no = weight_no;
	}
	public int getCurrent_weight() {
		return current_weight;
	}
	public void setCurrent_weight(int current_weight) {
		this.current_weight = current_weight;
	}
	public int getTarget_weight() {
		return target_weight;
	}
	public void setTarget_weight(int target_weight) {
		this.target_weight = target_weight;
	}
	public String getWeight_date() {
		return weight_date.toString();
	}
	public void setWeight_date(Date weight_date) {
		this.weight_date = weight_date;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	@Override
	public String toString() {
		return "MypageChartDTO [weight_no=" + weight_no + ", current_weight=" + current_weight + ", target_weight="
				+ target_weight + ", weight_date=" + weight_date + ", member_no=" + member_no + "]";
	}
	
}
