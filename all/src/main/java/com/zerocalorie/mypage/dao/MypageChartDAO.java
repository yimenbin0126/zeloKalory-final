package com.zerocalorie.mypage.dao;

import java.util.List;

import com.zerocalorie.mypage.dto.MypageChartDTO;

public interface MypageChartDAO {
	
	public List<MypageChartDTO> read(MypageChartDTO mypageChartDTO);

	public void add(MypageChartDTO mypageChartDTO);
	
	public void mod(MypageChartDTO mypageChartDTO);
	

}
