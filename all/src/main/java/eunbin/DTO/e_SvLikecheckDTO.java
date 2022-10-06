package eunbin.DTO;

import java.sql.Date;

public class e_SvLikecheckDTO {
	private int bno;
	private int like_check;
	private int dislike_check;
	private int member_no;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getLike_check() {
		return like_check;
	}
	public void setLike_check(int like_check) {
		this.like_check = like_check;
	}
	public int getDislike_check() {
		return dislike_check;
	}
	public void setDislike_check(int dislike_check) {
		this.dislike_check = dislike_check;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	@Override
	public String toString() {
		return "e_SvLikecheckDTO [bno=" + bno + ", like_check=" + like_check + ", dislike_check=" + dislike_check
				+ ", member_no=" + member_no + "]";
	}
}
