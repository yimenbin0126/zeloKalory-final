package com.zerocalorie.calender.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zerocalorie.calender.dto.CalPageMbDTO;

public interface CalCheerMsgService {
	
	// >> 응원 메세지

	// 응원 msg 추가
	public void cheerMsgAdd(HttpServletRequest request, String pageId);
	
	// 응원 msg 삭제 또는 수정
	public void cheerMsgDelnMod(HttpServletRequest request, String pageId);
	
	// 응원 msg db에서 삭제 >> cheerMsgDelnMod 했음
	public void cheerMsgDel(HttpServletRequest request, List<Integer> pCHR_NOList);

	// 응원 msg db에서 메세지만 비움 >> cheerMsgDelnMod 했음
	public void cheerMsgEmpty(HttpServletRequest request);
	
	// >>> 페이징 관련
	public Map paging(HttpServletRequest request, HttpServletResponse response, CalPageMbDTO calPageMbDTO);
	
	public Map getPagingList(CalPageMbDTO calPageMbDTO, int pageNum, int countPage, int[] countPerPageArr);
	
}
