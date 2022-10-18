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

	// 주소에서 id 가져옴
	public String findId(HttpServletRequest request);

	// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
	public CalPageMbDTO idToMbNo(String pageId);
	
	// 접속자가 본인 페이지 보는거면 true
	public boolean checkMyPage(String pageId ,e_MemberDTO sessionUserDTO );
	
	// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
	public Map setPageDate(HttpServletRequest request);
	
	
	// >>> 페이징 관련
	public Map paging(HttpServletRequest request, HttpServletResponse response, CalPageMbDTO calPageMbDTO);
	
	public Map getPagingList(CalPageMbDTO calPageMbDTO, int pageNum, int countPage, int[] countPerPageArr);
	
	
	public List<TodoListDTO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbDTO calPageMbDTO);
	
	// >>>>> todolist 조회
	public List<TodoListDTO> TodoListRead(HttpServletRequest request, CalPageMbDTO calPageMbDTO, Map pageDateInfo);

	// 친구 검색
	public List<CalSearchMbDTO> searchUser(HttpServletRequest request);
	
	// command 값을 받아옴 (읽기, 추가 삭제 등이 들어오면 수행 (read, add, del))
	public void reciveCommand(HttpServletRequest request, CalPageMbDTO calPageMbVO, e_MemberDTO sessionUserDTO);
	
	// 응원 msg db에 추가
	public void cheerMsgAdd(HttpServletRequest request, CalPageMbDTO calPageMbDTO, e_MemberDTO sessionUserDTO );
	
	
	// 응원 msg db에서 삭제
	public void cheerMsgDel(HttpServletRequest request, List<Integer> pCHR_NOList);

	// 응원 msg db에서 메세지만 비움
	public void cheerMsgEmpty(HttpServletRequest request);
	
	// todoList 추가
	public void todoListAdd(HttpServletRequest request, CalPageMbDTO calPageMbDTO);
	
	// todoList 삭제
	public void todoListDel(HttpServletRequest request);
	
	// todoList 수정
	public void todoListMod(HttpServletRequest request);

}
