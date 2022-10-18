package com.zerocalorie.calender.dao;

import java.util.List;
import java.util.Map;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CheerMsgDTO;


public interface ChrPagingDAO {
	
	// 해당 페이지의 목록만 조회
	public List<CheerMsgDTO> selectPagingList(CalPageMbDTO calPageMbDTO, int start, int end);
	
	// 전체 글 개수 가져오기
	public int selectListCount(CalPageMbDTO calPageMbDTO);

}
