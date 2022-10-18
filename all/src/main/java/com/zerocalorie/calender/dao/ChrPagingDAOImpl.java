package com.zerocalorie.calender.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CheerMsgDTO;

@Repository
public class ChrPagingDAOImpl implements ChrPagingDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	//public List<CheerMsgDTO> selectPagingList(Map chrPagingInfoMap) {
	public List<CheerMsgDTO> selectPagingList(CalPageMbDTO calPageMbDTO, int start, int end) {
		//TODO : 부르는곳에서 해쉬맵으로 주자
		Map chrPagingInfoMap = new HashMap();
		chrPagingInfoMap.put("member_no",calPageMbDTO.getMember_no());
		chrPagingInfoMap.put("start",start);
		chrPagingInfoMap.put("end",end);
		
		return sqlSession.selectList("chrPagingMapper.selectPagingList",chrPagingInfoMap);
	}

	@Override
	public int selectListCount(CalPageMbDTO calPageMbDTO) {
		return sqlSession.selectOne("chrPagingMapper.selectListCount",calPageMbDTO);
	}

}
