package com.zerocalorie.calender.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CalSearchMbDTO;
import com.zerocalorie.member.dto.e_MemberDTO;



public interface CalenderService {
	
	// >>> 페이지관련

	// 주소에서 id 가져옴
	public String findId(HttpServletRequest request);

	// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
	public CalPageMbDTO idToMbNo(String pageId);
	
	// 접속자가 본인 페이지 보는거면 true
	public boolean checkMyPage(String pageId ,e_MemberDTO sessionUserDTO );
	
	// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
	//public Map setPageDate(HttpServletRequest request);
	public Map setPageDate(String pageYear, String pageMonth, String pageDate);
	
	// 친구 검색
	public List<CalSearchMbDTO> searchUser(HttpServletRequest request);

}
