package com.zerocalorie.mypage.service;

import java.util.List;

import com.zerocalorie.mypage.dto.MypageChartDTO;

public interface MypageChartService {
	
	public List<MypageChartDTO> weightread(int member_no);

	public void weightAdd(MypageChartDTO mypageChartDTO);
	
	public void weightMod(MypageChartDTO mypageChartDTO);
}
