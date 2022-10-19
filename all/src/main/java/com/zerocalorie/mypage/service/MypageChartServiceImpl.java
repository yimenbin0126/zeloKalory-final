package com.zerocalorie.mypage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.member.dto.e_MemberDTO;
import com.zerocalorie.mypage.dao.MypageChartDAO;
import com.zerocalorie.mypage.dto.MypageChartDTO;


@Service
public class MypageChartServiceImpl implements MypageChartService {
	
	@Autowired
	MypageChartDAO mypageChartDAO;
	
	// mypage 해당user 몸무게 가져오기
	@Override
	public List<MypageChartDTO> weightread(int member_no) {
		System.out.println(">> weightread");
		return mypageChartDAO.read(member_no);
	}
	
	// mypage 몸무게 추가
	@Override
	public void weightAdd(HttpServletRequest request, MypageChartDTO mypageChartDTO) {
		e_MemberDTO sessionUserDTO = new e_MemberDTO();	// 접속자 정보
		sessionUserDTO = (e_MemberDTO)request.getSession().getAttribute("user");
		mypageChartDTO.setMember_no(sessionUserDTO.getMember_no());
		mypageChartDAO.add(mypageChartDTO);		
	}
	
	
	// mypage 몸무게 수정
	@Override
	public void weightMod(HttpServletRequest request, MypageChartDTO mypageChartDTO) {
		e_MemberDTO sessionUserDTO = new e_MemberDTO();	// 접속자 정보
		sessionUserDTO = (e_MemberDTO)request.getSession().getAttribute("user");
		mypageChartDTO.setMember_no(sessionUserDTO.getMember_no());
		mypageChartDAO.mod(mypageChartDTO);	
	}

}
