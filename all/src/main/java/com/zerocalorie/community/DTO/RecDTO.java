package com.zerocalorie.community.DTO;

public class RecDTO {
	
	int articleno;
	
	Integer member_no;
	
	int reccount;
	
	String title;

	public int getArticleno() {
		return articleno;
	}

	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(Integer member_no) {
		this.member_no = member_no;
	}

	public int getReccount() {
		return reccount;
	}

	public void setReccount(int reccount) {
		this.reccount = reccount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "RecDTO [articleno=" + articleno + ", member_no=" + member_no + ", reccount=" + reccount + ", title=" + title + "]";
	}
	
	
}
