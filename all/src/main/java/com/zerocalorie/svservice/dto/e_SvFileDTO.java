package com.zerocalorie.svservice.dto;

import java.sql.Date;

public class e_SvFileDTO {
	private int bno;
	private int file_order;
	private String filename;
	private String f_path;
	private Date file_date;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getFile_order() {
		return file_order;
	}
	public void setFile_order(int file_order) {
		this.file_order = file_order;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getF_path() {
		return f_path;
	}
	public void setF_path(String f_path) {
		this.f_path = f_path;
	}
	public Date getFile_date() {
		return file_date;
	}
	public void setFile_date(Date file_date) {
		this.file_date = file_date;
	}
	@Override
	public String toString() {
		return "e_SvFileDTO [bno=" + bno + ", file_order=" + file_order + ", filename=" + filename + ", f_path="
				+ f_path + ", file_date=" + file_date + "]";
	}
}
