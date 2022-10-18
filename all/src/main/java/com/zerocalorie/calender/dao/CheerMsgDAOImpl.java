package com.zerocalorie.calender.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zerocalorie.calender.dto.CalPageMbDTO;
import com.zerocalorie.calender.dto.CheerMsgDTO;

@Repository
public class CheerMsgDAOImpl implements CheerMsgDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<CheerMsgDTO> read(CalPageMbDTO calPageMbDTO) {
		return sqlSession.selectList("cheerMsgMapper.read",calPageMbDTO);
	}

	@Override
	public void add(CheerMsgDTO cheerMsgDTO) {
		sqlSession.insert("cheerMsgMapper.add",cheerMsgDTO);
	}

	@Override
	public void empty(CheerMsgDTO cheerMsgDTO) {
		sqlSession.update("cheerMsgMapper.empty",cheerMsgDTO);
	}

	@Override
	public void del(CheerMsgDTO cheerMsgDTO) {
		 sqlSession.delete("cheerMsgMapper.del",cheerMsgDTO);
	}

}
