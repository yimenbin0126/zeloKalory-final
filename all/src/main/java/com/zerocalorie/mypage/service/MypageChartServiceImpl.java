package com.zerocalorie.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.mypage.dao.MypageChartDAO;
import com.zerocalorie.mypage.dto.MypageChartDTO;


@Service
public class MypageChartServiceImpl implements MypageChartService {
	
	@Autowired
	MypageChartDAO mypageChartDAO;
	
	@Override
	public List<MypageChartDTO> weightread(int member_no) {
		MypageChartDTO mypageChartDTO = new MypageChartDTO();
		mypageChartDTO.setMember_no(member_no);
		return mypageChartDAO.read(mypageChartDTO);
	}
	
	@Override
	public void weightAdd(MypageChartDTO mypageChartDTO) {
		/*MypageChartDTO mypageChartDTO = new MypageChartDTO();
		mypageChartDTO.setCurrent_weight(0);
		mypageChartDTO.setTarget_weight(0);
		mypageChartDTO.setMember_no(0);*/
		mypageChartDAO.add(mypageChartDTO);
		
	}
	
	@Override
	public void weightMod(MypageChartDTO mypageChartDTO) {
		/*MypageChartDTO mypageChartDTO = new MypageChartDTO();
		mypageChartDTO.setCurrent_weight(0);
		mypageChartDTO.setTarget_weight(0);
		mypageChartDTO.setMember_no(0);*/
		mypageChartDAO.mod(mypageChartDTO);
		
	}

}
