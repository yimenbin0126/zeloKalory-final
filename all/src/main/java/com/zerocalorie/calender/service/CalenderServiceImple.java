package com.zerocalorie.calender.service;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocalorie.calender.dao.CalPageMbDAO;
import com.zerocalorie.calender.dao.CalSearchMbDAO;
import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CalSearchMbDTO;
import com.zerocalorie.member.dto.e_MemberDTO;


@Service
public class CalenderServiceImple implements CalenderService {
	
	@Autowired
	CalPageMbDAO calPageMbDAO;

	@Autowired
	CalSearchMbDAO calSearchMbDAO;


	// 주소에서 id 가져옴
	@Override
	public String findId(HttpServletRequest request) {
		String urL = request.getServletPath();	// /cal
		String urI = request.getRequestURI();	// /all/cal/qwer

//		String id = urI.substring(urI.indexOf(urL)+urL.length()+1,urI.length());

		int starIdx = urI.lastIndexOf("/");
		int endIdx = urI.length();
		
		// if ?가 있으면 ?로 자른다
		if(urI.lastIndexOf("?") != -1) {
			endIdx = urI.lastIndexOf("?");
		}
		String id = urI.substring(starIdx+1, endIdx);
		
		
		System.out.println("urL : "+urL);
		System.out.println("urI : "+urI);
//		System.out.println(urI.indexOf(urL));
//		System.out.println(urI.length());

		System.out.println("주소에서 id 가져온 id: "+id);

		return id;
	}
	
	
	// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
	@Override
	public CalPageMbDTO idToMbNo(String pageId) {
//		CalPageMbDTO calPageMbDTO = calPageMbDAO.read(pageId);
//		System.out.println("MEMBER_NO : "+calPageMbVO.getMEMBER_NO());
//		System.out.println("NICKNAME : "+calPageMbVO.getNICKNAME());
//		System.out.println("id로 회원정보 가져오기 성공 ");

		return calPageMbDAO.read(pageId);
	}
	
	// 접속자가 본인 페이지 보는거면 true
	@Override
	public boolean checkMyPage(String pageId ,e_MemberDTO sessionUserDTO ) {
		return pageId.equals(sessionUserDTO.getId());
	}
	
	// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
	@Override
	public Map setPageDate(String pageYear, String pageMonth, String pageDate) {
		Map<String, Integer> pageDateInfo = new HashMap<String, Integer>();

		// JSP(뷰)에서 가져온 날짜가 있으면 그대로 돌려줌 ( 클릭>새로고침 되도 페이지 이어짐을 위해)
		if (pageYear != null) {
			pageDateInfo.put("pageYear", Integer.parseInt(pageYear));
			pageDateInfo.put("pageMonth", Integer.parseInt(pageMonth));
			pageDateInfo.put("pageDate", Integer.parseInt(pageDate));

			System.out.println("JSP에서 가져온 날짜 : "+pageDateInfo.get("pageYear")+", "+pageDateInfo.get("pageMonth")+", "+pageDateInfo.get("pageDate"));
		
		// 가져온 날짜 없으면 오늘 날짜 세팅
		} else {
			GregorianCalendar now = new GregorianCalendar();
			pageDateInfo.put("pageYear", now.get(1));
			pageDateInfo.put("pageMonth", now.get(2));
			pageDateInfo.put("pageDate", now.get(5));

			System.out.println("Servlet 에서 오늘 날짜 세팅 :"+pageDateInfo.get("pageYear")+", "
														+pageDateInfo.get("pageMonth")+", "+pageDateInfo.get("pageDate"));
		}
		
		return pageDateInfo;
		
	}

	// 친구 검색
	@Override
	public List<CalSearchMbDTO> searchUser(HttpServletRequest request) {

		return calSearchMbDAO.searchMembers(request.getParameter("serchID"));
	}

}
