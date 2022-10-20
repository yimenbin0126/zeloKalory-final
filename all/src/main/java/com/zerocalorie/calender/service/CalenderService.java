package com.zerocalorie.calender.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CalSearchMbDTO;
import com.zerocalorie.calender.dto.TodoListDTO;
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
	
	// >>> 페이징 관련
	public Map paging(HttpServletRequest request, HttpServletResponse response, CalPageMbDTO calPageMbDTO);
	
	public Map getPagingList(CalPageMbDTO calPageMbDTO, int pageNum, int countPage, int[] countPerPageArr);
	
	
	public List<TodoListDTO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbDTO calPageMbDTO);
	
	// >>>>> todolist 조회
	public List<TodoListDTO> TodoListRead(HttpServletRequest request, CalPageMbDTO calPageMbDTO, Map pageDateInfo);

	// 친구 검색
	public List<CalSearchMbDTO> searchUser(HttpServletRequest request);
	

	
	// >> 응원 메세지
	
	// 응원 msg 추가 >> 했음
	public void cheerMsgAdd(HttpServletRequest request, String pageId);
	
	// 응원 msg 삭제 또는 수정 >> 했음
	public void cheerMsgDelnMod(HttpServletRequest request, String pageId);
	
	// 응원 msg db에서 삭제 >> cheerMsgDelnMod 했음
	public void cheerMsgDel(HttpServletRequest request, List<Integer> pCHR_NOList);

	// 응원 msg db에서 메세지만 비움 >> cheerMsgDelnMod 했음
	public void cheerMsgEmpty(HttpServletRequest request);
	
	
	// >> todolist
	
	// todoList 추가 >> 했음
	public void todoListAdd(HttpServletRequest request, String pageId);
	
	// todoList 삭제 >> 했음
	public void todoListDel(HttpServletRequest request);
	
	// todoList 수정 >> 했음
	public void todoListMod(HttpServletRequest request);

}
