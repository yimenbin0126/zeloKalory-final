package com.zerocalorie.svservice.dto;

public class e_SvSearchDTO {
	// 검색의 값
	private String search_time;
	private String search_content;
	private String search_type;
	// 페이징에서 가져올 값
	private String sv_type;
	private int board_NowStartBno;
	private int board_NowEndBno;

	// 멤버 번호
	private int member_no;
	// 조회수, 좋아요순
	// view, like
	private String standard;
	
	public String getSearch_time() {
		return search_time;
	}
	public void setSearch_time(String search_time) {
		this.search_time = search_time;
	}
	public String getSearch_content() {
		return search_content;
	}
	public void setSearch_content(String search_content) {
		this.search_content = search_content;
	}
	public String getSearch_type() {
		return search_type;
	}
	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}
	public String getSv_type() {
		return sv_type;
	}
	public void setSv_type(String sv_type) {
		this.sv_type = sv_type;
	}
	public int getBoard_NowStartBno() {
		return board_NowStartBno;
	}
	public void setBoard_NowStartBno(int board_NowStartBno) {
		this.board_NowStartBno = board_NowStartBno;
	}
	public int getBoard_NowEndBno() {
		return board_NowEndBno;
	}
	public void setBoard_NowEndBno(int board_NowEndBno) {
		this.board_NowEndBno = board_NowEndBno;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	@Override
	public String toString() {
		return "e_SvSearchDTO [search_time=" + search_time + ", search_content=" + search_content + ", search_type="
				+ search_type + ", sv_type=" + sv_type + ", board_NowStartBno=" + board_NowStartBno
				+ ", board_NowEndBno=" + board_NowEndBno + ", member_no=" + member_no + ", standard=" + standard + "]";
	}
	
}
