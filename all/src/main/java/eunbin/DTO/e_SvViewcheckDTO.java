package eunbin.DTO;

import java.sql.Date;

public class e_SvViewcheckDTO {
	private int bno;
	private Date view_time;
	private String member_ip;
	private int member_no;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public Date getView_time() {
		return view_time;
	}
	public void setView_time(Date view_time) {
		this.view_time = view_time;
	}
	public String getMember_ip() {
		return member_ip;
	}
	public void setMember_ip(String member_ip) {
		this.member_ip = member_ip;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	@Override
	public String toString() {
		return "e_SvViewcheckDTO [bno=" + bno + ", view_time=" + view_time + ", member_ip=" + member_ip + ", member_no="
				+ member_no + "]";
	}
	
}
