package com.zerocalorie.mypage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zerocalorie.mypage.dto.MypageChartDTO;

public interface MypageChartService {
	
	public List<MypageChartDTO> weightread(int member_no);

	public void weightAdd(HttpServletRequest request, MypageChartDTO mypageChartDTO);
	
	public void weightMod(HttpServletRequest request, MypageChartDTO mypageChartDTO);
}
